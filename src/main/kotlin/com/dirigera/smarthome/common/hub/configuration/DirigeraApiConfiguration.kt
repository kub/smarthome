package com.dirigera.smarthome.common.hub.configuration

import com.dirigera.smarthome.common.hub.api.DirigeraClient
import com.dirigera.smarthome.common.hub.cert.HttpsTrustManager
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Configuration
class DirigeraApiConfiguration {

    @Bean
    open fun dirigeraApiClient(
        objectMapper: ObjectMapper,
        okHttpClientBuilder: OkHttpClient.Builder,
        sslSocketFactory: SSLSocketFactory,
        trustManager: X509TrustManager,
        hostnameVerifier: HostnameVerifier,
        @Value("\${dirigera.port}") dirigeraPort: Int,
        @Value("\${dirigera.access-token}") accessToken: String
    ): DirigeraClient {
        val client = okHttpClientBuilder.build().newBuilder()
            .addInterceptor(accessTokenInterceptor(accessToken))
            .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier(hostnameVerifier)
            .build()

        val retrofit = Retrofit.Builder().client(client)
            .baseUrl(URL("https://${findDirigeraIp(dirigeraPort)}:$dirigeraPort/"))
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()

        return retrofit.create(DirigeraClient::class.java)
    }

    private fun findDirigeraIp(dirigeraPort: Int): String? {
        for (i in (1..999)) {
            val ip = "192.168.0.${i}"
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress(ip, dirigeraPort), 100)
                if (socket.isConnected) {
                    return ip
                }
            } catch (_: Exception) {}
        }
        return null
    }

    fun accessTokenInterceptor(accessToken: String): Interceptor {
        return Interceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader(HttpHeaders.AUTHORIZATION, "Bearer $accessToken").build()
            chain.proceed(request)
        }
    }

    @Bean
    open fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .registerModule(JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    @Bean
    open fun hostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _: String?, _: SSLSession? -> true }
    }

    @Bean
    open fun sslSocketFactory(trustManager: X509TrustManager): SSLSocketFactory? {
        val context = SSLContext.getInstance("TLS")
        context.init(null, arrayOf(trustManager), SecureRandom())
        return context?.socketFactory!!
    }

    @Bean
    open fun trustManager(): X509TrustManager {
        class TrustManager : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }

        return TrustManager()
    }
}
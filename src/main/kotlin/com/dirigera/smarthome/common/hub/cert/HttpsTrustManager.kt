package com.dirigera.smarthome.common.hub.cert

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HttpsTrustManager : X509TrustManager {

    companion object {
        private val trustManagers = arrayOf<TrustManager>(HttpsTrustManager())
        private val _AcceptedIssuers = arrayOf<X509Certificate>()

        fun allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier { arg0: String?, arg1: SSLSession? -> true }
            val context = SSLContext.getInstance("TLS")
            context.init(null, trustManagers, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(context?.socketFactory)
        }
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        TODO("Not yet implemented")
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        TODO("Not yet implemented")
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return _AcceptedIssuers
    }
}
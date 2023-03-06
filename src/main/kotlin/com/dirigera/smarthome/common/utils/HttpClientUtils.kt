package com.dirigera.smarthome.common.utils

import com.dirigera.smarthome.orchestration.DirigeraClientException
import retrofit2.Response

object HttpClientUtils {

    fun <T : Any> handleResponse(response: Response<T>): T {
        if (!response.isSuccessful) {
            throw DirigeraClientException(response.message())
        }
        return response.body() ?: throw DirigeraClientException(response.message())
    }

    fun handleResponse(response: Response<Void>) {
        if (!response.isSuccessful) {
            throw DirigeraClientException(response.message())
        }
    }
}
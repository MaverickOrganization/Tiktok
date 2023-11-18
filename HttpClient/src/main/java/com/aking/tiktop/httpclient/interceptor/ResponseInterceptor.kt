package com.aking.tiktop.httpclient.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body != null) {
            val body = response.body?.string()
            val responseBody = body?.toResponseBody()
            response.newBuilder().body(responseBody).build()
        } else {
            response
        }

    }
}
package com.aking.tiktop.httpclient.http

import com.aking.tiktop.httpclient.config.AppConfig
import com.aking.tiktop.httpclient.interceptor.ResponseInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    @Volatile
    @JvmStatic
    private var _OKCLIENT: OkHttpClient? = null

    @JvmStatic
    val okClient = _OKCLIENT ?: synchronized(this) {
        _OKCLIENT ?: kotlin.run {
            OkHttpClient.Builder()
                .addInterceptor(ResponseInterceptor())
                .build()
        }
            .also { _OKCLIENT = it }
    }

    @Volatile
    @JvmStatic
    private var _RETROFIT: Retrofit? = null

    @JvmStatic
    val retrofitClient = _RETROFIT ?: synchronized(this) {
        _RETROFIT ?: kotlin.run {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConfig.BASE_URL)
                .client(okClient)
                .build()
        }.also { _RETROFIT = it }
    }
}
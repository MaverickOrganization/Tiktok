package com.aking.tiktop.httpclient.http

import com.aking.tiktop.httpclient.config.AppConfig
import com.aking.tiktop.httpclient.interceptor.ResponseInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ResponseInterceptor())
            .build()
    }


    val retrofitClient: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }
}
package com.aking.tiktop.httpclient.http

import com.aking.tiktop.httpclient.api.FriendApi
import com.aking.tiktop.httpclient.api.UserApi
import retrofit2.create

/**
 * http api初始化
 */
object ApiClient {

    val userApi: UserApi by lazy { RetrofitClient.retrofitClient.create() }

    val friendApi: FriendApi by lazy { RetrofitClient.retrofitClient.create() }
}
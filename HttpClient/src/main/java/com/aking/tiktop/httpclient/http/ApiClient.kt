package com.aking.tiktop.httpclient.http

import com.aking.tiktop.httpclient.api.FriendApi
import com.aking.tiktop.httpclient.api.UserApi
import com.aking.tiktop.httpclient.http.RetrofitClient.retrofitClient

/**
 * http api初始化
 */
object ApiClient {

    @Volatile
    @JvmStatic
    private var _USERAPI: UserApi? = null

    @Volatile
    @JvmStatic
    private var _FriendAPI: FriendApi? = null

    @JvmStatic
    val userApi = _USERAPI ?: synchronized(this) {
        _USERAPI ?: retrofitClient.create(UserApi::class.java).also { _USERAPI = it }
    }

    @JvmStatic
    val friendApi = _FriendAPI ?: synchronized(this) {
        _FriendAPI ?: retrofitClient.create(FriendApi::class.java).also { _FriendAPI = it }
    }
}
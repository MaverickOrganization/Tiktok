package com.aking.tiktop.httpclient.http

import com.aking.tiktop.httpclient.api.CommentApi
import com.aking.tiktop.httpclient.api.FriendApi
import com.aking.tiktop.httpclient.api.UserApi
import com.aking.tiktop.httpclient.api.WorksApi
import retrofit2.create

/**
 * http api初始化
 */
object ApiClient {

    // 用户接口
    val userApi: UserApi by lazy { RetrofitClient.retrofitClient.create() }

    // 好友接口
    val friendApi: FriendApi by lazy { RetrofitClient.retrofitClient.create() }

    // 作品接口
    val worksApi: WorksApi by lazy { RetrofitClient.retrofitClient.create() }

    // 评论接口
    val commentApi: CommentApi by lazy { RetrofitClient.retrofitClient.create() }
}
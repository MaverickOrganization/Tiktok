package com.aking.tiktop.httpclient.api

import com.aking.data.ApiResponse
import com.aking.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    /**
     * 登录
     */
    @POST("user/login")
    suspend fun login(@Query("phone") phone: String, @Query("password") password: String): ApiResponse<User>

    /**
     * 注册
     */
    @POST("user/register")
    suspend fun register(@Body user: User): ApiResponse<String>

    /**
     * 通过电话号码查询用户信息
     */
    @POST("user/selectByPhone")
    suspend fun selectUserByPhone(@Query("phone") phone: String): ApiResponse<User>

    /**
     * 通过id查询用户信息
     */
    @POST("user/selectUser")
    suspend fun selectUser(@Query("userId") userId: Long): ApiResponse<User>

}
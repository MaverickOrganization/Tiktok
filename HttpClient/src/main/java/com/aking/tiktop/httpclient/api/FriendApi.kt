package com.aking.tiktop.httpclient.api

import com.aking.data.ApiResponse
import com.aking.data.model.User
import com.aking.data.model.Friend
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Objects

interface FriendApi {

    /**
     * 通过用户Id查看所有关注
     */
    @POST("friend/selectAllFriend")
    suspend fun selectAllFriend(@Query("userId") userId: Long): ApiResponse<List<User>>

    /**
     * 取消关注
     */
    @POST("friend/deleteFriend")
    suspend fun deleteFriend(@Body friend: Friend): ApiResponse<Objects>

    /**
     * 关注好友
     */
    @POST("friend/addFriend")
    suspend fun addFriend(@Body friend: Friend): ApiResponse<Objects>

    /**
     * 通过用户Id查看所有关注
     */
    @POST("friend/isFriend")
    suspend fun isFriend(@Body friend: Friend): ApiResponse<Friend>

}
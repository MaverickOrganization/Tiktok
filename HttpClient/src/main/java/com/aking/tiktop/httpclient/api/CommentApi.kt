package com.aking.tiktop.httpclient.api

import com.aking.data.ApiResponse
import com.aking.data.model.Comment
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentApi {

    /**
     * 分页获取作品评论
     */
    @POST("comment/getCommentInfo")
    suspend fun getCommentInfo(
        @Query("page") page: Int,
        @Query("rows") rows: Int,
        @Query("worksId") worksId: Long,
        @Query("parentCommentId") parentCommentId: Long
    ):
            ApiResponse<List<Comment>>

    /**
     * 发布作品
     */
    @POST("comment/addComment")
    suspend fun addComment(@Body comment: Comment): ApiResponse<String>

    /**
     * 发布作品
     */
    @POST("comment/deleteComment")
    suspend fun deleteComment(@Query("commentId") commentId: Long): ApiResponse<String>
}
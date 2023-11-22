package com.aking.tiktop.httpclient.api

import retrofit2.http.POST
import retrofit2.http.Query
import com.aking.data.ApiResponse
import com.aking.data.model.Works
import retrofit2.http.Body

interface WorksApi {

    /**
     * 获取用户发布所有作品
     */
    @POST("works/getAllUserWorks")
    suspend fun getAllUserWorks(@Query("userId") userId: Long, @Query("page") page: Int,
            @Query("rows") rows: Int): ApiResponse<List<Works>>

    /**
     * 获取所有作品
     */
    @POST("works/getAllWorks")
    suspend fun getAllWorks(@Query("page") page: Int, @Query("rows") rows: Int): ApiResponse<List<Works>>

    /**
     * 发布作品
     */
    @POST("works/addWorks")
    suspend fun addWorks(@Body works: Works): ApiResponse<String>

    /**
     * 发布作品
     */
    @POST("works/updateWorks")
    suspend fun updateWorks(@Body works: Works): ApiResponse<String>

    /**
     * 删除作品
     */
    @POST("works/deleteWorks")
    suspend fun deleteWorks(@Query("worksId") worksId: Long): ApiResponse<String>
}
package com.aking.data.model
data class Works(
    val worksId: Long,
    val userId: Long,
    val title: String,
    val url: String,
    val username: String,
    val userPic: String,
    val createTime: String,
    val updateTime: String,
    val page: Int,
    val rows: Int
)

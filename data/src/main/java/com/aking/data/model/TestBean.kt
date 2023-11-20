package com.aking.data.model


/**
 * Created by Rick at 2023-11-20 20:53.
 * Description:
 */
data class TestBean(
    val id: Long,
    val url: String,
    val avatar: Int,
    val music: Int,
    val name: String = "Tiktok",
    val description: String = "这是一条测试视频"
)
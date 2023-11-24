package com.aking.data.model

data class Comment(
    // 评论Id
    val commentId: Long,
    // 作品Id
    val worksId: Long,
    // 在该评论下接着评论
    val parentCommentId: Long,
    // 用户发布评论Id
    val userId: Long,
    // 回复的用户Id
    val replyUserId: Long,
    // @的用户集合
    val referredId: List<Long>,
    // 评论内容
    val content: String? = null,
    // 评论时间
    val createTime: String? = null,
    val page: Int ?= 0,
    val rows: Int ?= 0
)

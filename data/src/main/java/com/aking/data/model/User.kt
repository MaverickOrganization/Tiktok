package com.aking.data.model

data class User(
    // 用户id
    val userId: Long,
    // 用户名称
    val username: String?,
    // 密码
    val password: String,
    // 头像
    val userPic: String,
    // 电话
    val phone: String,
    // 性别
    val sex: Int,
    // 年龄
    val age: Int,
    // 简介
    val profile: String
)

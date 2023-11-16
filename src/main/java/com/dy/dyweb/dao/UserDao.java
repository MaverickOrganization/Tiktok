package com.dy.dyweb.dao;

import lombok.Data;

@Data
public class UserDao {

    // 抖音号
    private Long userId;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 图片
    private String userPic;

    // 电话
    private String phone;

    // 性别
    private int sex;

    // 年龄
    private int age;

    // 简介
    private String profile;
}

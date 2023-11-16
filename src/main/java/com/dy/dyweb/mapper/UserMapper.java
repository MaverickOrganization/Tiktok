package com.dy.dyweb.mapper;

import com.dy.dyweb.bean.User;
import com.dy.dyweb.dao.UserDao;

public interface UserMapper {

    UserDao login(User user);

    boolean register(User user);

    UserDao selectUser(Long userId);

    UserDao selectUserByPhone(String phone);
}

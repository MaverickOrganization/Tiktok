package com.dy.dyweb.service;

import com.dy.dyweb.bean.User;
import com.dy.dyweb.dao.UserDao;

public interface UserService {

    UserDao login(User user);

    boolean register(User User);

    UserDao selectUser(Long userId);

    UserDao selectUserByPhone(String phone);

}

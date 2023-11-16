package com.dy.dyweb.service.impl;

import com.dy.dyweb.bean.User;
import com.dy.dyweb.dao.UserDao;
import com.dy.dyweb.mapper.UserMapper;
import com.dy.dyweb.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDao login(User user) {
        return userMapper.login(user);
    }

    @Override
    public boolean register(User user) {
        return userMapper.register(user);
    }

    @Override
    public UserDao selectUser(Long userId) {
        return userMapper.selectUser(userId);
    }

    @Override
    public UserDao selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }
}

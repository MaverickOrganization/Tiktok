package com.dy.dyweb.service.impl;

import com.dy.dyweb.bean.Friend;
import com.dy.dyweb.bean.User;
import com.dy.dyweb.dao.FriendDao;
import com.dy.dyweb.dao.UserDao;
import com.dy.dyweb.mapper.FriendMapper;
import com.dy.dyweb.service.FriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    FriendMapper friendMapper;

    @Override
    public List<UserDao> selectAllFriend(Long userId) {
        return friendMapper.selectAllFriend(userId);
    }

    @Override
    public boolean deleteFriend(Friend friend) {
        return friendMapper.deleteFriend(friend);
    }

    @Override
    public boolean addFriend(Friend friend) {
        return friendMapper.addFriend(friend);
    }

    @Override
    public FriendDao isFriend(Friend friend) {
        return friendMapper.isFriend(friend);
    }
}

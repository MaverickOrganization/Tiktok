package com.dy.dyweb.service;

import com.dy.dyweb.bean.Friend;
import com.dy.dyweb.dao.FriendDao;
import com.dy.dyweb.dao.UserDao;

import java.util.List;

public interface FriendService {

    List<UserDao> selectAllFriend(Long userId);

    boolean deleteFriend(Friend friend);

    boolean addFriend(Friend friend);

    FriendDao isFriend(Friend friend);
}

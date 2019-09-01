package com.manong.community.service;

import com.manong.community.mapper.UserMapper;
import com.manong.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void InsertOrUpdate(User user) {
    User dbUser = userMapper.findByAccountId(user.getAccountId());
    if(dbUser == null){
        //插入
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        userMapper.insertUser(user);
    } else {
        //更新
        dbUser.setGmtModified(System.currentTimeMillis());
        dbUser.setAvatarUrl(user.getAvatarUrl());
        dbUser.setToken(user.getToken());
        dbUser.setName(user.getName());
        userMapper.update(dbUser);
    }
    }
}

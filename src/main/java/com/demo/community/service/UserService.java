package com.demo.community.service;

import com.demo.community.mapper.UserMapper;
import com.demo.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null){
            if (user.getName() == null) user.setName("fuckGitHub");
            userMapper.insert(user);
        }else{
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(user.getGmtModified());
            userMapper.update(dbUser);
        }
    }
}

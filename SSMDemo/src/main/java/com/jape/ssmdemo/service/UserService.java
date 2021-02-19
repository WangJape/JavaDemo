package com.jape.ssmdemo.service;

import com.jape.ssmdemo.dao.UserMapper;
import com.jape.ssmdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {



    @Autowired
    private UserMapper userMapper;

    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Transactional
    public int addOne(User user) {
        int insert = userMapper.insert(user);
        return insert;
    }

}

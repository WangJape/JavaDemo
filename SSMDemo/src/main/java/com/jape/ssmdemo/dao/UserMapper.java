package com.jape.ssmdemo.dao;


import com.jape.ssmdemo.entity.User;

public interface UserMapper {

    User getById(Integer id);

    int insert(User user);

}

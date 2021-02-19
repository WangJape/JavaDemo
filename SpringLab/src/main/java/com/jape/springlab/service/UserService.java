package com.jape.springlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private RoleService roleService;

    public UserService(){
        System.err.println("UserService实例化");
    }

}

package com.jape.springlab.service;

import com.jape.springlab.annotation.MyAnno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private RoleService roleService;

    public UserService(){
        System.err.println("UserService实例化");
    }

    @MyAnno
    public String getRole(){
        return null;
    }

}

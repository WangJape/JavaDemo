package com.jape.springlab.service;

import com.jape.springlab.annotation.MyAnno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private UserService userService;

        public RoleService(){
        System.err.println("RoleService实例化");
        System.err.println("file.encoding=" + System.getProperty("file.encoding"));
    }

    @MyAnno
    public String getUser(){
        return null;
    }

}
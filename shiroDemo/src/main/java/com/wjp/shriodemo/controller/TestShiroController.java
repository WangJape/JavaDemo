package com.wjp.shriodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestShiroController {

    //@RequiresGuest
    @GetMapping("401")
    public String unauthorizedInfo(){
        return "未授权访问";
    }

    //@RequiresGuest
    @PostMapping("doLogin")
    public String doLogin(String username,String password){
        Subject subject = SecurityUtils.getSubject();
        //用户名密码封装为shiro的Token
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //执行shiro登录方法
        try {
            subject.login(token);
        }catch (UnknownAccountException e){
            return "username";
        }catch (IncorrectCredentialsException e){
            return "password";
        }
        return "success";
    }

    /**
     * 登出
     */
    //@RequiresGuest
    @GetMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "logout";
    }

    @RequiresRoles("admin")
    @GetMapping("admin")
    public String admin(){
        return "admin";
    }


    @GetMapping("superadmin")
    public String superadmin(){
        return "superadmin";
    }
}

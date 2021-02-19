package com.wjp.springsecurityspringsessionsredisdemo.controller;


import com.wjp.springsecurityspringsessionsredisdemo.entity.Account;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Permission;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private AccountService accountService;
    //@PreAuthorize("permitAll()")
    //@PermitAll
    //@PreAuthorize("isAnonymous()")
    @GetMapping("test1")
    public ArrayList<Permission> test1(String username){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        Account account = new Account();
        account.setUserAccount(username);
        account = accountService.getById(account);
        System.err.println(account);

        ArrayList<Permission> userRoleAllPermission = accountService.getAccountRoleAllPermission(username);

        return userRoleAllPermission;
    }

    @PreAuthorize("hasRole('role:add')")
    @GetMapping("admin")
    public String admin(HttpSession session){
        String currentUsername = (String)session.getAttribute("username");
        System.err.println();
        return "admin权限页面" + currentUsername;
    }

    @PreAuthorize("hasRole('vuerouter:update')")
    @GetMapping("superadmin")
    public String superadmin(){
        return "superadmin权限页面";
    }

}

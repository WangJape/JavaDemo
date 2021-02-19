package com.wjp.springsecurityspringsessionsredisdemo.controller;

import com.wjp.springsecurityspringsessionsredisdemo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasRole('permission:list')")
    @GetMapping("getAllPermission")
    public List getAllPermission(){
        return permissionService.list();
    }

}

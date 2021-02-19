package com.wjp.springsecurityspringsessionsredisdemo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wjp.springsecurityspringsessionsredisdemo.entity.VueRouter;
import com.wjp.springsecurityspringsessionsredisdemo.service.VueRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VueRouterController {

    @Autowired
    private VueRouterService vueRouterService;

    @PreAuthorize("hasRole('vuerouter:list')")
    @GetMapping("getAllVueRouter")
    public List getAllVueRouter(){
        return vueRouterService.list(Wrappers.<VueRouter>lambdaQuery().orderByAsc(VueRouter::getVuerouterOrderscore));
    }


}

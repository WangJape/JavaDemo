package com.wjp.springsecurityspringsessionsredisdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjp.springsecurityspringsessionsredisdemo.entity.VueRouter;

import java.util.ArrayList;
import java.util.Map;

public interface VueRouterService extends IService<VueRouter> {

    ArrayList<VueRouter>  getUserRoleAllVueRouter(String account);

}

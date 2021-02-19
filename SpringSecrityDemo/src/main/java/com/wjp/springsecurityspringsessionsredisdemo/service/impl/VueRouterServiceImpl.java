package com.wjp.springsecurityspringsessionsredisdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjp.springsecurityspringsessionsredisdemo.entity.VueRouter;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.VueRouterMapper;
import com.wjp.springsecurityspringsessionsredisdemo.service.VueRouterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class VueRouterServiceImpl extends ServiceImpl<VueRouterMapper, VueRouter> implements VueRouterService {
    @Override
    public ArrayList<VueRouter>  getUserRoleAllVueRouter(String account) {
        ArrayList<VueRouter>  list = this.baseMapper.queryUserRoleAllVueRouter(account);
        return list;
    }
}

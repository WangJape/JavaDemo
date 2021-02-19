package com.wjp.springsecurityspringsessionsredisdemo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Account;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Permission;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.AccountMapper;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountService;
import com.wjp.springsecurityspringsessionsredisdemo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

/*    @Autowired
    private RedisUtils redisUtils;*/


    @Override
    public ArrayList<Permission> getAccountRoleAllPermission(String account) {
        return this.baseMapper.queryAccountRoleAllPermission(account);
    }

    @Override
    public ArrayList<Map<String, Object>> getAllAccountWithRoleByPage(Page page) {
        return this.baseMapper.queryAllAccountWithRoleByPage(page);
    }
}

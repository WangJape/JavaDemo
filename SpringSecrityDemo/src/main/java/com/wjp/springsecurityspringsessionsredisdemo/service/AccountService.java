package com.wjp.springsecurityspringsessionsredisdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Account;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Permission;

import java.util.ArrayList;
import java.util.Map;

public interface AccountService extends IService<Account> {

    ArrayList<Permission> getAccountRoleAllPermission(String account);

    ArrayList<Map<String,Object>> getAllAccountWithRoleByPage(Page page);
}


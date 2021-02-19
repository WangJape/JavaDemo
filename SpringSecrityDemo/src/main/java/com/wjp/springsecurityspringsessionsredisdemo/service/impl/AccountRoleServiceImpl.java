package com.wjp.springsecurityspringsessionsredisdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjp.springsecurityspringsessionsredisdemo.entity.AccountRole;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.AccountRoleMapper;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountRoleService;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleServiceImpl extends ServiceImpl<AccountRoleMapper, AccountRole> implements AccountRoleService {
}

package com.wjp.springsecurityspringsessionsredisdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Permission;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.PermissionMapper;
import com.wjp.springsecurityspringsessionsredisdemo.service.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}

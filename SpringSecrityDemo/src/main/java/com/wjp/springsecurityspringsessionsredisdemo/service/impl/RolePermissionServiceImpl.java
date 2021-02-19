package com.wjp.springsecurityspringsessionsredisdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjp.springsecurityspringsessionsredisdemo.entity.RolePermission;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.RolePermissionMapper;
import com.wjp.springsecurityspringsessionsredisdemo.service.RolePermissionService;
import org.springframework.stereotype.Service;


@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}

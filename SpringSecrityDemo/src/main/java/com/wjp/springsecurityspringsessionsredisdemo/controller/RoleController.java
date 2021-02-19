package com.wjp.springsecurityspringsessionsredisdemo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wjp.springsecurityspringsessionsredisdemo.entity.AccountRole;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Role;
import com.wjp.springsecurityspringsessionsredisdemo.entity.RolePermission;
import com.wjp.springsecurityspringsessionsredisdemo.entity.RoleVueRouter;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountRoleService;
import com.wjp.springsecurityspringsessionsredisdemo.service.RolePermissionService;
import com.wjp.springsecurityspringsessionsredisdemo.service.RoleService;
import com.wjp.springsecurityspringsessionsredisdemo.service.RoleVueRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RoleController {

    @Autowired
    private AccountRoleService accountRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RoleVueRouterService roleVueRouterService;

    @PreAuthorize("hasRole('role:list')")
    @GetMapping("getAllRoles")
    public List<Role> getAllRoles(){
        List<Role> list = roleService.list();
        return list;
    }

    @PreAuthorize("hasRole('role:add')")
    @PostMapping("addRole")
    public String addRole(Role role){
        Role existRole = roleService.getOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleCode,role.getRoleCode())
                .or()
                .eq(Role::getRoleName,role.getRoleName()));
        if(existRole != null){
            return "角色已经存在";
        }
        if(roleService.save(role)){
            return "success";
        }
        else{
            return "添加失败";
        }
    }

    @PreAuthorize("hasRole('role:delete')")
    @PostMapping("deleteRole")
    public String deleteRole(Role role){
        //检查是否还有账号拥有此角色
        int accountUseRoleCount = accountRoleService.count(Wrappers.<AccountRole>lambdaQuery()
                .eq(AccountRole::getRoleCode,role.getRoleCode()));
        if(accountUseRoleCount > 0){
            return "此角色还有账户在使用！";
        }
        if(roleService.removeById(role.getRid())){
            return "success";
        }
        else{
            return "删除失败";
        }
    }

    @PreAuthorize("hasRole('role:update')")
    @PostMapping("updateRole")
    public String updateRole(Role role){
        Role existRole = roleService.getOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleName,role.getRoleName()));
        if(existRole != null){
            return "角色已经存在";
        }
        if(roleService.updateById(role)){
            return "success";
        }
        else{
            return "更新失败";
        }
    }


    @PreAuthorize("hasRole('permission:list')")
    @GetMapping("getRolePermission")
    public List getRolePermission(String roleCode){
        return rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleCode,roleCode));
    }

    @PreAuthorize("hasRole('permission:update')")
    @PostMapping("updateRolePermission")
    public String updateRolePermission(String roleCode, String[] roleAddPermission, String[] roleDeletePermission){
        System.err.println(roleCode);
        RolePermission rolePermission;
        if(roleAddPermission != null){
            for (int i = 0; i < roleAddPermission.length; i++) {
                System.err.println("添加权限" + i + ":" + roleAddPermission[i]);
            }
            System.err.println();
            List<RolePermission> addList = new ArrayList<>();
            for(String permissionCode:roleAddPermission){
                rolePermission = new RolePermission();
                rolePermission.setRoleCode(roleCode);
                rolePermission.setPermissionCode(permissionCode);
                addList.add(rolePermission);
            }
            rolePermissionService.saveBatch(addList);
        }
        if(roleDeletePermission != null){
            for (int i = 0; i < roleDeletePermission.length; i++) {
                System.err.println("删除权限" + i + ":" + roleDeletePermission[i]);
            }
            rolePermission = new RolePermission();
            for(String permissionCode:roleDeletePermission){
                rolePermission.setRoleCode(roleCode);
                rolePermission.setPermissionCode(permissionCode);
                rolePermissionService.remove(Wrappers.lambdaQuery(rolePermission));
            }

        }
        return "success";
    }

    @PreAuthorize("hasRole('vuerouter:list')")
    @GetMapping("getRoleVueRouter")
    public List getRoleVueRouter(String roleCode){
        return roleVueRouterService.list(Wrappers.<RoleVueRouter>lambdaQuery().eq(RoleVueRouter::getRoleCode,roleCode));
    }

    @PreAuthorize("hasRole('vuerouter:update')")
    @PostMapping("updateRoleVueRouter")
    public String updateRoleVueRouter(String roleCode, String[] roleAddVueRouter, String[] roleDeleteVueRouter){
        System.err.println(roleCode);
        RoleVueRouter roleVueRouter;
        if(roleAddVueRouter != null){
            for (int i = 0; i < roleAddVueRouter.length; i++) {
                System.err.println("添加路由菜单" + i + ":" + roleAddVueRouter[i]);
            }
            System.err.println();
            List<RoleVueRouter> addList = new ArrayList<>();
            for(String vuerouterPath:roleAddVueRouter){
                roleVueRouter = new RoleVueRouter();
                roleVueRouter.setRoleCode(roleCode);
                roleVueRouter.setVuerouterPath(vuerouterPath);
                addList.add(roleVueRouter);
            }
            roleVueRouterService.saveBatch(addList);
        }
        if(roleDeleteVueRouter != null){
            for (int i = 0; i < roleDeleteVueRouter.length; i++) {
                System.err.println("删除路由菜单" + i + ":" + roleDeleteVueRouter[i]);
            }
            roleVueRouter = new RoleVueRouter();
            for(String vuerouterPath:roleDeleteVueRouter){
                roleVueRouter.setRoleCode(roleCode);
                roleVueRouter.setVuerouterPath(vuerouterPath);
                roleVueRouterService.remove(Wrappers.lambdaQuery(roleVueRouter));
            }
        }
        return "success";
    }

}

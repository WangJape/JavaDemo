package com.wjp.springsecurityspringsessionsredisdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjp.springsecurityspringsessionsredisdemo.config.MybatisRedisCache;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Account;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Permission;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Map;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface AccountMapper extends BaseMapper<Account> {

    @Select("<script>"+
            "SELECT rp.permission_code " +
            "from account a " +
            "left join account_role ar on a.user_account = ar.user_account " +
            "left join role_permission rp on ar.role_code = rp.role_code " +
            "<if test='account!=null' > " +
                "where a.user_account = #{account} " +
            "</if>" +
            "</script>")
    ArrayList<Permission> queryAccountRoleAllPermission(@Param("account") String account);

    @Results({
            @Result(column = "user_account",property = "userAccount"),
            @Result(column = "user_nickname",property = "userNickname"),
            @Result(column = "user_createtime",property = "userCreatetime"),
            @Result(column = "role_code",property = "roleCode"),
            @Result(column = "role_name",property = "roleName"),
    })
    @Select("SELECT a.aid,a.user_account,a.user_nickname,a.user_createtime," +
                   "ar.arid,r.role_code,r.role_name " +
            "FROM `account` a " +
            "LEFT JOIN account_role ar on a.user_account = ar.user_account " +
            "LEFT JOIN role r on ar.role_code = r.role_code " +
            "ORDER BY a.user_createtime DESC ")
    ArrayList<Map<String,Object>> queryAllAccountWithRoleByPage(Page page);


}

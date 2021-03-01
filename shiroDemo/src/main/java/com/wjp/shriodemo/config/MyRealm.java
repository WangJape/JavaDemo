package com.wjp.shriodemo.config;

import com.wjp.shriodemo.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 *  shiro访问数据库的工具
 */
public class MyRealm extends AuthorizingRealm {

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        System.err.println("shiro用户认证:验证账号["+ token.getUsername() +"]密码：["+ token.getPassword() +"]");

        //访问数据库
        User user = new User("jape","970501","admin","admin");
        String username1 = "wei";
        String password1 = "182320";

        //用户是否存在，手动验证
        if (token.getUsername().equals(user.getUsername())) {
            //由shiro托管完成密码验证
            return new SimpleAuthenticationInfo(user, user.getPaaword(), user.getUsername());
        }else{
            throw new UnknownAccountException("账户不存在!");
        }

    }

    //每次访问有权限的请求时，从数据库查，然后找权限。
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("shiro用户授权：为用户["+ principals.getRealmNames() +"]授权");

        User user = (User)principals.getPrimaryPrincipal();
        System.err.println("用户当前角色:" + user.getRole());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(user.getRole());
        //info.addStringPermission(user.getPermission());

        return info;
    }

}
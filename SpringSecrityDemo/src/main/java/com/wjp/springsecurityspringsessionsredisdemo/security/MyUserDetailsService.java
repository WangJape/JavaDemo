package com.wjp.springsecurityspringsessionsredisdemo.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Account;
import com.wjp.springsecurityspringsessionsredisdemo.entity.AccountRole;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Permission;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.AccountRoleMapper;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountRoleService;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRoleService accountRoleService;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        System.err.println("登录类型" + request.getParameter("loginType"));
        System.err.println("认证方式" + request.getParameter("authcMode"));

        Account account = new Account();
        account.setUserAccount(userAccount);
        account = accountService.getOne(Wrappers.lambdaQuery(account));
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        /*AccountRole accountRole = new AccountRole();
        accountRole.setUserAccount(userAccount);
        accountRole = accountRoleService.getOne(Wrappers.lambdaQuery(accountRole));
        session.setAttribute("roleCode",accountRole.getRoleCode());*/
        List<Permission> userRoleAllPermission = accountService.getAccountRoleAllPermission(userAccount);
        List<GrantedAuthority> authList = new ArrayList();
        if(userRoleAllPermission.size() != 1 || userRoleAllPermission.get(0) != null){//判断是不是有权限，有就加进去
            for( Permission permission : userRoleAllPermission){
                //角色信息存储的时候可以没有"ROLE_"前缀，但是包装成GrantedAuthority对象的时候必须要有
                // 因为在SecurityExpressionRoot类中有属性private String defaultRolePrefix = "ROLE_";
                System.err.println("为用户["+ userAccount +"]加入权限["+permission.getPermissionCode()+"]");
                authList.add(new SimpleGrantedAuthority("ROLE_"+ permission.getPermissionCode() ));
            }
        }

        String password = new BCryptPasswordEncoder().encode(account.getUserPassword());

        UserDetails userDetails = new User(userAccount, password,true,true,true,true,authList);
        return userDetails;
    }
}

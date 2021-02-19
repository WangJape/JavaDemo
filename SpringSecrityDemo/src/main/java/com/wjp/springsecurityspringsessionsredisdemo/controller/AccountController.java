package com.wjp.springsecurityspringsessionsredisdemo.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Account;
import com.wjp.springsecurityspringsessionsredisdemo.entity.AccountRole;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountRoleService;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRoleService accountRoleService;

    @PreAuthorize("hasRole('user:list')")
    @GetMapping("getAllAccountInfo")
    public Page getAllAccountInfo(Page page){
        page.setRecords(accountService.getAllAccountWithRoleByPage(page));
        //page.setTotal(accountService.count());//page会自动算出来
        return page;
    }

    @PreAuthorize("hasRole('user:add')")
    @PostMapping("addAccountWithRole")
    public String addAccountWithRole(Account account,AccountRole accountRole){
        System.err.println(account.toString());
        System.err.println(accountRole.toString());

        if(accountService.count(Wrappers.<Account>lambdaUpdate()
                .eq(Account::getUserAccount,account.getUserAccount())) == 1){
            return "此账号已被使用";
        }

        accountService.save(account);
        accountRoleService.save(accountRole);
        return "success";
    }

    @PreAuthorize("hasRole('user:update')")
    @PostMapping("updateAccountWithRole")
    public String updateAccountWithRole(Account account,AccountRole accountRole){
        System.err.println(account.toString());
        System.err.println(accountRole.toString());
        if(account.getUserNickname() != null || account.getUserPassword() != null){
            accountService.updateById(account);
        }
        if(accountRole.getRoleCode() != null){
            accountRoleService.updateById(accountRole);
        }
        return "success";
    }

    @PreAuthorize("hasRole('user:delete')")
    @PostMapping("deleteAccountWithRole")
    public String deleteAccountWithRole(Account account,AccountRole accountRole){
        if(accountRoleService.removeById(accountRole.getArid())){
            if(accountService.removeById(account.getAid())){
                return "success";
            }else{
                return "删除用户失败，但用户角色已删除";
            }
        }else{
            return "删除用户角色失败，删除用户已取消";
        }
    }

}

















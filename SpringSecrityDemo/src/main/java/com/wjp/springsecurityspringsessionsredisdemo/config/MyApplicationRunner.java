package com.wjp.springsecurityspringsessionsredisdemo.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Account;
import com.wjp.springsecurityspringsessionsredisdemo.entity.AccountRole;
import com.wjp.springsecurityspringsessionsredisdemo.entity.Permission;
import com.wjp.springsecurityspringsessionsredisdemo.entity.VueRouter;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.AccountMapper;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.AccountRoleMapper;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.RoleMapper;
import com.wjp.springsecurityspringsessionsredisdemo.mapper.VueRouterMapper;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountRoleService;
import com.wjp.springsecurityspringsessionsredisdemo.service.AccountService;
import com.wjp.springsecurityspringsessionsredisdemo.service.RoleService;
import com.wjp.springsecurityspringsessionsredisdemo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/*@SuppressWarnings("all")
@Component
@Order(100)
public class MyApplicationRunner implements ApplicationRunner {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AccountRoleMapper accountRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private VueRouterMapper vueRouterMapper;

    @Autowired
    private RedisUtils redisUtils;



    *//**
     * args.getNonOptionArgs();可以用来获取命令行中的无key参数（和CommandLineRunner一样）。
     * args.getOptionNames();可以用来获取所有key/value形式的参数的key。
     * args.getOptionValues(key));可以根据key获取key/value 形式的参数的value。
     * args.getSourceArgs(); 则表示获取命令行中的所有参数。
     *//*
    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Account> allAccount = accountMapper.selectList(Wrappers.emptyWrapper());
        HashMap map = new HashMap<>();
        StringBuffer string = new StringBuffer();
        for(Account account:allAccount){
            map.put( "aid",account.getAid());
            map.put( "userAccount",account.getUserAccount());
            map.put( "userPassword",account.getUserPassword());
            map.put( "userNickname",account.getUserNickname());
            map.put( "userCreatetime",account.getUserCreatetime());
            string.append("account:" );
            string.append(account.getUserAccount());
            redisUtils.hmset(string.toString() ,map);
            string.setLength(0);//清空
        }

        Set keys = redisUtils.keys("account:*");
        System.err.println(keys);
        for(Object key:keys){
            System.err.println(redisUtils.hmget((String)key));
            redisUtils.del((String)key);
        }
    }
}*/
















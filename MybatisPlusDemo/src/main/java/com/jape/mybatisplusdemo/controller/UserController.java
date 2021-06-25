package com.jape.mybatisplusdemo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jape.mybatisplusdemo.entity.User;
import com.jape.mybatisplusdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Jape
 * @since 2021-06-25
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getList")
    public Object getList(int pageNo, int pageSize) {
        log.info("入参[{}],[{}]", pageNo, pageSize);
        Page<User> page = new Page<>(pageNo, pageSize);
        IPage<User> userIPage = userService.page(page);
        log.info("分页查询结果[{}]", userIPage);
        return userIPage;
    }

    @GetMapping("getPage")
    public Object getPage(int pageNo, int pageSize) {
        log.info("入参[{}],[{}]", pageNo, pageSize);
        Page<User> page = new Page<>(pageNo, pageSize);
        //在service中已将list封装进page
        List<User> users = userService.selectByPage(page);
        log.info("分页查询结果[{}]", page);
        return page;
    }

    @GetMapping("getOne/{id}")
    public Object getOne(@PathVariable String id) {
        log.info("入参[{}]", id);
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUuid, id));
        log.info("查询结果[{}]", user);
        return user;
    }


}


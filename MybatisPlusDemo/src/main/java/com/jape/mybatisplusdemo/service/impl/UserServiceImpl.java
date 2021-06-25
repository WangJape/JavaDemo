package com.jape.mybatisplusdemo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jape.mybatisplusdemo.entity.User;
import com.jape.mybatisplusdemo.mapper.UserMapper;
import com.jape.mybatisplusdemo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Jape
 * @since 2021-06-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> selectByPage(Page<User> page) {
        List<User> users = baseMapper.selectByPage(page);
        page.setRecords(users);
        return users;
    }
}

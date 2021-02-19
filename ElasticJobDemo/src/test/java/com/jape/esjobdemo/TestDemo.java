package com.jape.esjobdemo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jape.esjobdemo.entity.User;
import com.jape.esjobdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试
 *
 * @author Jape
 * @since 2020/11/15 18:37
 */
@SpringBootTest
public class TestDemo {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testPageHelper() {
        PageHelper.startPage(1, 100, true);
        List<User> users = userMapper.selectAll();
        PageInfo<User> pageInfo = (PageInfo<User>) users;
        System.err.println(pageInfo);
    }

    @Test
    public void testPageQuery() {
        List<User> users = userMapper.selectByPage(0, 100);
        System.err.println(users);
    }

}

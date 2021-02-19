package com.jape.shardingjdbcdemo;

import com.jape.shardingjdbcdemo.dao.UserMapper;
import com.jape.shardingjdbcdemo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void test1() {
        for (int i = 0; i < 12; i++) {
            User user = new User();
            user.setName("test" + i);
            user.setCityId(i);
            user.setSex(i > 5 ? 1 : 2);
            user.setPhone("11111111" + i);
            user.setEmail("xxxxx");
            user.setCreateTime(new Date());
            user.setPassword("eeeeeeeeeeee");
            userMapper.save(user);
        }
    }   

    @Test
    void test2() {
        User user = userMapper.get(522814919616233472L);
        System.err.println(user);
    }

}

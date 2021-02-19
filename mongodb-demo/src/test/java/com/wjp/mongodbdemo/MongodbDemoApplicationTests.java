package com.wjp.mongodbdemo;

import com.wjp.mongodbdemo.dao.UserRepository;
import com.wjp.mongodbdemo.entity.User;
import com.wjp.mongodbdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class MongodbDemoApplicationTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    void insertOne() {
        User user = new User(null, "Jape", "男", 0, 1);
        userRepository.insert(user);
    }
    @Test
    void insertMany() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("name"+i);
            if(i%2==0){
                user.setSex("女");
            }else {
                user.setSex("男");
            }
            user.setPraise(0);
            user.setStatus(1);
            list.add(user);
        }
        userRepository.insert(list);
    }

    @Test
    void findAll() {
        System.err.println(userRepository.findAll().toString());
    }

    @Test
    void findByName() {
        System.err.println(userRepository.findByName("Jape").toString());
    }

    @Test
    void updatePraiseNumByName() {
        String name = "name5";
        userService.updatePraiseNumByName(name);
        System.err.println(userRepository.findByName(name).toString());
    }

    @Test
    void updatePraiseNumByNameIsLike() {
        String name = "name";
        userService.updatePraiseNumByNameIsLike(name);
        System.err.println(userRepository.findByNameIsLike(name).toString());
    }

}

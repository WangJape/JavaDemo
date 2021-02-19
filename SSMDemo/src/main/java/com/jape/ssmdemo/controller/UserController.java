package com.jape.ssmdemo.controller;

import com.jape.ssmdemo.entity.User;
import com.jape.ssmdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    Logger log = LoggerFactory.getLogger(Object.class);

    @Autowired
    private UserService userService;

    @GetMapping("/getById/{id}")
    @ResponseBody
    public String getUser(@PathVariable int id) {
        User user = userService.getById(id);
        return user.toString();
    }

    @GetMapping("/addOne")
    @ResponseBody
    public String addOne() throws ParseException {
        log.info("执行插入");
        User user = new User();
        user.setName("赵伟");
        user.setSex("0");
        user.setQq("2577353996");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        Date date = simpleDateFormat.parse("1997-10-27");
        user.setBirthday(date);
        userService.addOne(user);
        return "成功";
    }
}

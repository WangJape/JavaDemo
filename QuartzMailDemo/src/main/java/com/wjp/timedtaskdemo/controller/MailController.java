package com.wjp.timedtaskdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mail")
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("sendQQMail")
    @ResponseBody
    public String sendQQMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("114@qq.com");
        message.setTo("2144@qq.com");
        message.setSubject("邮件发送测试");
        message.setText("邮件发送测试内容，邮件发送测试内容");
        javaMailSender.send(message);
        return "Success";
    }

}

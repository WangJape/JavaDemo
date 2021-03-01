package com.wjp.springcloudalibabanacosclientprovider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Test1Controller {

    @Value("${spring.application.name}")
    private String serverName;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("testCluster")
    public HashMap testCluster() {
        HashMap res = new HashMap();
        res.put("服务器名称", serverName);
        res.put("服务器端口", serverPort);
        res.put("请求状态", "Request success");
        System.err.println(res);
        return res;
    }

    @RequestMapping("testTimeout")
    public HashMap testTimeout() throws InterruptedException {
        Thread.sleep(3000);
        HashMap res = new HashMap();
        res.put("服务器名称", serverName);
        res.put("服务器端口", serverPort);
        res.put("请求状态", "测试超时，访问成功");
        System.err.println(res);
        return res;
    }


}

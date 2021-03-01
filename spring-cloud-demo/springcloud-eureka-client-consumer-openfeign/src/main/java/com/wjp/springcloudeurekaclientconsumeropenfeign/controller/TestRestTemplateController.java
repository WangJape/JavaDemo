package com.wjp.springcloudeurekaclientconsumeropenfeign.controller;

import com.wjp.springcloudeurekaclientconsumeropenfeign.openfeign.ProviderClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestRestTemplateController {

    @Resource
    private ProviderClient providerClient;

    @RequestMapping("testProviderCluster")
    @ResponseBody
    public HashMap testProviderCluster(){
        HashMap result = providerClient.testCluster();
        System.err.println("消费者接收到：" + result);
        return result;
    }

    @RequestMapping("testFeignTimeout")
    @ResponseBody
    public HashMap testFeignTimeout(){
        HashMap result = providerClient.testTimeout();
        System.err.println("消费者接收到：" + result);
        return result;
    }

}

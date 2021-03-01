package com.wjp.springcloudalibabanacosconfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope //支持nacos的动态刷新
@RestController
public class LookConfigController {

    @Value("${info}")
    private String info;

    @GetMapping("getInfo")
    public String getInfo(){
        return info;
    }

}

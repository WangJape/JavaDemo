package com.wjp.springcloudconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController("/config")
public class LookConfigController {

    @Value("${info}")
    private String info;

    @GetMapping("getInfo")
    public String getInfo(){
        return info;
    }

}

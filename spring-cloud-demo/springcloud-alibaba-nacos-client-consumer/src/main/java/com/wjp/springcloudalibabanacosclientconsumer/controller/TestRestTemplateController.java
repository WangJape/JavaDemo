package com.wjp.springcloudalibabanacosclientconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestRestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("testProviderCluster")
    public String testProviderCluster(){
        String requestUrl = "http://springcloud-alibaba-nacos-client-provider/testCluster";
        String result  = restTemplate.getForObject(requestUrl, String.class);
        System.err.println("消费者接收到：" + result);
        return result;
    }

}

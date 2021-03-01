package com.wjp.springcloudeurekaclient.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestRestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("testProviderCluster")
    @ResponseBody
    public String testProviderCluster(){
        String requestUrl = "http://eureka-client-provider/testCluster";
        String result  = restTemplate.getForObject(requestUrl, String.class);
        System.err.println("消费者接收到：" + result);
        return result;
    }


    @RequestMapping("testRestTemplateGet")
    @ResponseBody
    public String testRestTemplateGet(){
        String requestUrl = "http://eureka-client-provider/testProviderRestGet";
        //模拟get请求,第一个参数是链接和参数，第二个是期望的返回对象的类 如String.class
        String requestResult =  restTemplate.getForObject( requestUrl , String.class);
        System.err.println("消费者接收到：" + requestResult);
        return requestResult;
    }

    @RequestMapping("testRestTemplatePost")
    @ResponseBody
    public String testRestTemplatePost(){
        String requestUrl = "http://eureka-client-provider/testProviderRestPost";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name","wjp");
        paramMap.add("age","23");
        //模拟post请求,第一个参数是url， 第二个参数是 传的参数的实体类，第三个是期望的返回类型。
        String requestResult = restTemplate.postForObject( requestUrl, paramMap, String.class);
        System.err.println("消费者接收到：" + requestResult);
        return requestResult;
    }

    @RequestMapping("testRestTemplateDelete")
    @ResponseBody
    public String testRestTemplateDelete(){
        String requestUrl = "http://eureka-client-provider/testProviderRestDelete";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name","wjp");
        paramMap.add("age","23");
        HttpEntity param = new HttpEntity<String[]>(paramMap);
        //模拟delete请求
        ResponseEntity<String> resp  = restTemplate.exchange( requestUrl, HttpMethod.DELETE, param, String.class);
        System.err.println("消费者接收到：" + resp.getBody());
        return resp.getBody();
    }

}

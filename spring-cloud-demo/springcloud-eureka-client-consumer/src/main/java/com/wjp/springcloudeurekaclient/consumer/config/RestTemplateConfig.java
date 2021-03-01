package com.wjp.springcloudeurekaclient.consumer.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Autowired
    RestTemplateBuilder restTemplateBuilider;

    /**
     * 配置restTemplate
     * @return
     */
    @Bean
    @LoadBalanced //启用Ribbon负载均衡，默认轮询
    public RestTemplate restTemplate() {
        //使用build()方法进行获取RestTemplate实例
        return restTemplateBuilider.build();
    }



}

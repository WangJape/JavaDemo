package com.wjp.springcloudalibabanacosclientconsumer.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * 配置restTemplate
     * @return
     */
    @Bean
    @LoadBalanced //启用Ribbon负载均衡，默认轮询
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }



}

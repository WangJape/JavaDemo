package com.wjp.springcloudalibabanacosclientconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudAlibabaNacosClientConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudAlibabaNacosClientConsumerApplication.class, args);
    }

}

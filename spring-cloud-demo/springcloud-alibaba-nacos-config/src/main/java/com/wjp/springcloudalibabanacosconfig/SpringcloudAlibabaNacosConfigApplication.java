package com.wjp.springcloudalibabanacosconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudAlibabaNacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudAlibabaNacosConfigApplication.class, args);
    }

}

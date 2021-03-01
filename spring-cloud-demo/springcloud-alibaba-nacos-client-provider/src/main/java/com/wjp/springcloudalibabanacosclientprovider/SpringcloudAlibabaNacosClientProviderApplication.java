package com.wjp.springcloudalibabanacosclientprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudAlibabaNacosClientProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudAlibabaNacosClientProviderApplication.class, args);
    }

}

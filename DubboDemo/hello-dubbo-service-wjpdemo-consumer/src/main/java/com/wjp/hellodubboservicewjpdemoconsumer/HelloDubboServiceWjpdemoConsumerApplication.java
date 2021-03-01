package com.wjp.hellodubboservicewjpdemoconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class HelloDubboServiceWjpdemoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloDubboServiceWjpdemoConsumerApplication.class, args);
    }

}

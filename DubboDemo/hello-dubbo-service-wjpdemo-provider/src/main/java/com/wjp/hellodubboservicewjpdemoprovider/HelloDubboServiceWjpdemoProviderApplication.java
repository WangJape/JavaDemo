package com.wjp.hellodubboservicewjpdemoprovider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@SpringBootApplication
public class HelloDubboServiceWjpdemoProviderApplication {

    public static void main(String[] args) {
        //无需web支持，直接用builder
        new SpringApplicationBuilder(HelloDubboServiceWjpdemoProviderApplication.class).run(args);
        //SpringApplication.run(HelloDubboServiceWjpdemoProviderApplication.class, args);
    }

}

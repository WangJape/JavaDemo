package com.jape.ibmmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class IbmMqDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(IbmMqDemoApplication.class, args);
    }

}

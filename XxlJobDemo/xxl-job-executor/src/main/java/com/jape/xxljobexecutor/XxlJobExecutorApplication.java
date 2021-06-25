package com.jape.xxljobexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.locks.LockSupport;

@SpringBootApplication
public class XxlJobExecutorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(XxlJobExecutorApplication.class, args);
        //不让应用直接死
        LockSupport.park();
    }

}

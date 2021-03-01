package com.jape.springbootdemo;

import com.jape.springbootdemo.listener.PubEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDemoApplicationTests {

    @Autowired
    PubEventService pubEventService;

    @Test
    void contextLoads() throws InterruptedException {
        pubEventService.pubMyEvent("我是消息体");
        Thread.sleep(5000);
    }

}

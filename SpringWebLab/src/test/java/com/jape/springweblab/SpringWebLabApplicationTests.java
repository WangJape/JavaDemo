package com.jape.springweblab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class SpringWebLabApplicationTests {


    @Test
    void contextLoads() {
        RestTemplate restTemplate = new RestTemplate();

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            String rsp = restTemplate.getForObject("http://127.0.0.1/", String.class);
            System.out.println(Thread.currentThread().getName() + ">>>>>>" + rsp);
        }


    }

}

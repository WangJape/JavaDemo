package com.jape.springbootdemo.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PubEventService {

    @Autowired
    private ApplicationContext applicationContext;

    public void pubMyEvent(String msg){
        System.err.println("发布事件...");
        MyEvent event = new MyEvent(applicationContext, msg);
        applicationContext.publishEvent(event);
    }

}

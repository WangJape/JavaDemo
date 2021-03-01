package com.jape.springbootdemo.listener;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

    private final String msg;

    public MyEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

package com.jape.springlab;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextApplication {

    public static void main(String[] args) {

        //spring容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringLabApplication.class);

        System.err.println(applicationContext.getBeanDefinitionCount());

    }

}

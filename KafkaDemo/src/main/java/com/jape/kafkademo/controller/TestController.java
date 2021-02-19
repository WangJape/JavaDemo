package com.jape.kafkademo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class TestController {



    @GetMapping("/test1")
    public String test1() {
        return "ok";
    }

}

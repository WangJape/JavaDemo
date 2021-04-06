package com.jape.httpdemoserver.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/ping")
    public String ping(@RequestParam String name) {
        System.err.println(name);
        return "pong";
    }

    @PostMapping("/doPost")
    public String doPost(@RequestBody String json) {
        System.err.println(json);
        return "nice";
    }

}

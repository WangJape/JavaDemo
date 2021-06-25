package com.jape.springweblab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: 王建朋
 * @Date: 2020/9/15 18:00
 */
@RestController
public class TestController {

    @GetMapping("/getSysEncode")
    public String getSysEncode() {
        return System.getProperty("file.encoding");
    }


}

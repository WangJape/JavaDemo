package com.wjp.hellodubboservicewjpdemoconsumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wjp.dubbo.demo.api.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class wjpDemoController {

    @Reference(version = "1.0.0")
    private DemoService demoService;

    @HystrixCommand(fallbackMethod = "reqError")//指定断路后的回调方法（回调方法必须与原方法参数类型相同、返回值类型相同、方法名可以不同）
    @RequestMapping("demo1")
    //@ResponseBody
    public String sayHi(){
        return demoService.sayHi();
    }
    public String reqError(){
        return "请求处理出错了！";
    }

}

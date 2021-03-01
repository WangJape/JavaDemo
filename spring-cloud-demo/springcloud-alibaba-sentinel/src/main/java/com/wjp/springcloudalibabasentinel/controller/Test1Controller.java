package com.wjp.springcloudalibabasentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wjp.springcloudalibabasentinel.sentinel.BlockHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Test1Controller {

    @Value("${spring.application.name}")
    private String serverName;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("testCluster")
    public HashMap testCluster() {
        HashMap res = new HashMap();
        res.put("服务器名称", serverName);
        res.put("服务器端口", serverPort);
        res.put("请求状态", "Request success");
        System.err.println(res);
        return res;
    }

    @RequestMapping("testTimeout")
    public HashMap testTimeout() throws InterruptedException {
        Thread.sleep(3000);
        HashMap res = new HashMap();
        res.put("服务器名称", serverName);
        res.put("服务器端口", serverPort);
        res.put("请求状态", "测试超时，访问成功");
        System.err.println(res);
        return res;
    }

    @RequestMapping("/testException")
    @SentinelResource(value = "testException",
            fallback = "deal_RuntimeException",
            blockHandler = "deal_BlockException")
    public String testException() {
        int age = 10/0;
        return "不可能到这里";
    }
    public String deal_RuntimeException(Throwable e){
        return "/testException 程序逻辑错误，熔断保护了:" + e.getMessage();
    }
    public String deal_BlockException(BlockException e){
        return "/testException 接口配置熔断保护:" + e.getMessage();
    }

    @GetMapping("/testGlobalBlockHandler")
    @SentinelResource(value = "testGlobalBlockHandler",
            blockHandlerClass = BlockHandler.class,
            blockHandler = "handler429")
    public String testGlobalBlockHandler() {
        return "success:/testGlobalBlockHandler";
    }

}

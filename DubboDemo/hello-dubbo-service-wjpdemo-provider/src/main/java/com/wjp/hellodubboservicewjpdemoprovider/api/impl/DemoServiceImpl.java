package com.wjp.hellodubboservicewjpdemoprovider.api.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wjp.dubbo.demo.api.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "1.0.0")
public class DemoServiceImpl implements DemoService {

    @Value("${dubbo.protocol.port}")
    private String port;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),//当在配置时间窗口内达到此数量的失败后，进行短路，默认20个
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),//短路多久以后开始尝试是否恢复，默认5s
    })
    @Override
    public String sayHi() {
        //throw new RuntimeException("测试熔断使用的异常");
        return "服务提供者返回的数据" + port;
    }
}

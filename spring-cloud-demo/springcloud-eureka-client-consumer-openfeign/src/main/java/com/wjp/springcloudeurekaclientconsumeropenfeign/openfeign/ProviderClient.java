package com.wjp.springcloudeurekaclientconsumeropenfeign.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Component
@FeignClient(value = "EUREKA-CLIENT-PROVIDER")
public interface ProviderClient {

    @GetMapping("testCluster")
    HashMap testCluster();

    @GetMapping("testTimeout")
    HashMap testTimeout();

}

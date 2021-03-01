package com.wjp.springcloudeurekaclientconsumeropenfeign.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RibbonConfig {

    /**
     * 配置Ribbon负载均衡策略，默认轮询
     * com.netflix.loadbalancer.RoundRobinRule  - 轮询
     * com.netflix.loadbalancer.RandomRule  - 随机
     * com.netflix.loadbalancer.RetryRule - 重试，先按RoundRobinRule进行轮询，如果失败就在指定时间内进行重试
     * com.netflix.loadbalancer.WeightedResponseTimeRule - 权重，响应速度越快，权重越大，越容易被选中。
     * com.netflix.loadbalancer.BestAvailableRule  - 先过滤掉不可用的处于断路器跳闸转态的服务，然后选择一个并发量最小的服务
     * com.netflix.loadbalancer.AvailabilityFilteringRule - 先过滤掉故障实例，再选择并发量较小的实例
     * com.netflix.loadbalancer.ZoneAvoidanceRule - 默认规则，复合判断server所在区域的性能和server的可用性进行服务的选择。
     * @return
     */
    @Bean
    public IRule myRule(){
        return new RandomRule(); // 改为随机算法规则
    }

}

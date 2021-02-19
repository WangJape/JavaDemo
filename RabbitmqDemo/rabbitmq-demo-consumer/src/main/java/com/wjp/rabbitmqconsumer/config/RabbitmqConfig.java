package com.wjp.rabbitmqconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String EXCHANGE = "TranslationRecords";
    public static final String ROUTER_KEY = "topic.test";

    /**
     * exchange是交换机交换机的主要作用是接收相应的消息并且绑定到指定的队列.交换机有四种类型,分别为Direct,topic,headers,Fanout.
     *
     * 1、Direct是RabbitMQ默认的交换机模式,也是最简单的模式.
     *    即创建消息队列的时候,指定一个BindingKey.
     *    当发送者发送消息的时候,指定对应的Key.
     *    当Key和消息队列的BindingKey一致的时候,消息将会被发送到该消息队列中.
     * 2、Fanout是路由广播的形式,发布订阅模式
     *    将会把消息发给绑定它的全部队列,即便设置了key,也会被忽略.
     * 3、topic转发信息主要是依据通配符,（*、#）
     *    队列和交换机的绑定主要是依据一种模式(通配符+字符串),
     *    而当发送消息的时候,只有指定的Key和该模式相匹配的时候,消息才会被发送到该消息队列中.
     * 4、headers也是根据一个规则进行匹配,传一个Map键值对
     *    在消息队列和交换机绑定的时候会指定一组键值对规则,
     *    而发送消息的时候也会指定一组键值对规则,当两组键值对规则相匹配的时候,消息会被发送到匹配的消息队列中.
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue testQueue() {
        return new Queue(ROUTER_KEY);
    }

    /**
     * 将队列与交换机绑定，routing_key为ROUTER_KEY,就是完全匹配（*、#）
     * @param testQueue
     * @param topicExchange
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage(@Qualifier("testQueue")Queue testQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(testQueue).to(topicExchange).with(ROUTER_KEY);
    }


}

package com.jape.distributedlockdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisExpListener extends KeyExpirationEventMessageListener {

    public RedisExpListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        try{
            System.out.println("redis失效key====="+expiredKey);
        }catch (Exception e){
            log.error("redis 消息订阅异常:{}",e.getMessage());
        }
    }
}

package com.jape.redisdemo.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * 消息消费者
 */
@Component
public class RedisStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

    private static final String StreamKey = "stream:2";
    private static final String ConsumerGroup = "group-1";
    private static final String ConsumerName = "consumer-1";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        System.err.println("key  :" + message.getStream());
        System.err.println("id   :" + message.getId());
        System.err.println("value:" + message.getValue());
        Long acknowledge = redisTemplate.opsForStream().acknowledge(ConsumerGroup, message);
        System.err.println("ack    :" + acknowledge);
        /*Long delete = redisTemplate.opsForStream().delete(message);
        System.err.println("delete :" + delete);*/
        PendingMessagesSummary pending = redisTemplate.opsForStream().pending(StreamKey, ConsumerGroup);
        System.err.println("pending:" + pending.getIdRange());
    }

    // 配置
    @PostConstruct
    public void configListener() {

        //创建流
        if (!redisTemplate.hasKey(StreamKey)) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("init", "0");
            RecordId recordId = redisTemplate.opsForStream().add(StreamKey, map);
            redisTemplate.opsForStream().delete(StreamKey, recordId);
        }
        //创建消费者组
        StreamInfo.XInfoGroups groups = redisTemplate.opsForStream().groups(StreamKey);
        long count = groups.stream().parallel().filter(infoGroup -> ConsumerGroup.equals(infoGroup.groupName())).count();
        if (count == 0) {
            redisTemplate.opsForStream().createGroup(StreamKey, ConsumerGroup);
        }

        // 根据配置对象创建监听容器对象
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer =
                StreamMessageListenerContainer.create(redisConnectionFactory);

        // 使用监听容器对象开始监听消费（使用的是手动确认方式）
        streamMessageListenerContainer.receive(
                Consumer.from(ConsumerGroup, ConsumerName),
                StreamOffset.create(StreamKey, ReadOffset.lastConsumed()),
                this);

        // 启动监听
        streamMessageListenerContainer.start();
    }
}

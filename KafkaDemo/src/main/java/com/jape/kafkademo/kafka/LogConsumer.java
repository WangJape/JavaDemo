package com.jape.kafkademo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jape.kafkademo.entity.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LogConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = {"test-log"})
    public void saveLog(ConsumerRecord<?, ?> consumerRecord) throws JsonProcessingException {
        //判断是否为null
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            String message = (String)kafkaMessage.get();
            Log log = objectMapper.readValue(message, Log.class);
            System.err.println("消费消息:" + log.toString());
        }
    }

}

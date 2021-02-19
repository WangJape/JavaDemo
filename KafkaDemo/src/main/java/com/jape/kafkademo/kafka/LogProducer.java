package com.jape.kafkademo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jape.kafkademo.entity.Log;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    public void sendLog(Log log) {
        String reqJson = objectMapper.writeValueAsString(log);
        kafkaTemplate.send("test-log", reqJson);
    }

}

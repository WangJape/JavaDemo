package com.wjp.rabbitmqconsumer.mqmonitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjp.rabbitmqconsumer.config.RabbitmqConfig;
import com.wjp.rabbitmqconsumer.entity.RecordsProductTransaction;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = RabbitmqConfig.ROUTER_KEY)
public class MessageReceive {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitHandler
    public void process(String data){
        RecordsProductTransaction records = null;
        try {
            records = objectMapper.readValue(data, RecordsProductTransaction.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.err.println(records.toString());
    }

}

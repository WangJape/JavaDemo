package com.wjp.rabbitmqprovider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjp.rabbitmqprovider.config.RabbitmqConfig;
import com.wjp.rabbitmqprovider.entity.RecordsProductTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitmqDemoApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendTopicMessage() {
        //amqpTemplate.convertAndSend(RabbitmqConfig.ROUTER_KEY, "Hello World");
        RecordsProductTransaction records = new RecordsProductTransaction();
        for (int i = 0; i < 1; i++) {
            records.setStoreName("测试店铺名" + i);
            records.setProductName("测试产品名" + i);
            records.setTransactionTime(LocalDateTime.now());
            String data = null;
            try {
                data = objectMapper.writeValueAsString(records);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            amqpTemplate.convertAndSend(RabbitmqConfig.EXCHANGE, RabbitmqConfig.ROUTER_KEY, data);
        }


    }

}

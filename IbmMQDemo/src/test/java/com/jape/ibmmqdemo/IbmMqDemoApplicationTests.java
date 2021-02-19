package com.jape.ibmmqdemo;

import com.jape.ibmmqdemo.mq.MsgSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IbmMqDemoApplicationTests {

    @Autowired
    private MsgSender msgSender;

    @Test
    void contextLoads() throws InterruptedException {
        long a = 0L;
        while (true) {
            a += 1;
            msgSender.sendMsg(String.valueOf(a));
            //Thread.sleep(50);
        }

    }

}

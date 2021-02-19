package com.jape.ibmmqdemo.mq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

/**
 * ibm mq 消息发送者
 */
@Component
public class MsgSender {

    @Autowired
    private JmsOperations jmsOperations;

    public void sendMsg(String msg) {
        jmsOperations.convertAndSend("test1", msg);
    }


}

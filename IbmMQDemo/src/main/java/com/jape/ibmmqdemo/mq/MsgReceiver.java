package com.jape.ibmmqdemo.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class MsgReceiver extends MessageListenerAdapter {

    @Override
    @JmsListener(destination = "test1") //队列
    public void onMessage(Message message, Session session) throws JMSException {
        //必须转换如果不转换直接message.tostring消息的传输有限制。
        TextMessage textMessage = (TextMessage) message;  //转换成文本消息
        System.out.println("接收到的值:" + textMessage.getText());
    }
}

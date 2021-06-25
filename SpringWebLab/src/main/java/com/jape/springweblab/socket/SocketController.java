package com.jape.springweblab.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {

    @Autowired
    private MyWebSocket webSocket;

    @RequestMapping("sendWebSocketMsg")
    public void sendWebSocketMsg() {
        for (int i = 0; i < 100; i++) {
            webSocket.sendMsgToAllUser("系统第[" + i + "]次发送数据！");
        }
    }
}

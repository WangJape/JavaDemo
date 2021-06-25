package com.jape.springweblab.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jape.springweblab.dto.SocketMsg;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @Description: websocket的具体实现类
 * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
 * 但在springboot中连容器都是spring管理的。
 * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
 * 所以可以用一个静态set保存起来。
 */
@ServerEndpoint(value = "/websocket/{nickname}")
@Component
public class MyWebSocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String nickname;

    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //用来记录sessionId和该session进行绑定
    private static Map<String,Session> map = new HashMap<String, Session>();

    @OnOpen
    public void onOpen(Session session,@PathParam("nickname") String nickname) {
        if(map.get(nickname) != null){
            sendMsgToOneUser(new SocketMsg(nickname,"您已经在线，当前在线人数" + webSocketSet.size()));
            return;
        }

        this.session = session;
        this.nickname = nickname;

        map.put(nickname, session);
        webSocketSet.add(this);

        System.out.println("有新连接加入:" + nickname + ",当前在线人数为" + webSocketSet.size());

        sendMsgToOneUser(new SocketMsg(nickname,"登录成功，当前在线人数" + webSocketSet.size()));
    }


    @OnClose
    public void onClose() {
        map.remove(this.nickname);
        webSocketSet.remove(this);
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickname") String nickname) {
        System.out.println("来自["+nickname+"]的消息-->:" + message);
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);
            if(socketMsg.getType() == 1){
                //单聊.需要找到发送者和接受者.
                socketMsg.setFromUser(this.nickname);//发送者
                sendMsgToOneUser(socketMsg);
            }else{
                //群发消息
                sendMsgToAllUser(nickname+": "+socketMsg.getMsg());
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 单发自定义消息
     * @param socketMsg
     */
    public void sendMsgToOneUser(SocketMsg socketMsg){
        Session fromSession = map.get(socketMsg.getFromUser());
        Session toSession = map.get(socketMsg.getToUser());
        //发送给接受者.
        if(toSession != null){
            //发送给接收者.
            toSession.getAsyncRemote().sendText(nickname+"："+socketMsg.getMsg());
            if(fromSession != null){
                //反馈发送状态
                fromSession.getAsyncRemote().sendText(nickname+"："+socketMsg.getMsg()+"发送成功！");
            }
        }else if(fromSession != null){
            //发送给发送者.
            fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");
        }else{
            System.out.println("没有接收目标！");
        }
    }

    /**
     * 群发自定义消息
     */
    public  void sendMsgToAllUser(String message){
        for (MyWebSocket item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }
}


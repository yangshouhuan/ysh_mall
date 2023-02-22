package com.qfedu.fmmall.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{oid}")
public class WebSocketServer {

    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * @OnOpen: 前端发送请求websocket就会执行open方法
     */
    @OnOpen
    public void open(@PathParam("oid") String orderId, Session session) {

        // 保存连接
        sessionMap.put(orderId, session);
    }

    /**
     * @OnClose: 前端断开连接，都会执行该请求
     */
    @OnClose
    public void close(@PathParam("oid") String orderId) {
        // 删除连接
        sessionMap.remove(orderId);
    }

    /**
     * 发送信息
     * @param orderId
     * @param msg
     */
    public static void sendMsg(String orderId, String msg) {
        try {
            Session session = sessionMap.get(orderId);
            session.getBasicRemote().sendText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

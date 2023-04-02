package com.feng.component;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint(value = "/game/{userId}")
public class WebSocketComponent {
    public static CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();
    public static Map<String,Session> SESSION_POOL = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId){
        try {
            SESSION_POOL.put(userId,session);
            SESSIONS.add(session);

            log.info("欢迎用户{},进入游戏，现在时间:{}",userId,DateUtil.now());
        }catch (Exception e){
            log.error("出现错误,错误原因:{},现在时间是:{}",e.getMessage(), DateUtil.now());
        }
    }
    @OnClose
    public void onClose(Session session,@PathParam(value = "userId") String userId){
        try{
            SESSIONS.remove(session);
            SESSION_POOL.remove(userId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userIds",SESSION_POOL.keySet());
            sendAllMessage(jsonObject.toJSONString());
            log.info("下次光临");
        }catch (Exception e){
            log.error("出错错误,错误信息:{},现在时间:{}",e.getMessage(),DateUtil.now());
        }
    }
    @OnMessage
    public void onMessage(String message){
        JSONObject jsonObject = JSON.parseObject(message);
        log.info(message);
        if(jsonObject.containsKey("toUserId")){
            sendOneMessage(jsonObject.getString("toUserId"),jsonObject.getString("fromUserId"));
        }
    }
    public void sendAllMessage(String message) {
        log.info("【WebSocket消息】广播消息：" + message);
        for (Session session : SESSIONS) {
            try {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void sendOneMessage(String userId, String message) {
        Session session = SESSION_POOL.get(userId);
        if (session != null && session.isOpen()) {
            try {
                synchronized (session) {
                    log.info("【WebSocket消息】单点消息：" + message);
                    session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

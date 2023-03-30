package com.feng.component;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

//@Component
@Slf4j
@ServerEndpoint(value = "/game/{userId}")
public class WebSocketComponent {
    private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();
    private static final Map<String,Session> SESSION_POOL = new HashMap<>();

    @OnOpen
    private void onOpen(Session session, @PathParam(value = "userId") String userId){
        try {
            SESSION_POOL.put(userId,session);
            SESSIONS.add(session);
            log.info("欢迎用户{},进入游戏，现在时间:{}",userId,DateUtil.now());
        }catch (Exception e){
            log.error("出现错误,错误原因:{},现在时间是:{}",e.getMessage(), DateUtil.now());
        }
    }
}

package com.qisi.open.listener.controller;

import com.alibaba.fastjson.JSONObject;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import com.lark.oapi.service.im.v1.ImService;
import com.lark.oapi.service.im.v1.model.P1MessageReceivedV1;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import com.lark.oapi.service.task.v1.TaskService;
import com.lark.oapi.service.task.v1.model.P2TaskCommentUpdatedV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/lark")
@Slf4j
public class EventController {
    @Value("${lark.encryptKey}")
    private String encryptKey;
    @Value("${lark.verificationToken}")
    private String verificationToken;
    // 注入 ServletAdapter 实例
    @Autowired
    private ServletAdapter servletAdapter;
    //注册消息处理器
    private  EventDispatcher EVENT_DISPATCHER;
    @PostConstruct
    public void init(){
        EVENT_DISPATCHER=EventDispatcher.newBuilder(verificationToken,
                encryptKey)
                .onP2TaskCommentUpdatedV1(new TaskService.P2TaskCommentUpdatedV1Handler() {
                    @Override
                    public void handle(P2TaskCommentUpdatedV1 event) throws Exception {
                        log.info("接收到事件{}", JSONObject.toJSONString(event));
                    }
                })
                .onP2MessageReceiveV1(new ImService.P2MessageReceiveV1Handler() {
                    @Override
                    public void handle(P2MessageReceiveV1 event) throws Exception {
                        log.info("接收到事件{}", JSONObject.toJSONString(event));
                    }
                })
                .build();
    }




    //创建路由处理器
    @RequestMapping("/webhook/event")
    public void event(HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        //回调扩展包提供的事件回调处理器
        servletAdapter.handleEvent(request, response, EVENT_DISPATCHER);
    }
}

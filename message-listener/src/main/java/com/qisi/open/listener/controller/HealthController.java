package com.qisi.open.listener.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : qisi
 * @date: 2023/2/21
 * @description: 心跳检测
 */
@Slf4j
@RestController
public class HealthController {
    @GetMapping("/health")
    public String hello(){
        log.info("health");
        return "success";
    }
}

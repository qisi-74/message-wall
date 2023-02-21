package com.qisi.open.listener.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : qisi
 * @date: 2023/2/21
 * @description: 应用启动入口
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.qisi.open")
public class ListenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ListenerApplication.class,args);
        log.info("启动项目成功");
    }
}

package com.qisi.open.listener.config;

import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : qisi
 * @date: 2023/2/25
 * @description:
 */
@Configuration
public class InitConfiguration {
    @Bean
    public ServletAdapter getServletAdapter(){
        return new ServletAdapter();
    }
}

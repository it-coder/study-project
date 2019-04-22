package com.lihang.websocket;

import com.lihang.exception.annotation.EnableExceptionHandle;
import com.lihang.exception.config.ExceptionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * websocket项目
 *
 * @author lih
 * @create 2019-04-19-16:25.
 */
@SpringBootApplication
@EnableExceptionHandle(value = "execution(* com.lihang.websocket.controller.*.*(..))")
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }
}

package com.lihang.websocket;

import com.lihang.exception.annotation.EnableExceptionHandle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

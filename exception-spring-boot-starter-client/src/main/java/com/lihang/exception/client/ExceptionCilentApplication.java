package com.lihang.exception.client;

import com.lihang.exception.annotation.EnableExceptionHandle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lih
 * @create 2019-04-23-10:42.
 */
@SpringBootApplication
@EnableExceptionHandle(value = "execution(* com.lihang.exception.client.controller.*.*(..))")
public class ExceptionCilentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionCilentApplication.class, args);
    }
}

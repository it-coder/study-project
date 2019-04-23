package com.lihang.exception.aspect;

import com.lihang.exception.handle.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author lih
 * @create 2019-04-19-19:23.
 */
//@Aspect
//@Configuration
@Slf4j
public class ExceptionAspect {

    private ExceptionHandler exceptionHandler;

    public ExceptionAspect(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @AfterThrowing(pointcut = "execution(* com.lihang.exception.controller.*.*(..))", throwing = "e")
    public void exceptionNoticeWithMethod(JoinPoint joinPoint, RuntimeException e) {
        log.debug("======================:{} --> {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        handleException(e, joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    private void handleException(RuntimeException exception, String methodName, Object[] args) {
        log.debug("出现异常：" + methodName + String.join(",", Arrays.stream(args).map(x -> x.toString()).toArray(String[]::new)));
        exceptionHandler.createNotice(exception, methodName, args);
    }


}

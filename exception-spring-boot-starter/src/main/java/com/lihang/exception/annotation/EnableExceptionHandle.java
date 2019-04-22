package com.lihang.exception.annotation;

import java.lang.annotation.*;

/**
 * 开启错误处理
 * @author lih
 * @create 2019-04-22-19:19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface EnableExceptionHandle {
    String value();
}

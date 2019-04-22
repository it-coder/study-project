package com.lihang.exception.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 邮件配置属性读取
 *
 *  stmp.host、port、username、password通过spring-boot-starter-mail配置
 * @author lih
 * @create 2019-04-20-9:34.
 */
@ConfigurationProperties(prefix = "exception-handle.email")
@Setter
@Getter
@ToString
public class ExceptionEmailProperties {

    /** 发件人 **/
    String from;
    /** 发送给 **/
    String[] to;
    /** 抄送 **/
    String[] cc;
}

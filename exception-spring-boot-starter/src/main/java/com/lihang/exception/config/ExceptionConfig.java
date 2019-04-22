package com.lihang.exception.config;

import com.lihang.exception.aspect.ExceptionAspect;
import com.lihang.exception.handle.ExceptionHandler;
import com.lihang.exception.notice.EmailNoticeComponent;
import com.lihang.exception.property.ExceptionEmailProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

/**
 *  自动装配
 * @author lih
 * @create 2019-04-20-9:31.
 */
@Configuration
@EnableConfigurationProperties({ ExceptionEmailProperties.class })
public class ExceptionConfig {

    @Bean
    //@ConditionalOnProperty(name = "exception-handle.enable-notice", havingValue = "true")
    public EmailNoticeComponent emailNoticeComponent(MailSender mailSender, MailProperties mailProperties, ExceptionEmailProperties exceptionEmailProperties) {
        return new EmailNoticeComponent(mailSender, mailProperties, exceptionEmailProperties);
    }

    @Bean
   // @ConditionalOnProperty(name = "exception-handle.enable-notice", havingValue = "true")
    public ExceptionHandler exceptionHandler(EmailNoticeComponent emailNoticeComponent) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(emailNoticeComponent);
        return exceptionHandler;
    }

//    @Bean
//    //@ConditionalOnProperty(name = "exception-handle.enable-notice", havingValue = "true")
//    public ExceptionAspect exceptionAspect(ExceptionHandler exceptionHandler) {
//        return new ExceptionAspect(exceptionHandler);
//    }
}

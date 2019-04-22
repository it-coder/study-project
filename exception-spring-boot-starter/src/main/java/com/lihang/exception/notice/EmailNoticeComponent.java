package com.lihang.exception.notice;

import com.lihang.exception.entity.ExceptionNotice;
import com.lihang.exception.property.ExceptionEmailProperties;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.regex.Pattern;

/**
 * 邮件发送组件
 *
 * @author lih
 * @create 2019-04-20-10:01.
 */
public class EmailNoticeComponent {

    MailSender mailSender;

    MailProperties mailProperties;

    ExceptionEmailProperties exceptionEmailProperties;

    public EmailNoticeComponent(MailSender mailSender, MailProperties mailProperties, ExceptionEmailProperties exceptionEmailProperties) {
        this.exceptionEmailProperties = exceptionEmailProperties;
        this.mailProperties = mailProperties;
        this.mailSender = mailSender;

        checkEmailProperties();
    }

    private boolean isEmail(String email) {
        if (email != null)
            return Pattern.matches("^[A-Za-z0-9_\\-]+@[a-zA-Z0-9_\\-]+(\\.[a-zA-Z]{2,4})+$", email);
        return false;
    }

    private void checkEmailProperties() {
        String fromEmail = exceptionEmailProperties.getFrom();
        if (fromEmail != null && !isEmail(fromEmail))
            throw new IllegalArgumentException("发件人邮箱错误");
        String[] toEmail = exceptionEmailProperties.getTo();
        if (toEmail != null) {
            for (String email : toEmail) {
                if (!isEmail(email))
                    throw new IllegalArgumentException("收件人邮箱错误");
            }
        }
        String[] ccEmail = exceptionEmailProperties.getCc();
        if (ccEmail != null) {
            for (String email : ccEmail) {
                if (!isEmail(email))
                    throw new IllegalArgumentException("抄送人邮箱错误");
            }
        }
    }

    public void send(ExceptionNotice exceptionNotice) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String fromEmail = exceptionEmailProperties.getFrom();
        fromEmail = fromEmail == null ? mailProperties.getUsername() : fromEmail;
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(exceptionEmailProperties.getTo());
        String[] cc = exceptionEmailProperties.getCc();
        if (cc != null && cc.length > 0) {
            mailMessage.setCc(cc);
        }
        mailMessage.setText(exceptionNotice.createText());
        mailMessage.setSubject(String.format("来自%s的异常提醒", exceptionNotice.getProject()));
        mailSender.send(mailMessage);

    }
}

package com.lihang.exception.handle;

import com.lihang.exception.entity.ExceptionNotice;
import com.lihang.exception.notice.EmailNoticeComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * 错误消息处理
 *
 * @author lih
 * @create 2019-04-20-10:12.
 */
@Slf4j
public class ExceptionHandler {

    private EmailNoticeComponent emailNoticeComponent;

    public ExceptionHandler(EmailNoticeComponent emailNoticeComponent) {
        this.emailNoticeComponent = emailNoticeComponent;
    }

    public boolean createNotice(Throwable ex, String method, Object[] args) {
        ExceptionNotice exceptionNotice = new ExceptionNotice(ex, null, args);
        messageSend(exceptionNotice);
        return true;
    }

    private void messageSend(ExceptionNotice exceptionNotice) {
        emailNoticeComponent.send(exceptionNotice);
    }
}

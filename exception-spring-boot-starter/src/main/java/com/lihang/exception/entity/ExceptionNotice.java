package com.lihang.exception.entity;

import com.lihang.exception.property.ExceptionSettingProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *  异常消息体
 * @author lih
 * @create 2019-04-20-10:52.
 */
@Setter
@Getter
public class ExceptionNotice {

    /**
     * 工程名
     */
    protected String project;


    /**
     * 异常的标识码
     */
    protected String uid;

    /**
     * 方法名
     */
    protected String methodName;

    /**
     * 方法参数信息
     */
    protected List<Object> parames;

    /**
     * 类路径
     */
    protected String classPath;

    /**
     * 异常信息
     */
    protected String exceptionMessage;

    /**
     * 异常追踪信息
     */
    protected List<String> traceInfo;

    public ExceptionNotice(Throwable ex, Object[] args, String projectName) {
        this.exceptionMessage = parseExceptionMessage(ex);
        this.parames = args == null ? null : Arrays.stream(args).collect(toList());
        this.project = projectName;
        List<StackTraceElement> list = Arrays.stream(ex.getStackTrace())
                .filter(x -> !x.getFileName().equals("<generated>")).collect(toList());
        if (list.size() > 0) {
            this.traceInfo = list.stream().map(x -> x.toString()).collect(toList());
            this.methodName = list.get(0).getMethodName();
            this.classPath = list.get(0).getClassName();
        }
    }


    private String parseExceptionMessage(Throwable exception) {
        String em = exception.toString();
        if (exception.getCause() != null)
            em = String.format("%s\r\n\tcaused by : %s", em, parseExceptionMessage(exception.getCause()));
        return em;
    }

    public String createText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("工程信息：").append(project).append("\r\n");
        stringBuilder.append("类路径：").append(classPath).append("\r\n");
        stringBuilder.append("方法名：").append(methodName).append("\r\n");
        if (parames != null && parames.size() > 0) {
            stringBuilder.append("参数信息：")
                    .append(String.join(",", parames.stream().map(x -> x.toString()).collect(toList()))).append("\r\n");
        }
        stringBuilder.append("异常信息：").append(exceptionMessage).append("\r\n");
        stringBuilder.append("异常追踪：").append("\r\n").append(String.join("\r\n", traceInfo)).append("\r\n");
        return stringBuilder.toString();

    }
}

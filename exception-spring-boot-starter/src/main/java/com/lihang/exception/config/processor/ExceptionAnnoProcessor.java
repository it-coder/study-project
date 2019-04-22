package com.lihang.exception.config.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.*;

import com.google.auto.service.AutoService;
import com.lihang.exception.annotation.EnableExceptionHandle;

/**
 * 注解加载器
 *
 * @author lih
 * @create 2019-04-22-19:13.
 */
@AutoService(Processor.class)
public class ExceptionAnnoProcessor extends AbstractProcessor {
    private Messager messager; //用于打印日志
    private Elements elementUtils; //用于处理元素
    private Filer filer;  //用来创建java文件或者class文件


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<String>(1);
        set.add(EnableExceptionHandle.class.getCanonicalName());
        return Collections.unmodifiableSet(set);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(EnableExceptionHandle.class);

        List<String> strings = new ArrayList<>(2);
        // 未启用注解直接返回
        for (Element e : elements) {
            if (e.getKind() == ElementKind.CLASS && e instanceof TypeElement) {
                TypeElement temp = (TypeElement)e;
                String qualifiedName = temp.getQualifiedName().toString();
                EnableExceptionHandle anno = temp.getAnnotation(EnableExceptionHandle.class);
                // 写入的包
                String qname = getPackage(qualifiedName);
                messager.printMessage(Diagnostic.Kind.NOTE, qname);
                strings.add(qname);
                // 声明的切点
                strings.add(anno.value());
                break;
            }
        }

        if (strings.isEmpty()) {
            return true;
        }

        try {
            // 生成java文件
            JavaFileObject exceptionAspectFile = filer.createSourceFile("ExceptionAspect");
            createFile(strings, exceptionAspectFile.openWriter());
            // 编译
            compile(exceptionAspectFile.toUri().getPath());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "ExceptionAspect编译失败");
        }
        return false;
    }


    private void createFile(List<String> settings, Writer writer) throws IOException {
        writer.write("package " + settings.get(0) + ";\n"
            + "import com.lihang.exception.handle.ExceptionHandler;\n" +
                "import lombok.extern.slf4j.Slf4j;\n" +
                "import org.aspectj.lang.JoinPoint;\n" +
                "import org.aspectj.lang.annotation.AfterThrowing;\n" +
                "import org.aspectj.lang.annotation.Aspect;\n" +
                "import org.springframework.context.annotation.Configuration;\n" +
                "\n" +
                "import java.util.Arrays;\n" +
                "\n" +
                "/**\n" +
                " * @author lih\n" +
                " * @create 2019-04-19-19:23.\n" +
                " */\n" +
                "@Aspect\n" +
                "@Configuration\n" +
                "@Slf4j\n" +
                "public class ExceptionAspect {\n" +
                "\n" +
                "    private ExceptionHandler exceptionHandler;\n" +
                "\n" +
                "    public ExceptionAspect(ExceptionHandler exceptionHandler) {\n" +
                "        this.exceptionHandler = exceptionHandler;\n" +
                "    }\n" +
                "\n" +
                "    @AfterThrowing(pointcut = \"" + settings.get(1) + "\", throwing = \"e\")\n" +
                "    public void exceptionNoticeWithMethod(JoinPoint joinPoint, RuntimeException e) {\n" +
                "         System.out.println(\"==============\");\n" +
                "        log.debug(\"======================:{} --> {}\", joinPoint.getSignature().getName(), joinPoint.getArgs());\n" +
                "        handleException(e, joinPoint.getSignature().getName(), joinPoint.getArgs());\n" +
                "    }\n" +
                "\n" +
                "    private void handleException(RuntimeException exception, String methodName, Object[] args) {\n" +
                "        System.out.println(\"出现异常：\" + methodName + String.join(\",\", Arrays.stream(args).map(x -> x.toString()).toArray(String[]::new)));\n" +
                "        exceptionHandler.createNotice(exception, methodName, args);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}"
        );

        writer.flush();
        writer.close();
    }

    /**
     *  手动编译
     * @param path
     */
    public void compile(String path) throws IOException {
        // 拿到系统编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 文件管理器
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        // 要编译的文件
        Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjects(path);
        // 编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, javaFileObjects);
        // 执行编译任务
        task.call();
        fileManager.close();
    }

    /**
     * 读取包名
     * @param name
     */
    private String getPackage(String name) {
        String result = name;
        if (name.contains(".")) {
            result = name.substring(0, name.lastIndexOf("."));
        }else {
            result = "";
        }
        return result;
    }
}

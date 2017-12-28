package com.xulinchao.NameCheckProcessor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @SupportedAnnotationTypes("*") 支持所有注解
 * @SupportedSourceVersion(SourceVersion.RELEASE_8) 只支持jdk 1.8的java代码
 **/
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);

    }
    /**
     * annotations参数是获取到此注解处理器锁要处理的注解集合
     * roundEnv访问到当前这个round中的语法树节点，每个语法树节点在这里为一个Element
     * **/
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                nameChecker.checkName(element);

            }
        }
        return false;
    }
}

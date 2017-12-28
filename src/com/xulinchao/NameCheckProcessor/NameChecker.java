package com.xulinchao.NameCheckProcessor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;


public class NameChecker {
    private final Messager messager;
    NameCheckScanner nameCheckScanner;

    public NameChecker(ProcessingEnvironment processingEnvironment) {
        this.messager=processingEnvironment.getMessager();
        nameCheckScanner=new NameCheckScanner(this.messager);
    }

    public Messager getMessager() {
        return messager;
    }

    /**
     * 对java名程序命名进行检查，是否符合驼式命名
     * **/
    public void checkName(Element element){
        nameCheckScanner.scan(element);
    }

}

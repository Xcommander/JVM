package com.xulinchao.NameCheckProcessor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;

public class NameCheckScanner extends ElementScanner8 {
    private Messager messager;

    public NameCheckScanner(Messager messager) {
        this.messager = messager;
    }

    /**
     * 检查字段，也就是变量
     **/
    @Override
    public Object visitVariable(VariableElement e, Object o) {
        return super.visitVariable(e, o);
    }

    /**
     * 检查java类
     **/
    @Override
    public Object visitType(TypeElement e, Object o) {
        scan(e.getTypeParameters(), o);
        checkCamelCase(e, true);
        super.visitType(e, o);

        return null;
    }

    /**
     * 检查方法
     **/
    @Override
    public Object visitExecutable(ExecutableElement e, Object o) {

        return super.visitExecutable(e, o);
    }

    private void checkCamelCase(Element element, boolean initialCaps) {
        /**
         * 先取出每一个，然后进行首字母判断，接下来，就是对类继续判断,类名一般来说，不允许两个
         * **/
        String name = element.getSimpleName().toString();
        int firstCodePoint = name.codePointAt(0);
        boolean previousUpper = false;
        boolean conventional = true;
        /**
         * 首字母是否大写或者小写，来判断类或者名字是否符合规范**/
        if (Character.isUpperCase(firstCodePoint)) {
            previousUpper=true;
            if (!initialCaps) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以小写字母开头", element);
                return;
            }
            conventional=true;
        }else if(Character.isLowerCase(firstCodePoint)){
            if(initialCaps){
                messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以大写字母开头", element);
                return;
            }
            conventional=true;

        }else{
            conventional=false;
        }
        if(conventional){
            int cp=firstCodePoint;
            for(int i=Character.charCount(cp);i<name.length()-1;i+=Character.charCount(cp)){
                cp=name.codePointAt(i);
                if(Character.isUpperCase(cp)){
                    if(previousUpper){
                        conventional=false;
                        break;
                    }
                    previousUpper=true;
                }else{
                    previousUpper=false;
                }

            }
        }
        if(!conventional){
            messager.printMessage(Diagnostic.Kind.WARNING, "名称“" + name + "”应当符合驼式命名法（Camel Case Names）", element);
        }


    }
}

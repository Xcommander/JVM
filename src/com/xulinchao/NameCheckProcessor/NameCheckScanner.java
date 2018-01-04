package com.xulinchao.NameCheckProcessor;


import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * 变量默认为public
 **/
public class NameCheckScanner extends ElementScanner8 {
    private Messager messager;

    public NameCheckScanner(Messager messager) {
        this.messager = messager;
    }

    /**
     * @name: visitVariable
     * @Description: 检查变量名是否合法
     * @param: [e, o]
     * @return: java.lang.Object
     **/
    @Override
    public Object visitVariable(VariableElement e, Object o) {

        if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e))
            checkAllCaps(e);
        else
            checkCamelCase(e, false);


        return null;
    }

    /**
     * @name: checkAllCaps
     * @Description: 检查是否都是大写
     * @param: [element]
     * @return: void
     */

    public void checkAllCaps(Element element) {
        String name = element.getSimpleName().toString();
        int firstPoint = name.codePointAt(0);
        boolean conventional = true;
        if (!Character.isUpperCase(firstPoint)) {
            conventional = false;
        } else {
            /**
             * previousUnderscore是为了防止出现连续的下划线
             * **/
            boolean previousUnderscore = false;
            int cp = firstPoint;
            for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                cp = name.codePointAt(i);
                if (cp == (int) '_') {
                    if (previousUnderscore) {
                        conventional = false;
                        break;

                    }
                    previousUnderscore = true;
                } else {
                    previousUnderscore = false;
                    if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                        conventional = false;
                        break;
                    }

                }
            }

        }

        if (!conventional) {
            messager.printMessage(Diagnostic.Kind.WARNING, "常量" + " 应该全部以大写字母或者下划线命名，并且以字母开头", element);

        }
    }

    /**
     * @name: heuristicallyConstant
     * @Description: 判断一个变量是否是常量
     * @param: [variableElement]
     * @return: boolean
     **/
    public boolean heuristicallyConstant(VariableElement variableElement) {
        if (variableElement.getEnclosingElement().getKind() == ElementKind.INTERFACE)
            return true;
        else if (variableElement.getKind() == ElementKind.FIELD && variableElement.getModifiers().containsAll(
                EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)))
            return true;
        else
            return false;


    }

    /**
     * @name: visitType
     * @Description: 检查java类
     * @param: [e, o]
     * @return: java.lang.Object
     **/
    @Override
    public Object visitType(TypeElement e, Object o) {
        scan(e.getTypeParameters(), o);
        checkCamelCase(e, true);
        super.visitType(e, o);

        return null;
    }

    /**
     * @name: visitExecutable
     * @Description: 检查方法
     * @param: [e, o]
     * @return: java.lang.Object
     **/
    @Override
    public Object visitExecutable(ExecutableElement e, Object o) {
        if (e.getKind() == ElementKind.METHOD) {
            Name name = e.getSimpleName();
            if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                messager.printMessage(Diagnostic.Kind.WARNING, "一个普通的方法 " + name + " 不应当和类名重复，避免和构造函数产生混淆", e);

            }
            checkCamelCase(e, false);

        }
        super.visitExecutable(e, o);

        return null;
    }

    /**
     * @name: checkCamelCase
     * @Description: 先取出每一个，然后进行首字母判断，接下来，就是对类继续判断,类名一般来说，不允许两个
     * @param: [element, initialCaps]
     * @return: void
     **/
    private void checkCamelCase(Element element, boolean initialCaps) {
        String name = element.getSimpleName().toString();
        int firstCodePoint = name.codePointAt(0);
        boolean previousUpper = false;
        boolean conventional = true;
        /**
         * 首字母是否大写或者小写，来判断类或者名字是否符合规范**/
        if (Character.isUpperCase(firstCodePoint)) {
            previousUpper = true;
            if (!initialCaps) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以小写字母开头", element);
                return;
            }
            conventional = true;
        } else if (Character.isLowerCase(firstCodePoint)) {
            if (initialCaps) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以大写字母开头", element);
                return;
            }
            conventional = true;

        } else {
            conventional = false;
        }
        if (conventional) {
            int cp = firstCodePoint;
            for (int i = Character.charCount(cp); i < name.length() - 1; i += Character.charCount(cp)) {
                cp = name.codePointAt(i);
                if (Character.isUpperCase(cp)) {
                    if (previousUpper) {
                        conventional = false;
                        break;
                    }
                    previousUpper = true;
                } else {
                    previousUpper = false;
                }

            }
        }
        if (!conventional) {
            messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当符合驼式命名法（Camel Case Names）", element);
        }


    }
}

public class SuperClass {
    /**
     * 初始化类的时候，类的变量一定会被初始化
     * static标识的，才属于是类的，其他都是实例的
     * **/
    static {
        System.out.println("This Super class init");

    }
    public static int xxx=3;

    public SuperClass() {
        System.out.println("xxx");
    }
    //    public static int value = 12;
////    void SuperClass(){
////        System.out.println(value);
////    }
}

class Sub extends SuperClass {
    static {
        System.out.println("This is SubClass init");
    }
    public final static int xlc=3;


}
class NotInitialization{
    public static void main(String[] args) {
//        System.out.println(Sub.value);
        /**
         创建类的新实例--new，反射，克隆或反序列化；
         调用类的静态方法；
         操作类和接口的静态字段；（final字段除外）
         调用Java的特定的反射方法；
         初始化一个类的子类；
         指定一个类作为Java虚拟机启动时的初始化类（含有main方法的启动类）。。
         都会造成类的初始化
         类的初始化和实例初始化不一样
         实例初始化，是对象被创建时候，才会初始化
         * **/
//        SuperClass sca=new SuperClass();
        System.out.println(SuperClass.xxx);
        SuperClass[] s1=new SuperClass[10];
        String name="xlc.111.22";
        System.out.println(name.lastIndexOf(".")+1);
        System.out.println(name.substring(name.lastIndexOf(".")+1));
        /**
         * 当类的变量通过final修饰的话，对类的变量进行引用，其不会初始化，因为在编译的时候，就会被存储到，引用类的常量池中。
         * 所以调用的时候，两个类没有任何联系。
         * **/
//        System.out.println(Sub.xlc);
    }
}
import java.io.Serializable;

public class StaticDispatch {
    static  class Human {
    }

    ;

    static class man extends Human {
    }

    ;

    static class woman extends Human {
    }

    ;

    public void sayHello(Human guy) {
        System.out.println("Hello,guy!");
    }

    public void sayHello(man guy) {
        System.out.println("Hello,man!");
    }

    public void sayHello(woman guy) {
        System.out.println("Hello,woman!");
    }

    public static void main(String[] args) {
        Human man = new man();
        man= new woman();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello(man);
        staticDispatch.sayHello(man);
    }
}
class Overload{
    public static void sayHello(Object arg){
        System.out.println("Hello,Object!");

    }
//    public static void sayHello(int arg){
//        System.out.println("Hello,int!");
//    }
//    public static void sayHello(long arg){
//        System.out.println("Hello,long!");
//    }
//    public static void sayHello(Character arg){
//        System.out.println("Hello,Character!");
//    }
//    public static void sayHello(char arg){
//        System.out.println("Hello,char!");
//    }
//    public static void sayHello(Serializable arg){
//        System.out.println("Hello,Serializable!");
//    }

    public static void main(String[] args) {
        sayHello('a');
    }
}

public class DynamicDispatch {
    static  abstract class Human{
        public abstract void sayHello();
    }
    static class Man extends Human{
        @Override
        public void sayHello() {
            System.out.println("man say hello!");
        }
    }
    static class Woman extends Human{
        @Override
        public void sayHello() {
            System.out.println("woman say hello!");
        }
//        public String sayHello(int a){
//            return "";
//        }

    }

    public static void main(String[] args) {
        Human man =new Man();
        Human woman=new Woman();
        man.sayHello();
        woman.sayHello();
    }
}

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    interface IHello{
        void sayHello();
    }
    static class Hello implements IHello{
        @Override
        public void sayHello() {
            System.out.println("Hello,World!");
        }
    }
    static class DynamicProxy implements InvocationHandler{
        Object object;
        Object bind(Object o){
            this.object=o;
            /**
             * 也就是生成一个代理类的class字节码，然后在实例化成代理类，而这个代理类具有InvocationHandler特征，
             * 代理类的字节码中，会重写各个方法，在每个方法中都会先调用invoke，然后由invoke方法体，反过来调用方法。
             * 这样方法的主导权就跑到了invoke中了。
             *
             */


            return Proxy.newProxyInstance(object.getClass().getClassLoader(),object.getClass().getInterfaces(),
                    this);

        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Welcome,jason bourne!");
            return method.invoke(object,args);
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello=(IHello) new DynamicProxy().bind(new Hello());

        hello.sayHello();

    }

}

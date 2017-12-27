import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {
    static class classA {
        public void println(String s) {
            System.out.println(s);

        }
    }

    public static void main(String[] args) {
        Object obj1 = System.currentTimeMillis() % 2 == 0 ? System.out : new classA();
        try {
            getPrintlnMH(obj1).invokeExact("xulinchao");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }

    public static MethodHandle getPrintlnMH(Object reciver) throws NoSuchMethodException, IllegalAccessException {
        /**
         * 第一个是返回值，第二个是传入的参数
         * **/
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(reciver.getClass(), "println", methodType).bindTo(reciver);
    }
}

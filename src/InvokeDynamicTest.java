import java.lang.invoke.*;

public class InvokeDynamicTest {
    public static void testMethod(String s) {
        System.out.println(s);

    }

    private static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name,
                                            MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, methodType));

    }

    private static MethodType MT_BootstrapMethod() {
        return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
    }

    private static MethodHandle MH_BootstrapMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findStatic(InvokeDynamicTest.class, "BootstrapMethod", MT_BootstrapMethod());
    }

    private static MethodHandle INDY_BootstrapMethod() throws Throwable {
        CallSite callSite = (CallSite) MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(), "testMethod",
                MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null));
        return callSite.dynamicInvoker();
    }

    public static void main(String[] args) {
        try {
            INDY_BootstrapMethod().invokeExact("xulinchao");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}

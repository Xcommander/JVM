
import java.util.*;

public class fanxing {
    public static Integer cout = 4;

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Hello", "您好");
        map.put("how are you?", "吃了没?");
        System.out.println(map.get("Hello"));
        System.out.println(map.get("how are you?"));
        test();
        System.out.println(3L==3);
//         int i=1;
//         int j=1;
//        System.out.println(i==j);
//
//        int x=1;
//        Integer y=1;
//        System.out.println(x==y);
//
//        Integer o=1;
//        Integer l=1;
//        System.out.println(o==l);
//        System.out.println(cout);


    }
    class A{

    }
    public static void cast(){
        if(true){
            System.out.println(1);
        }else{
            System.out.println(2);
        }
    }

    public static void test() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
    }

    /**
     * 不能共存的，下面两个方法，
     * 因为泛型在进行类型擦除的时候
     * 两个方法的签名完全一致
     * java的签名只看方法名，参数名，参数顺序，如果一致，则表明两个方法的签名一致，不允许编译。
     */
    public static String method(List<String> list) {
        System.out.println("invoke method(List<String> list)");
        return "";
    }

    //    public static int method(List<Integer> list) {
//        System.out.println("invoke method(List<Integer> list)");
//        return 1;
//    }
    public static void auto() {
        List<Integer> list = Arrays.asList(1, 2, 4, 5);
        int sum = 0;
        for (int i : list) {
            sum += i;

        }
        System.out.println(sum);

    }

}

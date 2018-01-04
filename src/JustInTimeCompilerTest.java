/**
 * @author jasonbourne1992x@gmail.com(xulinchao)
 * @version V1.0
 * @Class: JustInTimeCompilerTest.java
 * @Package PACKAGE_NAME
 * @Description: 测试JIT编译本地代码
 * @date 2018/1/4 17:45
 */
public class JustInTimeCompilerTest {

    public static final int NUM = 15000;

    public static int doubleValue(int i) {
        for (int j = 0; j < 10000; j++) ;
        return i * 2;
    }

    public static long calcSum() {
        long sum = 0L;
        for (int i = 1; i <= 100; i++) {
            sum += doubleValue(i);

        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i=0;i<NUM;i++){
            calcSum();
        }
    }

}

/**
 * @author jasonbourne1992x@gmail.com(xulinchao)
 * @version V1.0
 * @Class: VolatileMustTest.java
 * @Package PACKAGE_NAME
 * @Description: some thing
 * @date 2018/1/16 14:01
 */
public class VolatileMustTest {
    public volatile static int x=0;

    public static void main(String[] args) {
        VolatileMustTest.x=4;

    }
}

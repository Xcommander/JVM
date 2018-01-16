import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jasonbourne1992x@gmail.com(xulinchao)
 * @version V1.0
 * @Class: VectorTest.java
 * @Package PACKAGE_NAME
 * @Description: some thing
 * @date 2018/1/15 14:09
 */
public class VectorTest {
    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }
            Thread removeThread = new Thread(() -> {
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);

                    }
                }
            });
            Thread prinThread = new Thread(() -> {
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println(vector.get(i));
                    }
                }
            });
            removeThread.start();

            prinThread.start();
            while (Thread.activeCount() > 20) ;
        }

    }
}

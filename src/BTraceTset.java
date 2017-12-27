import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BTraceTset {
    public int add(int a, int b) {
        return a + b;

    }

    public static void main(String[] args) throws IOException {
        BTraceTset bTraceTset = new BTraceTset();
//        Class cls=bTraceTset.getClass();
//        System.out.println(cls.getSimpleName());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i++) {
            bufferedReader.readLine();
            int a = (int) Math.round(Math.random() * 1000);
            int b = (int) Math.round(Math.random() * 1000);
            System.out.println(bTraceTset.add(a, b));

        }
    }
}

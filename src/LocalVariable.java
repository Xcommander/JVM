public class LocalVariable {
    static int j;

    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a=0;

        System.out.println(j);
        System.gc();
    }
}

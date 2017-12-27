public class Bar {
    int a=1;
    static int b=2;
    public int sum(int c){
        return a+b+c;
    }
    public static int[] sdefault=new int[]{
            1,
            2,
            3,
            4,
            5
    };

    public static void main(String[] args) {
        /**
         * -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly
         * -Xcomp -XX:CompileCommand=dontinline,*Bar.sum -XX:CompileCommand=compileonly.*Bar.sum
         *
         *
         * **/
        System.out.println(sdefault[5]);

    }
}

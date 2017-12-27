public class FieldResolution {
    interface interface0 {
        int A = 0;
    }

    interface interface1 extends interface0 {
        int A = 1;

    }

    interface interface2 {
        int A = 2;
    }

    static class parent implements interface1 {
        public static int A = 3;
    }

    static class sub extends parent implements interface2 {
        public static int A = 4;
    }

    public static void main(String[] args) {
        System.out.println(sub.A);
        System.out.println(child.i
        );
    }
}

class parent {
    public static int i = 3;
    static {
        i = 1;
        System.out.println(i);
    }


}
class  child extends parent{
    public static int B=i;
}



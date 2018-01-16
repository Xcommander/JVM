/**
 * @author jasonbourne1992x@gmail.com(xulinchao)
 * @version V1.0
 * @Class: Singleton.java
 * @Package PACKAGE_NAME
 * @Description: some thing
 * @date 2018/1/10 15:56
 */
public class Singleton {
    private volatile static Singleton instance;
    public static Singleton getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
                if (instance==null){
                    instance=new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton.getInstance();
        final finalTest xlc=new finalTest();
        xlc.setI(2);
        System.out.println(xlc.getI());


    }
}
class finalTest{
    public int i=0;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}

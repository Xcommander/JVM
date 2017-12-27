import java.lang.ClassLoader;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader myLoader=new ClassLoader() {
            @Override

            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName=name.substring(name.lastIndexOf(".")+1)+".class";
                System.out.println(name);
                InputStream inputStream=getClass().getResourceAsStream(fileName);
                if(inputStream==null){
                    return super.loadClass(name);
                }

                try {
                    byte[] b=new byte[inputStream.available()];
                    inputStream.read(b);
                    return defineClass(name,b,0,b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Thread thread=new Thread(){
            @Override
            public void setContextClassLoader(ClassLoader cl) {
                super.setContextClassLoader(cl);
            }
        };


        try {
            Object obj=myLoader.loadClass("ClassLoaderTest").newInstance();
            System.out.println(obj.getClass());
            //obj 来自于自定义的classloader ，而后面这个来自于系统的classloader，所以说要比较是否相等，必须在相同的类加载器下
            //比较
            System.out.println(obj instanceof ClassLoaderTest);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

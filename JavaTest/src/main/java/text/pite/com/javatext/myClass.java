package text.pite.com.javatext;

import java.lang.reflect.Method;

public class myClass {
    Thread thread;

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("text.pite.com.javatext.utils.JavaBean");
        Method[] months = aClass.getDeclaredMethods();
        for (Method month : months) System.out.println(month);
       // System.out.println(aClass.getProtectionDomain());
        System.out.println(aClass.getSuperclass());
    }
}

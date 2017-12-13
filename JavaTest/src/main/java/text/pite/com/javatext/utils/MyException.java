package text.pite.com.javatext.utils;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/11/7
 */

public class MyException extends Exception {
    public MyException() {
    }
    public MyException(String message) {
        super(message);
    }
    private String toString(String message){
        return message;
    }
}

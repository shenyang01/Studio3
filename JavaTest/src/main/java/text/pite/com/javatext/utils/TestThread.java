package text.pite.com.javatext.utils;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/11/10
 */

public class TestThread extends Thread {
    public Object object = new Object();
    @Override
    public void run() {
          try{
              synchronized (object){
              System.out.println("start");
                  object.wait(10000);
              System.out.println("stop");
              }
                  }catch (Exception e){
                      e.getStackTrace();
                      System.out.println("Thread 异常 "+e.getStackTrace().toString());
                  }
    }
    public void TestNotify(){
        synchronized (object){
            object.notify();
            System.out.println("notify");
        }
    }
}

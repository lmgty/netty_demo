package demothread;

/**
 * @author admin
 * @data 2020/8/21
 */
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread("新的线程1");
        myThread.start();
        MyThread myThread2 = new MyThread("新的线程2");
        myThread2.start();

//        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            System.out.println("Main线程!"+ i);
        }
    }
}

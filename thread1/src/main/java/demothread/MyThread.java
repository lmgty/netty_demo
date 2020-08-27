package demothread;

/**
 * @author admin
 * @data 2020/8/21
 */
public class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName()+":正在运行!"+i);
        }
    }
}

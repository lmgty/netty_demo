package demothread;

import lombok.SneakyThrows;

/**
 * @author admin
 * @data 2020/8/21
 */
public class Ticket implements Runnable {
    private Integer ticket = 100;

    Object object = new Object();

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                if (ticket > 0) {
                    Thread.sleep(100);
                    String name = Thread.currentThread().getName();
                    System.out.println(name + "正在卖:" + ticket--);
                }
            }
        }
    }
}

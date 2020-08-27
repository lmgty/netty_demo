package DemoLock;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author admin
 * @data 2020/8/21
 */
public class Ticket implements Runnable {
    Lock lock = new ReentrantLock();
    private Integer ticket = 100;

    Object object = new Object();

    @SneakyThrows
    @Override
    public void run() {
        while (true) {

            lock.lock();
            try {
                if (ticket > 0) {
                    Thread.sleep(10);
                    String name = Thread.currentThread().getName();
                    System.out.println(name + "正在卖:" + ticket--);
                }
            }finally {
                lock.unlock();
            }



        }
    }
}

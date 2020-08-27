package threadpool;

/**
 * @author admin
 * @data 2020/8/24
 */
public class RunnableImpl implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"创建了一个新的线程。");
    }
}

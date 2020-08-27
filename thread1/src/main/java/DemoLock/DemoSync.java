package DemoLock;

/**
 * @author admin
 * @data 2020/8/21
 */
public class DemoSync {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        Thread thread1 = new Thread(ticket,"窗口1");
        Thread thread2 = new Thread(ticket,"窗口2");
        Thread thread3 = new Thread(ticket,"窗口3");

        thread1.start();
        thread2.start();
        thread3.start();

    }
}

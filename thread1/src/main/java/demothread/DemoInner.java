package demothread;

/**
 * @author admin
 * @data 2020/8/21
 */
public class DemoInner {
    public static void main(String[] args) {

        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Runnable");
            }
        };
        new Thread(runnable).start();

    }
}

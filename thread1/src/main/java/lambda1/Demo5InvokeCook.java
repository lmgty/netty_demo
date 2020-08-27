package lambda1;

/**
 * @author admin
 * @data 2020/8/24
 */
public class Demo5InvokeCook {
    public static void main(String[] args) {
        // 使用Lambda 标准格式 调用invokeCook方法
        invokeCook(() -> System.out.println("吃饭"));
    }



    private static void invokeCook(Cook cook) {
        cook.makeFood();
    }
}

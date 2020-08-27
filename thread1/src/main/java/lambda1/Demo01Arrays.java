package lambda1;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author admin
 * @data 2020/8/24
 */
public class Demo01Arrays {
    public static void main(String[] args) {
        Person[] arr = {
                new Person("bbb", 22),
                new Person("ccc", 33),
                new Person("aaa", 11),
        };

        // 对数组中的Person对象使用Arrays的sort方法通过年龄进行升序(前 减 后)排序
        Arrays.sort(arr, (o1, o2)-> o1.getAge() - o2.getAge());
        for (Person person : arr) {
            System.out.println(person);
        }
    }
}

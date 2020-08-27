import Utils.SerializationUtil;
import model.Person;

/**
 * @author admin
 * @data 2020/8/24
 */
public class SerializationTest {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            String filename = "test-person.txt";

            Person person = Person.builder().id(1).tel("99564654").username("idea").build();
            SerializationUtil.serialize(person, filename);
            Person newPerson = SerializationUtil.deserialize(person, filename);
            System.out.println(newPerson);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin));
    }
}


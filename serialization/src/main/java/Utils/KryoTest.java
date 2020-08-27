package Utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import model.Person;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author admin
 * @data 2020/8/24
 */
public class KryoTest {
    public static void main(String[] args) throws FileNotFoundException {
        Kryo kryo=new Kryo();
        Output output = new Output(new FileOutputStream("person.txt"));
        Person person=new Person();
        person.setId(1);
        person.setUsername("idea");
        kryo.writeObject(output, person);
        output.close();
        Input input = new Input(new FileInputStream("person.txt"));
        Person person1 = kryo.readObject(input, Person.class);
        input.close();
        System.out.println(person1.toString());
        assert "idea".equals(person1.getUsername());
    }
}

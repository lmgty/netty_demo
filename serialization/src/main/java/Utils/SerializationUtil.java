package Utils;

import model.Person;

import java.io.*;

/**
 * @author admin
 * @data 2020/8/24
 */
public class SerializationUtil {
    public static void serialize(Person person, String filename){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(person);
            out.close();
            fileOutputStream.close();
            System.out.println("Serialized data is saved in "+ filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static <T> T deserialize(T t,String filename){

        T obj = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            obj = (T)in.readObject();
            in.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return obj;
    }
}

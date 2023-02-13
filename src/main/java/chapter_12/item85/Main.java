package chapter_12.item85;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("int 직렬화 & 역직렬화");
        number();

        System.out.println("object 직렬화 & 역직렬화");
        object();
    }

    private static void number() throws IOException {
        // 직렬화
        final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("item85_number.txt"));
        oos.writeInt(1);
        oos.writeInt(2);
        oos.writeInt(3);
        oos.flush();

        // 역직렬화
        final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("item85_number.txt"));
        System.out.println(ois.readInt());
        System.out.println(ois.readInt());
        System.out.println(ois.readInt());
    }

    private static void object() throws IOException, ClassNotFoundException {
        // 직렬화
        final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("item85_obj.txt"));
        oos.writeObject(new TestObj("No.1"));
        oos.writeObject(new TestObj("No.2"));
        oos.writeObject(new TestObj("No.3"));
        oos.flush();

        // 역직렬화
        final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("item85_obj.txt"));
        final TestObj testObj = (TestObj) ois.readObject();
        System.out.println(testObj.getName());
        System.out.println(ois.readObject());
        System.out.println(ois.readObject());
    }

    private static class TestObj implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String name;

        private TestObj(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "TestObj{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}

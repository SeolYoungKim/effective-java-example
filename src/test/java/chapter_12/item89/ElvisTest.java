package chapter_12.item89;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElvisTest {
    @Test
    void singletonTest() {
        assertThat(Elvis.INSTANCE).isEqualTo(Elvis.INSTANCE);
    }

    @DisplayName("역직렬화를 하게 되면 싱글턴이 깨진다.")
    @Test
    void serializableTest() throws IOException, ClassNotFoundException {
        final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("elvis.txt"));
        oos.writeObject(Elvis.INSTANCE);
        oos.flush();

        final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("elvis.txt"));
        final Elvis readObject = (Elvis) ois.readObject();

        assertThat(Elvis.INSTANCE).isNotEqualTo(readObject);
    }

    @DisplayName("readResolve 메서드를 적절히 구현해둔 경우 싱글턴이 유지된다.")
    @Test
    void readResolveTest() throws IOException, ClassNotFoundException {
        final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sam.txt"));
        oos.writeObject(Sam.INSTANCE);
        oos.flush();

        final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("sam.txt"));
        final Sam readObject = (Sam) ois.readObject();

        assertThat(Sam.INSTANCE).isEqualTo(readObject);
    }
}
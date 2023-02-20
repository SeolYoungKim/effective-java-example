package item3;

import static org.assertj.core.api.Assertions.assertThat;

import chapter_02.item3.SerializeSingletonFail;
import chapter_02.item3.SerializeSingletonOk;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// https://madplay.github.io/post/what-is-readresolve-method-and-writereplace-method
public class SerializeTest {

    @DisplayName("싱글톤이 유지되지 않는다")
    @Test
    void fail() {
        SerializeSingletonFail fail = SerializeSingletonFail.getInstance();
        byte[] serializedData = serialize(fail);
        SerializeSingletonFail result = (SerializeSingletonFail) deserialize(serializedData);

        System.out.println("[싱글톤이 유지되지 않는 경우] instance == result : " + (fail == result));
        System.out.println("[싱글톤이 유지되지 않는 경우] instance.equals(result) : " + fail.equals(result));

        assertThat(fail == result).isFalse();
        assertThat(fail).isNotEqualTo(result);
    }

    @DisplayName("싱글톤이 유지된다.")
    @Test
    void success() {
        SerializeSingletonOk success = SerializeSingletonOk.getInstance();
        byte[] serializedData = serialize(success);
        SerializeSingletonOk result = (SerializeSingletonOk) deserialize(serializedData);

        System.out.println("[싱글톤이 유지되는 경우] instance == result : " + (success == result));
        System.out.println("[싱글톤이 유지되는 경우] instance.equals(result) : " + success.equals(result));

        assertThat(success == result).isTrue();
        assertThat(success).isEqualTo(result);
    }

    private byte[] serialize(Object instance) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(instance);  // ByteArrayOutputStream에 직렬화한 내용을 작성함
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bos.toByteArray();  // ByteArrayOutputStream에는 직렬화된 내용이 기입되어 있음
    }

    private Object deserialize(byte[] serializedData) {
        ByteArrayInputStream bis = new ByteArrayInputStream(serializedData);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

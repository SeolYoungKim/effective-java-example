package chapter_06.item41;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Item41 {

    static class NotSerializableClass {

    }

    public static void main(String[] args) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(System.out)) {
            objectOutputStream.writeObject(new NotSerializableClass());  // 컴파일 타임에 오류를 안 뱉는다. 이점을 살리지 못한 예시임.
        }
    }
}

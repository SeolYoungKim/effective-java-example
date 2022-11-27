package chapter_02.item3;

import java.io.Serializable;

public class SerializeSingletonFail implements Serializable {

    private static final SerializeSingletonFail SINGLETON_FAIL = new SerializeSingletonFail();

    private final String str = "fail";

    private SerializeSingletonFail() {
    }

    public static SerializeSingletonFail getInstance() {
        return SINGLETON_FAIL;
    }
}

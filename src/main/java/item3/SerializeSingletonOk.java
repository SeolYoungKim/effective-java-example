package item3;

import java.io.Serializable;

public class SerializeSingletonOk implements Serializable {

    private static final SerializeSingletonOk SINGLETON_OK = new SerializeSingletonOk();

    private final String str = "ok";  // 선언 안해줘도 문제없던데.. 왜지?

    private SerializeSingletonOk() {
    }

    public static SerializeSingletonOk getInstance() {
        return SINGLETON_OK;
    }

    // 직렬화된 값을 역직렬화 할 때, Object를 새로 만드는 대신, 해당 메서드를 Reflection API를 이용하여 사용한다.
    private Object readResolve() {
        return SINGLETON_OK;
    }
}

package chapter_12.item85;

import java.util.HashSet;
import java.util.Set;

public class Bomb {
    private static byte[] bomb() {
        Set<Object> root = new HashSet<>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            t1.add("foo");  // t1을 t2와 다르게 만든다.
            s1.add(t1);
            s1.add(t2);

            s2.add(t1);
            s2.add(t2);

            s1 = t1;
            s2 = t2;
        }

        return serialize(root);
    }

    private static byte[] serialize(final Set<Object> root) {
        // 직렬화 수행
        return null;
    }
}

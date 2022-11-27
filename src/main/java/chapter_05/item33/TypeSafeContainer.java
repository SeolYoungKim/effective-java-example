package chapter_05.item33;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class TypeSafeContainer {

    // 컬렉션
    // 정해진 타입만 넣을 수 있어 유연하지 못하다.
    static Set<String> set = Set.of("하이", "헬로");
    static Map<String, String> map = Map.of("한국어", "안녕",
            "영어", "하이"
    );

    // 단일 원소 컨테이너 > 매개변수화 되는 대상은 컨테이너 자신이다?
    // 하나의 컨테이너에서 매개변수화 할 수 있는 타입의 수는 제한된다. -> 유연하지 못하다.
    static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "안녕하십니까");
    static AtomicReference<String> atomicReference = new AtomicReference<>();  // Random의 next()가 사용함

    // 컨테이너 대신 키를 매개변수화 하고, 컨테이너에 값을 넣거나 뺄 때 매개변수화한 키를 함께 제공하면 ??
    // 제네릭 타입 시스템이 값의 타입이 키와 같음을 보장해줄 것이다.


}

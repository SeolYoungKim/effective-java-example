package chapter_05.item33;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckedCollections {

    /**
     * 아래의 래퍼들은 Class 객체와 컬렉션의 컴파일타임 타입이 같음을 보장한다.
     * 또한, 내부 컬렉션들을 실체화 한다. -> 런타임에 잘못된 값을 넣으려 할 경우 ClassCastException을 던진다.
     * 이 래퍼들은 제네릭과 로타입을 섞어서 사용하는 앱에서 클라이언트 코드가 컬렉션에 잘못된 타입의 원소를 넣지 못하게 추적하는 데 도움을 준다.
     */
    public static void main(String[] args) {
        List<Integer> checkedList = Collections.checkedList(new ArrayList<>(), Integer.class);
        List<Integer> uncheckedList = new ArrayList<>();

        Set<String> set = Collections.checkedSet(new HashSet<>(), String.class);

        Map<String, Integer> map = Collections.checkedMap(new HashMap<>(),
                String.class, Integer.class);
    }
}

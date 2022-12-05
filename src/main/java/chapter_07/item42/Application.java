package chapter_07.item42;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<String> words = List.of("he", "she");

        // 익명 클래스
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });

        // 람다식 : 컴파일러가 타입 추론
        // 타입 추론의 대부분의 정보를 제네릭에서 얻기 때문에, 람다에서 제네릭의 사용은 더욱 더 중요해진다.
        // 이 정보를 제공하지 않으면, 컴파일러는 람다의 타입을 추론할 수 없게 된다.
        Collections.sort(words, (o1, o2) -> Integer.compare(o1.length(), o2.length()));
        Collections.sort(words, Comparator.comparingInt(String::length));
        words.sort(Comparator.comparingInt(String::length));

        // 람다는 이름이 없고 문서화도 못한다. 따라서, 코드 자체르 동작이 명확하게 설명되지 않거나 줄수가 길어질 경우 람다를 쓰지 말아야 한다.
        // 람다, 익명클래스는 직렬화 형태가 구현 별로 다를 수 있기 때문에 직렬화 하는 일은 삼가야 함.
        // 람다는 자기 자신 참조 X
    }

}

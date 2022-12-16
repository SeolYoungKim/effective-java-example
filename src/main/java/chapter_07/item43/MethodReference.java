package chapter_07.item43;

import static chapter_07.item43.MethodReference.Trash.action;

import java.nio.file.DirectoryStream.Filter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// https://stackoverflow.com/questions/35914775/java-8-difference-between-method-reference-bound-receiver-and-unbound-receiver
public class MethodReference {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 1);
        map.merge("key", 1, (oldValue, value) -> oldValue + value);

        String key = "키";
        map.merge(key, 1, (oldValue, value) -> oldValue + value);
        System.out.println(map);

        // 메서드 참조를 이용
        map.merge(key, 1, Integer::sum);

        // 메서드와 람다가 같이 있을 때는, 람다가 메서드 참조보다 간결할 수 있다.
        // 즉, 메서드 참조로 바꾸는것이 항상 이득은 아님
        //TODO 성능 한번 찍어봐야지
        Runnable r1 = Trash::action;  // 이게 더 보기 어렵다.
        Runnable r2 = () -> action(); // ObjectWorld!!

        Function<String, String> identity = Function.identity();
        Function<String, String> func = (String str) -> str;  // Function.identity() 보다는 람다식이 더 의미 전달이 잘된다.

        //TODO 메서드 참조 5 유형
        // 1. 정적 메서드 참조
        List<String> strs = List.of("123", "41234234", "13483857");
        Function<String, Integer> parseInt = Integer::parseInt;

        List<Integer> intList = strs.stream()
                .map(parseInt)
                .collect(Collectors.toList());

        // 한정적, 비한정적 관련 내용 : https://ryumodrn.tistory.com/103 참고
        // 메서드 참조를 하는 시점에 메서드를 참조할 외부 인스턴스가 존재하고 있느냐 vs 아니냐 로 나눌 수 있음
        // 한정적 : 외부 인스턴스가 이미 존재하고 있어야 함
        // 비한정적 : 외부 인스턴스는 없어도 되고, 참조하는 시점에만 인스턴스가 존재하면 됨

        //TODO 2. 한정적(인스턴스) 메서드 참조
        // - 정적 참조와 비슷
        // - 함수 객체가 받는 인수와, 참조되는 메서드가 받는 인수가 똑같음
        // - 이미 존재하는 외부 객체에 대해 람다를 호출함
        Instant then = Instant.now();
        Filter<Instant> isAfter = Instant.now()::isAfter;  // 적용 시점에 외부 객체가 있어야 함
        Filter<Instant> isAfter2 = t -> then.isAfter(t);  // 함수 객체가 받는 인수 t와, then.isAfter()가 받는 t가 같음

        Trash obj = new Trash();
        List<Trash> list = List.of(obj, obj, obj);
        Object[] objects1 = list.stream().map(objs -> objs.value()).toArray();
        Object[] objects2 = list.stream().map(Trash::value).toArray();  // 이것도 메서드 참조 호출 시, object가 존재해야 하기 때문에 한정적 메서드 참조임.

        //TODO 3. 비한정적(인스턴스) 메서드 참조
        // - 함수 객체를 적용하는 시점에 수신 객체를 알려줌
        // - 수신 객체 전달용 매개변수가 매개변수 목록의 첫번쨰로 추가 됨 ???
        // - 그 후로는 참조되는 메서드 선언에 정의된 매개변수들이 뒤따름 ???
        // - 스트림 파이프라인에서의 매핑, 필터 함수에 쓰임
        // - 람다로 넘겨받는 매개변수를 사용하기 때문에, 외부 객체가 없어도 됨
        List<String> myName = List.of("김", "딱", "구");
        String name = myName.stream()
//                .map(str -> str.toLowerCase())
                .map(String::toLowerCase)  // 얘는 이걸 적용할 시점에 외부 객체가 없어도 됨. 람다의 매개변수 중 하나를 사용할 뿐
                .collect(Collectors.joining());

        //TODO 4. 클래스, 배열 생성자 참조 (팩터리 객체로 사용) - 예시 생략 너무 쉬움

        //TODO 람다로는 불가능하지만 메서드 참조는 가능한 경우 -> 제네릭 함수 타입 구현
        // 제네릭 함수 타입은 메서드 참조 표현식으로는 가능하지만, 람다식으로는 불가능
        // 아무래도.. 메서드의 파라미터로 넘겨진 제네릭은 되지만, 아래와 같이 예외를 지칭하는 제네릭은 람다식으로 구현이 안된다고 말하는것인듯 ?
        G g = new G() {
            @Override
            public <F extends Exception> String m() throws F {
                return "악ㄱㄱㄱㄱㄱㄱㄱㄱ";
            }
        };

        //TODO 예시 더 좋은거 찾음
//        Case1 case1Lam = () -> LocalDateTime.now();  // 제네릭스가 있어서 안됨
//        Case1 case1Ref = LocalDateTime::now;  // 하지만 메서드 참조로 하면 잘됨
//
//        Case2 case2 = () -> List.of(LocalDateTime.now());  // T가 들어있으면 안됨(즉, 제네릭스가 있으면 안됨)
//        Case3 case3 = () -> List.of(LocalDateTime.now());  // T가 들어있으면 안됨(즉, 제네릭스가 있으면 안됨)
//
//        Case4 case4 = () -> List.of(LocalDateTime.now());  // List<LocalDateTime>과 같이 구체적인 제네릭 메서드는 됨. (타입 파라미터 T가 안되는듯)

    }

    interface Case1 {
        <F extends Exception> LocalDateTime m() throws Exception;
    }

    interface Case2 {
        <T> List<T> m() throws Exception;
    }

    interface Case3 {
        <T extends LocalDateTime> List<T> m() throws Exception;
    }

    interface Case4 {
        List<LocalDateTime> m() throws Exception;
    }



    interface G1 {
        <E extends Exception> Object m() throws E;

    }
    interface G2 {
        <F extends Exception> String m() throws Exception;  // 더 많은 예외를 throw하려고 시도할 수 없음??
        // 모든 메서드의 throws 절에 의해 커버되는 가능한 한 많은 예외 유형을 탐색함

    }

    interface G extends G1, G2 {  // 가장 구체적인 유형을 선택하려고 시도함
    }



    static class Trash {
        private int value = 100;

        public static void action() {
            System.out.println("악!");
        }

        public int value() {
            return value;
        }
    }

}

package chapter_07.item46;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * 스트림을 가장한 반복적 코드다
 * 이는 스트림 API의 이점을 살리지 못하여, 같은 기능의 반복적 코드보다 길고, 읽기 어렵고, 유지보수에도 좋지 않다.
 */
public class BadCase {
    public static void main(String[] args) {
        Map<String, Long> freq = new HashMap<>();
        try (Stream<String> words = new Scanner(System.in).tokens()) {
            words.forEach(word -> { //forEach에서 외부 상태를 수정하는 람다를 실행한다 -> 문제가 생긴다.
                freq.merge(word.toLowerCase(), 1L, Long::sum);  // 람다가 상태를 수정하는 것은 나쁜 코드다. (연산 결과를 보여주는 일 이상의 것을 한다.)
            });
        }
    }
}

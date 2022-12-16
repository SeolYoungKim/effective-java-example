package chapter_07.item48;

import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

public class Example {
    public static void main(String[] args) {
        ThreadLocalRandom current = ThreadLocalRandom.current();  // 단일 스레드에서 쓰고자 설계된 것

        //TODO 무작위 수들로 이뤄진 스트림을 병렬화 할 때 아래의 클래스 이용
        SplittableRandom random = new SplittableRandom();  // 병렬을 위해 설계된 것

        //그냥 Random은 Atomic 연산을 하기 때문에 내부에서 락이 걸린다. 아주 최악임 `next()`메서드 확인
    }
}

package item32;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class VarargsTest {

    public static void main(String[] args) {
        varargsTest("hi", "bye", "안녕");

        // 컴파일러가 String[]으로 자동으로 형변환 해준다.
        String[] strings = pickTwo("좋은", "빠른", "저렴한");  // ClassCastException 발생!

    }


    public static <E> void varargsTest(E... elements) {  // 순수하게 인수들만 전달하는 목적이면 OK
        E e = elements[0];
        System.out.println("e = " + e);
        System.out.println(Arrays.toString(elements));
    }

    public static void varargsTestList(List<String>... lists) {  // 타입 안정성이 깨지기 때문에, 제네릭 varargs 배열 매개변수에 값을 저장하는 것은 안전하지 않음
        List<Integer> intList = List.of(1, 2, 3);
        Object[] lists1 = lists;
        lists1[0] = intList;  // 힙 오염
    }

    public static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }

        throw new AssertionError();
    }

    private static <T> T[] toArray(T... args) {
        return args;
    }

}

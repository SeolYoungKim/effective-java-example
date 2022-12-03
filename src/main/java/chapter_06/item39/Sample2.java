package chapter_06.item39;

import java.util.ArrayList;
import java.util.List;

public class Sample2 {

    //TODO 반복 가능한 애너테이션을 여러개 달면, 하나만 달았을 때와 구분하기 위해 "컨테이너 애너테이션 타입"이 적용된다.
    // getAnnotationsByType 메서드는 이 둘을 구분하지 않는다. (반복 가능 애너테이션과 컨테이너 애너테이션 모두 가져옴)
    // 하지만, isAnnotationPresent 메서드는 둘을 명확하게 구분한다.
    // 따라서, 반복 가능 애너테이션을 여러번 단 다음, isAnnotationPresent로 반복 가능 애너테이션이 달렸는지 검사한다면, "그렇지 않다"고 대답한다.
    // 그래서, 달려있는 수와 상관 없이 모두 검사하기 위해서는 둘을 따로 따로 확인해야 한다!

    @RepeatableTest(IllegalArgumentException.class)
    @RepeatableTest(IllegalStateException.class)
    @ExceptionTest({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void doublyBad() {
        List<String> list = new ArrayList<>();
        list.addAll(5, null);
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m1() {
        int i = 0;
        i = i / i;
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m2() {
        int[] a = new int[0];
        int i = a[1];
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m3() {

    }
}

package item30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class MyOperator {

    private String str;

    private static final UnaryOperator<Object> IDENTITY = (t) -> t;

    @SuppressWarnings("unchecked")
    public static <E> // 타입 정의
    UnaryOperator<E>  // 반환
    identityFunction() {
        return (UnaryOperator<E>) IDENTITY;
    }


    public static void main(String[] args) {
        UnaryOperator<Object> identity = UnaryOperator.identity();
        String str = "하잉";
        Object apply = identity.apply(str);

        UnaryOperator<String> identity2 = UnaryOperator.identity();  // target만 보고 타입 추론 가능.
        String apply4 = identity2.apply(str);  // 타입 추론.

        Function<String, String> identity1 = Function.identity();  // target만 보고 타입 추론 가능.
        String apply1 = identity1.apply(str);  // 타입 추론.

        UnaryOperator<String> identityCustom = identityFunction();
        String apply2 = identityCustom.apply(str);

        UnaryOperator<Integer> identityCustomNumber = identityFunction();
        Integer apply3 = identityCustomNumber.apply(1);

        List<String> stringList = Collections.checkedList(new ArrayList<>(), String.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyOperator that = (MyOperator) o;
        return Objects.equals(str, that.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }

}

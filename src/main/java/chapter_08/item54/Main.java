package chapter_08.item54;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer[] integers = List.of(1, 2, 3).toArray(new Integer[0]);
        List<Integer> emptyList = Collections.emptyList();

    }

    public static <T> T[] emptyArray() {
        return (T[]) new Object[0];
    }
}

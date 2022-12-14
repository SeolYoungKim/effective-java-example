package chapter_07.item47;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubLists {
    public static void main(String[] args) {
        Stream<List<String>> of = of(List.of("a", "b", "c"));
        List<List<String>> collect = of.collect(Collectors.toList());
        System.out.println(collect);
    }

    static <E> Stream<List<E>> of(List<E> list) {
        return Stream.concat(Stream.of(Collections.emptyList()),
                prefixes(list)
                        .peek(list1 -> System.out.println("prefixes : " + list1))
                        .flatMap(SubLists::suffixes)
                        .peek(list1 -> System.out.println("suffixes : " + list1))
        );
    }

    private static <E> Stream<List<E>> prefixes(List<E> list) {
        return IntStream.rangeClosed(1, list.size())
                .mapToObj(end -> list.subList(0, end));
    }

    // a, b, c
    private static <E> Stream<List<E>> suffixes(List<E> list) {
        return IntStream.range(0, list.size())
                .mapToObj(start -> list.subList(start, list.size()));
    }

}

package chapter_07.item46;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class GoodCase {
    public static void main(String[] args) {
        Map<String, Long> freq;
        try (Stream<String> words = new Scanner(System.in).tokens()) {
            freq = words.collect(
                    groupingBy(String::toLowerCase, counting())
            );
        }

        List<String> topTen = freq.keySet().stream()
                .sorted(comparing(freq::get).reversed())
                .limit(10)
                .collect(toList());
    }
}

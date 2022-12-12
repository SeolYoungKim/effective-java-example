package chapter_07.item45;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AnagramsVer2 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/java/chapter_07/item45/dictionary.md");
        int minGroupSize = 1;

        try (Stream<String> words = Files.lines(path)) {  // 파일 스트림을 얻음
            // alphabetize()메서드를 적용한 문자열을 기준으로 dictionary에 있는 단어들을 분류함
            Map<String, List<String>> groupingByAlphabetize = words.collect(
                    groupingBy(word -> alphabetize(word.trim())));

            groupingByAlphabetize.values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .forEach(group -> System.out.println(group.size() + ": " + group));  // 람다에서는 매개변수 이름을 확실하게 하자

            System.out.println(groupingByAlphabetize);
        }

    }

    private static String alphabetize(String s) {
        String lower = s.toLowerCase();
        char[] chars = lower.toCharArray();
        Arrays.sort(chars);
        return new String(chars).trim();
    }
}

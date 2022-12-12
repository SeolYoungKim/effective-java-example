package chapter_07.item45;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Anagrams {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/main/java/chapter_07/item45/dictionary.md");
        int minGroupSize = 1;

        Map<String, Set<String>> groups = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String word = scanner.nextLine().trim();
                groups.computeIfAbsent(alphabetize(word), (key) -> new TreeSet<>())
                        .add(word);
            }
        }

        groups.values().stream()
                .filter(set -> set.size() >= minGroupSize)
                .forEach(set -> System.out.println(set.size() + ": " + set));

        System.out.println(groups);
    }

    private static String alphabetize(String s) {
        String lower = s.toLowerCase();
        char[] chars = lower.toCharArray();
        Arrays.sort(chars);
        return new String(chars).trim();
    }
}

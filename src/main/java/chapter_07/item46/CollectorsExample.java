package chapter_07.item46;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class CollectorsExample {
    public static void main(String[] args) {
        // groupingBy vs partitioningBy
        Map<Boolean, Long> count = IntStream.rangeClosed(0, 100)
                .boxed()
                .collect(groupingBy(i -> i % 2 == 0, counting()));

        Map<Boolean, Long> partitioning = IntStream.rangeClosed(0, 100)
                .boxed()
                .collect(partitioningBy(i -> i % 2 == 0, counting()));

        System.out.println(count);
        System.out.println(partitioning);

        // 랜덤 함수 리스트
        Random random = new Random();
        List<Integer> randomNumbers = IntStream.range(0, 100)
                .mapToObj(i -> random.nextInt(10) + 1)
                .collect(toList());

        // 숫자의 개수를 세어서 Map으로 만듬 (groupingBy 이용)
        Map<Integer, Long> randomNumberCount = randomNumbers.stream()
                .collect(groupingBy(randomNumber -> randomNumber, counting()));
        System.out.println(randomNumberCount);


    }
}

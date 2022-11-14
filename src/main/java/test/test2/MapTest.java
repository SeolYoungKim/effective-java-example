package test.test2;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

public class MapTest {

    public static void main(String[] args) {
        EnumMap<TestEnum, Integer> map = new EnumMap<>(TestEnum.class);

        Arrays.stream(TestEnum.values())
                .forEach(testEnum -> map.put(testEnum, 0));

        Arrays.stream(TestEnum.values())
                .forEach(testEnum -> System.out.println(testEnum.hashCode()));

        Map<TestEnum, Integer> testEnumIntegerMap = Map.copyOf(map);
        testEnumIntegerMap.forEach((key, value) -> System.out.println(key));
    }
}

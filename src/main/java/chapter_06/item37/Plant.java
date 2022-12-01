package chapter_06.item37;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Plant {

    enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL,}

    final String name;
    final LifeCycle lifeCycle;

    public Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        // Key를 Enum으로 사용하는 EnumMap : array를 사용하기 때문에 성능이 좋다.

        // EnumMap의 put
        // : value를 저장할 때, null이 들어오면, 내부의 NULL이라는 것으로 마스킹(래핑?)하여 저장.
        // : 반환은 이전 값을 반환. (마스킹되어 있었던 경우에는 null로 바꿔서 반환해줌)

        // get
        // : 타입을 체크함. (EnumMap을 생성할 때 넘겨주었던 타입 토큰을 기반으로 체크)
        EnumMap<LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(LifeCycle.class);
        plantsByLifeCycle.put(LifeCycle.ANNUAL, new HashSet<>());
        plantsByLifeCycle.put(LifeCycle.PERENNIAL, null);
        plantsByLifeCycle.get(LifeCycle.ANNUAL);

        System.out.println(plantsByLifeCycle.keySet());

        List<Plant> plants = List.of(new Plant("응애", LifeCycle.ANNUAL));

        // 그냥 스트림을 사용하면 EnumMap을 사용하지 않게 됨.
        Map<LifeCycle, List<Plant>> noEnumMap = plants.stream()
                .collect(Collectors.groupingBy(plant -> plant.lifeCycle));

        // EnumMap을 이용해서 데이터와 열거 타입을 매핑할 수 있음.
        EnumMap<LifeCycle, Set<Plant>> enumMap = plants.stream()
                .collect(Collectors.groupingBy(plant -> plant.lifeCycle,
                        () -> new EnumMap<>(LifeCycle.class), Collectors.toSet()));

        System.out.println(enumMap.keySet());

        // 스트림을 사용하면 EnumMap만 사용했을 때와는 살짝 다르게 동작한다??
        // 중첩맵??... 먼소리지..

    }
}

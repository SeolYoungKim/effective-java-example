package chapter_06.item37;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public enum Phase {
    SOLID, LIQUID, GAS, PLASMA;

    public enum Transition {
        MELT(SOLID, LIQUID),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS),
        DEPOSIT(GAS, SOLID),
        IONIZE(GAS, PLASMA),
        DEIONIZE(PLASMA, GAS)
        ;


        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        private static final Map<Phase, Map<Phase, Transition>> PHASE_MAP = Arrays.stream(values())
                .collect(groupingBy(transition -> transition.from,
                        () -> new EnumMap<>(Phase.class),
                        toMap(transition -> transition.to, transition -> transition,
                                (x, y) -> y,
                                // 선언만 하고 실제로는 쓰이지 않음. EnumMap을 얻으려면 MapFacotory가 필요한데, 해당 파라미터 다음에 작성할 수 있기 때문이다.
                                () -> new EnumMap<>(Phase.class))));

        public static Transition from(Phase from, Phase to) {
            return PHASE_MAP.get(from).get(to);
        }
    }
}

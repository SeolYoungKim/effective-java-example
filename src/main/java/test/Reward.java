package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Reward {
    FIRST(6, 2_000_000_000, false),
    SECOND(5, 30_000_000, true),
    THIRD(5, 1_500_000, false),
    FOURTH(4, 50_000, false),
    FIFTH(4, 5_000, false);

    private final int reward;
    private final int match;
    private final boolean requireBonus;

    Reward(int match, int reward, boolean requireBonus) {
        this.reward = reward;
        this.match = match;
        this.requireBonus = requireBonus;
    }

    private static final Map<Integer, Reward> matchToNonRequireBonusReward =
            Collections.unmodifiableMap(Stream.of(values())
                    .filter(Predicate.not(reward1 -> reward1.requireBonus))
                    .collect(Collectors.toMap(
                            reward1 -> reward1.reward, Function.identity())));

    private static final Map<Integer, Reward> matchToRequireBonusReward =
            Collections.unmodifiableMap(Stream.of(values())
                    .filter(Reward::isRequireBonus)
                    .collect(Collectors.toMap(
                            reward1 -> reward1.reward, reward1 -> reward1)));

    public static Reward find(int match, boolean isBonusMatched) {
        if (isBonusMatched) {
            Reward found = matchToRequireBonusReward.get(match);
            if (found != null) {
                return found;
            }
        }

        return matchToNonRequireBonusReward.get(match);
    }

    public int getMatch() {
        return match;
    }

    public int getReward() {
        return reward;
    }

    public boolean isRequireBonus() {
        return requireBonus;
    }

    public static void main(String[] args) {
        System.out.println(matchToRequireBonusReward);
    }
}


package test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum LottoResults {
    FIFTH(
            3,
            false,
            5_000
    ),
    FORTH(
            4,
            false,
            50_000
    ),
    THIRD(
            5,
            false,
            1_500_000
    ),
    SECOND(
            5,
            true,
            30_000_000
    ),
    FIRST(
            6,
            false,
            2_000_000_000
    );

    private static final Map<Integer, LottoResults> map =
            Arrays.stream(LottoResults.values())
                    .filter(lottoResults -> !lottoResults.matchBonusNumber)
                    .collect(Collectors.toUnmodifiableMap(
                            lottoResults -> lottoResults.matchNumberCount,
                            lottoResults -> lottoResults
                    ));

    private final int matchNumberCount;
    private final boolean matchBonusNumber;
    private final int winnings;

    LottoResults(int matchNumberCount, boolean matchBonusNumber, int winnings) {
        this.matchNumberCount = matchNumberCount;
        this.matchBonusNumber = matchBonusNumber;
        this.winnings = winnings;
    }

    public String stringFormat() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(matchNumberCount)
                .append("개 일치");

        if (matchBonusNumber) {
            stringBuilder
                    .append(", 보너스 볼 일치");
        }

        stringBuilder
                .append(" (")
                .append(winnings)
                .append("원)");

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        LottoResults[] values = LottoResults.values();
        System.out.println(Arrays.toString(values));
        System.out.println("하잉");
    }
}


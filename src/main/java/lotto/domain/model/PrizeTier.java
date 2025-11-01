package lotto.domain.model;


import java.util.Arrays;
import java.util.function.BiPredicate;

public enum PrizeTier {
    SIX_MATCHES((matchCount, hasBonus) -> matchCount == 6, 2_000_000_000L),
    FIVE_WITH_BONUS_MATCHES((matchCount, hasBonus) -> matchCount == 5 && hasBonus, 30_000_000L),
    FIVE_MATCHES((matchCount, hasBonus) -> matchCount == 5 && !hasBonus, 1_500_000L),
    FOUR_MATCHES((matchCount, hasBonus) -> matchCount == 4, 50_000L),
    THREE_MATCHES((matchCount, hasBonus) -> matchCount == 3, 5_000L),
    NO_RANK((matchCount, hasBonus) -> false, 0L);

    private final BiPredicate<Integer, Boolean> matchRule;
    private final long prizeMoney;

    PrizeTier(BiPredicate<Integer, Boolean> matchRule, long prizeMoney) {
        this.matchRule = matchRule;
        this.prizeMoney = prizeMoney;
    }

    public long getPrizeMoney() { return prizeMoney; }

    public static PrizeTier from(int matchCount, boolean hasBonus) {
        return Arrays.stream(values())
                .filter(t -> t.matchRule.test(matchCount, hasBonus))
                .findFirst()
                .orElse(NO_RANK);
    }
}


package lotto.domain.model;


import java.util.Arrays;

public enum PrizeTier {
    SIX_MATCHES(6, BonusType.ANY, 2_000_000_000L),
    FIVE_WITH_BONUS_MATCHES(5, BonusType.MATCHED, 30_000_000L),
    FIVE_MATCHES(5, BonusType.UNMATCHED, 1_500_000L),
    FOUR_MATCHES(4, BonusType.ANY, 50_000L),
    THREE_MATCHES(3, BonusType.ANY, 5_000L),
    NO_RANK(0, BonusType.ANY, 0L);

    private enum BonusType {
        MATCHED, UNMATCHED, ANY
    }

    private final int matchCount;
    private final BonusType bonusType;
    private final long prizeMoney;

    PrizeTier(int matchCount, BonusType bonusType, long prizeMoney) {
        this.matchCount = matchCount;
        this.bonusType = bonusType;
        this.prizeMoney = prizeMoney;
    }

    public int matchCount() {
        return matchCount;
    }

    public boolean hasBonus() {
        return bonusType == BonusType.MATCHED;
    }

    public long prizeMoney() {
        return prizeMoney;
    }

    public static PrizeTier from(int matchCount, boolean hasBonus) {
        return Arrays.stream(values())
                .filter(tier -> match(matchCount, hasBonus, tier))
                .findFirst()
                .orElse(NO_RANK);
    }

    private static boolean match(int matchCount, boolean hasBonus, PrizeTier tier) {
        if (tier.bonusType == BonusType.MATCHED) {
            return tier.matchCount == matchCount && hasBonus;
        }
        if (tier.bonusType == BonusType.UNMATCHED) {
            return tier.matchCount == matchCount && !hasBonus;
        }
        return tier.matchCount == matchCount;
    }
}


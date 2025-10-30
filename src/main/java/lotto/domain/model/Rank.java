package lotto.domain.model;

import java.util.Arrays;
import java.util.Optional;

public enum Rank {
    SIX_MATCHES(6, false, 2_000_000_000L),
    FIVE_WITH_BONUS_MATCHES(5, true, 30_000_000L),
    FIVE_MATCHES(5, false, 1_500_000L),
    FOUR_MATCHES(4, false, 50_000L),
    THREE_MATCHES(3, false, 5_000L),
    NO_RANK(0, false, 0L);

    private final int matchCount;
    private final boolean hasBonus;
    private final long prizeMoney;

    Rank(int matchCount, boolean hasBonus, long prizeMoney) {
        this.matchCount = matchCount;
        this.hasBonus = hasBonus;
        this.prizeMoney = prizeMoney;
    }

    public static Rank of(int matchCount, boolean hasBonus) {
        return Arrays.stream(Rank.values())
                .filter(rank -> rank.getMatchCount() == matchCount && rank.isHasBonus() == hasBonus)
                .findFirst()
                .orElse(NO_RANK);
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isHasBonus() {
        return hasBonus;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }
}

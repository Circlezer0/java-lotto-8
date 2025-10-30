package lotto.domain.collection;

import java.util.EnumMap;
import java.util.List;
import lotto.domain.model.Rank;

public class LottoResult {

    private final EnumMap<Rank, Integer> counts;

    private LottoResult(EnumMap<Rank, Integer> counts) {
        this.counts = new EnumMap<>(counts);
    }

    public static LottoResult from(List<Rank> calculatedRanks) {
        EnumMap<Rank, Integer> counts = new EnumMap<>(Rank.class);

        for (Rank rank : calculatedRanks) {
            counts.merge(rank, 1, Integer::sum);
        }

        return new LottoResult(counts);
    }

    public long totalPrize() {
        return counts.entrySet()
                .stream()
                .mapToLong(e -> (long) e.getKey().getPrizeMoney() * e.getValue())
                .sum();
    }

    public int count(Rank rank) {
        return counts.getOrDefault(rank, 0);
    }
}

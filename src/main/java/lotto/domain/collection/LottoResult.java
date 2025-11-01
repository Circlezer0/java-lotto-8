package lotto.domain.collection;

import java.util.EnumMap;
import java.util.List;
import lotto.domain.model.PrizeTier;

public class LottoResult {

    private final EnumMap<PrizeTier, Integer> counts;

    private LottoResult(EnumMap<PrizeTier, Integer> counts) {
        this.counts = new EnumMap<>(counts);
    }

    public static LottoResult from(List<PrizeTier> calculatedRanks) {
        EnumMap<PrizeTier, Integer> counts = new EnumMap<>(PrizeTier.class);

        for (PrizeTier rank : calculatedRanks) {
            counts.merge(rank, 1, Integer::sum);
        }

        return new LottoResult(counts);
    }

    public long totalPrize() {
        return counts.entrySet()
                .stream()
                .mapToLong(e -> e.getKey().getPrizeMoney() * e.getValue())
                .sum();
    }

    public int count(PrizeTier rank) {
        return counts.getOrDefault(rank, 0);
    }
}

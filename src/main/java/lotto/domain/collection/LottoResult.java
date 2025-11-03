package lotto.domain.collection;

import java.util.EnumMap;
import java.util.List;
import lotto.domain.model.PrizeTier;
import lotto.exception.LottoException;
import lotto.exception.code.DomainErrorCode;

public class LottoResult {

    private final EnumMap<PrizeTier, Integer> tierCounts;

    private LottoResult(EnumMap<PrizeTier, Integer> tierCounts) {
        this.tierCounts = new EnumMap<>(tierCounts);
    }

    public static LottoResult from(List<PrizeTier> calculatedPrizeTiers) {
        if(calculatedPrizeTiers == null || calculatedPrizeTiers.isEmpty()) {
            throw new LottoException(DomainErrorCode.PRIZE_LIST_CANNOT_BE_NULL_OR_EMPTY);
        }

        EnumMap<PrizeTier, Integer> counts = new EnumMap<>(PrizeTier.class);

        for (PrizeTier prizeTier : calculatedPrizeTiers) {
            counts.merge(prizeTier, 1, Integer::sum);
        }

        return new LottoResult(counts);
    }

    public long totalPrize() {
        return tierCounts.entrySet()
                .stream()
                .mapToLong(e -> e.getKey().prizeMoney() * e.getValue())
                .sum();
    }

    public int count(PrizeTier prizeTier) {
        return tierCounts.getOrDefault(prizeTier, 0);
    }
}

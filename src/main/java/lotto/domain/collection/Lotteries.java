package lotto.domain.collection;

import java.util.Iterator;
import java.util.List;
import lotto.domain.model.Lotto;
import lotto.domain.model.PrizeTier;
import lotto.domain.model.WinningNumber;
import lotto.exception.LottoException;
import lotto.exception.code.DomainErrorCode;

public class Lotteries implements Iterable<Lotto> {
    private final List<Lotto> lotteries;

    private Lotteries(List<Lotto> lotteries) {
        if(lotteries == null || lotteries.isEmpty()) {
            throw new LottoException(DomainErrorCode.LOTTO_LIST_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.lotteries = List.copyOf(lotteries);
    }

    public static Lotteries of(List<Lotto> lotteries) {
        return new Lotteries(lotteries);
    }

    public int size() {
        return lotteries.size();
    }

    public LottoResult evaluateAll(WinningNumber winningNumber) {
        List<PrizeTier> ranks = lotteries.stream()
                .map(winningNumber::evaluatePrizeTier)
                .toList();

        return LottoResult.from(ranks);
    }

    @Override
    public Iterator<Lotto> iterator() {
        return lotteries.iterator();
    }
}

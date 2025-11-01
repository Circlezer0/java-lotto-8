package lotto.domain.collection;

import java.util.Iterator;
import java.util.List;
import lotto.domain.model.Lotto;
import lotto.domain.model.PrizeTier;
import lotto.domain.model.WinningNumber;

public class Lotteries {
    private final List<Lotto> lotteries;

    private Lotteries(List<Lotto> lotteries) {
        if(lotteries == null) {
            throw new IllegalArgumentException("[ERROR] 로또 목록은 null일 수 없습니다.");
        }
        this.lotteries = List.copyOf(lotteries);
    }

    public static Lotteries of(List<Lotto> lotteries) {
        return new Lotteries(lotteries);
    }

    public int size() {
        return lotteries.size();
    }

    public Iterator<Lotto> iterator() {
        return lotteries.iterator();
    }

    public LottoResult evaluateAll(WinningNumber winningNumber) {
        List<PrizeTier> ranks = lotteries.stream()
                .map(winningNumber::evaluatePrizeTier)
                .toList();

        return LottoResult.from(ranks);
    }
}

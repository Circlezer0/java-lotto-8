package lotto.domain.collection;

import java.util.List;
import java.util.stream.Stream;
import lotto.domain.model.Lotto;
import lotto.domain.model.Rank;
import lotto.domain.model.Winning;

public class Lottos {
    private final List<Lotto> lottos;

    private Lottos(List<Lotto> lottos) {
        if(lottos == null || lottos.isEmpty()) {
            throw new IllegalArgumentException("Lottos list cannot be null or empty.");
        }
        this.lottos = List.copyOf(lottos);
    }

    public static Lottos of(List<Lotto> lottos) {
        return new Lottos(lottos);
    }

    public int count() {
        return lottos.size();
    }

    public LottoResult evaluateAllRank(Winning winning) {
        List<Rank> ranks = lottos.stream()
                .map(lotto -> lotto.evaluateRank(winning))
                .toList();

        return LottoResult.from(ranks);
    }

    public Stream<Lotto> stream() {
        return lottos.stream();
    }
}

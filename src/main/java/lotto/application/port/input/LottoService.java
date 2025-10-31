package lotto.application.port.input;

import java.util.List;
import java.util.stream.IntStream;
import lotto.application.dto.BuyCommand;
import lotto.application.dto.DrawWinningCommand;
import lotto.domain.collection.Lottos;
import lotto.domain.collection.LottoResult;
import lotto.domain.model.Lotto;
import lotto.application.port.output.NumberGenerator;
import lotto.domain.model.Winning;
import lotto.domain.vo.Money;

public class LottoService {

    private static final Integer LOTTO_PRICE = 1000;

    private final NumberGenerator numberGenerator;

    public LottoService(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public Lottos buyLottos(BuyCommand command) {
        Money money = Money.of(command.money());

        int lottoCount = money.availableLottoCount(LOTTO_PRICE);

        List<Lotto> lottos = IntStream.range(0, lottoCount)
                .mapToObj(i -> new Lotto(numberGenerator.uniqueSixInts()))
                .toList();
        return Lottos.of(lottos);
    }

    public Winning drawWinning(DrawWinningCommand command) {
        return new Winning(command.numbers(), command.bonusNumber());
    }

    public LottoResult evaluateLottos(Lottos lottos, Winning winningNumber) {
        return lottos.evaluateAllRank(winningNumber);
    }

    public double calculateYield(Lottos lottos, LottoResult lottoResult) {
        long totalPrize = lottoResult.totalPrize();
        int spentMoney = lottos.size() * LOTTO_PRICE;
        return (double) totalPrize / spentMoney * 100.0;
    }
}

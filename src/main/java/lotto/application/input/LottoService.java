package lotto.application.input;

import java.util.List;
import java.util.stream.IntStream;
import lotto.application.output.NumberGenerator;
import lotto.domain.collection.Lotteries;
import lotto.domain.collection.LottoResult;
import lotto.domain.model.Lotto;
import lotto.domain.model.WinningNumber;
import lotto.domain.utils.LottoGameRule;
import lotto.domain.vo.Money;
import lotto.domain.vo.Yield;

public class LottoService {

    private final NumberGenerator numberGenerator;

    public LottoService(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public Lotteries buyLotteries(Money money){
        int lottoCount = money.purchaseQuantity(LottoGameRule.LOTTO_PRICE);

        List<Lotto> lottos = IntStream.range(0, lottoCount)
                .mapToObj(i -> new Lotto(makeLottoNumbers()))
                .toList();

        return Lotteries.of(lottos);
    }

    public WinningNumber drawWinningNumber(List<Integer> winningNumbers, Integer bonusNumber) {
        return new WinningNumber(winningNumbers, bonusNumber);
    }

    public LottoResult evaluateLotteries(Lotteries lotteries, WinningNumber winningNumber) {
        return lotteries.evaluateAll(winningNumber);
    }

    public Yield calculateYield(Lotteries lotteries, LottoResult lottoResult) {
        long totalPrize = lottoResult.totalPrize();
        int spentMoney = lotteries.size() * LottoGameRule.LOTTO_PRICE;
        return Yield.from(totalPrize, spentMoney);
    }

    private List<Integer> makeLottoNumbers() {
        return numberGenerator.uniqueNumbersInRange(
                LottoGameRule.MIN_NUMBER,
                LottoGameRule.MAX_NUMBER,
                LottoGameRule.PICK_COUNT
        );
    }
}

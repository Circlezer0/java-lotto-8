package lotto.application.port.input;

import java.util.List;
import lotto.domain.collection.Lotteries;
import lotto.domain.collection.LottoResult;
import lotto.domain.model.WinningNumber;
import lotto.domain.vo.Money;
import lotto.domain.vo.Yield;

public interface LottoUseCase {
    Lotteries buyLotteries(Money money);

    WinningNumber drawWinningNumber(List<Integer> winningNumbers, Integer bonusNumber);

    LottoResult evaluateLotteries(Lotteries lotteries, WinningNumber winningNumber);

    Yield calculateYield(Lotteries lotteries, LottoResult lottoResult);
}

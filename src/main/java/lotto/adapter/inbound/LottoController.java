package lotto.adapter.inbound;

import java.util.List;
import java.util.function.Supplier;
import lotto.application.input.LottoService;
import lotto.domain.collection.Lotteries;
import lotto.domain.collection.LottoResult;
import lotto.domain.model.WinningNumber;
import lotto.domain.vo.Money;
import lotto.domain.vo.Yield;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public LottoController(InputView inputView, OutputView outputView, LottoService lottoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
    }

    public void run() {
        Money money = readMoney();
        Lotteries lotteries = purchaseLotteries(money);
        displayBoughtLotteries(lotteries);

        WinningNumber winningNumber = drawWinningNumber();
        LottoResult result = lottoService.evaluateLotteries(lotteries, winningNumber);

        Yield yield = lottoService.calculateYield(lotteries, result);
        displayResult(result, yield);
    }

    private Money readMoney() {
        return executeUntilValid(() -> {
            outputView.displayMoneyInputGuide();
            Money money = Money.of(inputView.readInteger());
            outputView.displayEmptyLine();
            return money;
        });
    }

    private Lotteries purchaseLotteries(Money money) {
        return lottoService.buyLotteries(money);
    }

    private void displayBoughtLotteries(Lotteries lotteries) {
        outputView.displayBoughtLotto(lotteries);
        outputView.displayEmptyLine();
    }

    private WinningNumber drawWinningNumber() {
        return executeUntilValid(() -> {
            List<Integer> winningNumbers = readWinningNumbers();
            Integer bonusNumber = readBonusNumber();
            return lottoService.drawWinningNumber(winningNumbers, bonusNumber);
        });
    }

    private List<Integer> readWinningNumbers() {
        return executeUntilValid(() -> {
            outputView.displayWinningInputGuide();
            List<Integer> numbers = inputView.readIntegers();
            outputView.displayEmptyLine();
            return numbers;
        });
    }

    private Integer readBonusNumber() {
        return executeUntilValid(() -> {
            outputView.displayBonusInputGuide();
            Integer bonus = inputView.readInteger();
            outputView.displayEmptyLine();
            return bonus;
        });
    }

    private void displayResult(LottoResult lottoResult, Yield yield) {
        outputView.displayResult(lottoResult, yield);
    }

    private <T> T executeUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException exception) {
                outputView.displayErrorMessage(exception);
            }
        }
    }
}

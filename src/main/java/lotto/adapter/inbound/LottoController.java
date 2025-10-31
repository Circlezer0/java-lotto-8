package lotto.adapter.inbound;

import java.util.List;
import lotto.application.port.input.LottoService;
import lotto.application.dto.BuyCommand;
import lotto.application.dto.DrawWinningCommand;
import lotto.domain.collection.Lottos;
import lotto.domain.collection.LottoResult;
import lotto.domain.model.Winning;

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
        Integer money = displayAndReadMoney();
        Lottos lottos = purchaseLottosAndDisplay(money);

        Winning winningNumber = drawWinningNumber();

        LottoResult lottoResult = lottoService.evaluateLottos(lottos, winningNumber);

        double yield = lottoService.calculateYield(lottos, lottoResult);

        displayLottoResult(lottoResult, yield);
    }

    private Integer displayAndReadMoney(){
        outputView.displayMoneyInputGuide();
        return inputView.readInteger();
    }

    private Lottos purchaseLottosAndDisplay(Integer money){
        outputView.displayEmptyLine();

        BuyCommand command = new BuyCommand(money);
        Lottos lottos = lottoService.buyLottos(command);

        outputView.displayBoughtLotto(lottos);
        return lottos;
    }

    private Winning drawWinningNumber(){
        List<Integer> winningNumbers = displayAndReadWinningNumbers();
        Integer bonusNumber = displayAndReadBonusNumber();

        DrawWinningCommand command = new DrawWinningCommand(winningNumbers, bonusNumber);
        return lottoService.drawWinning(command);
    }

    private Integer displayAndReadBonusNumber() {
        outputView.displayEmptyLine();

        outputView.displayBonusInputGuide();
        return inputView.readInteger();
    }

    private List<Integer> displayAndReadWinningNumbers() {
        outputView.displayEmptyLine();

        outputView.displayWinningInputGuide();
        return inputView.readIntegers();
    }

    private void displayLottoResult(LottoResult lottoResult, double yield) {
        outputView.displayEmptyLine();
        outputView.displayLottoResults(lottoResult, yield);
    }
}

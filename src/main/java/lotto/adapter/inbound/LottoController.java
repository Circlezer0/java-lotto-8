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
        Integer money = readMoney();

        Lottos lottos = purchaseLottos(money);
        displayBoughtLottos(lottos);

        Winning winningNumber = drawWinningNumber();

        LottoResult lottoResult = lottoService.evaluateLottos(lottos, winningNumber);

        double yield = lottoService.calculateYield(lottos, lottoResult);

        displayLottoResult(lottoResult, yield);
    }

    private Integer readMoney(){
        outputView.displayMoneyInputGuide();
        Integer money = inputView.readInteger();
        outputView.displayEmptyLine();
        return money;
    }

    private Lottos purchaseLottos(Integer money){
        BuyCommand command = new BuyCommand(money);
        return lottoService.buyLottos(command);
    }

    private void displayBoughtLottos(Lottos lottos){
        outputView.displayBoughtLotto(lottos);
        outputView.displayEmptyLine();
    }

    private Winning drawWinningNumber(){
        List<Integer> winningNumbers = readWinningNumbers();
        Integer bonusNumber = readBonusNumber();

        DrawWinningCommand command = new DrawWinningCommand(winningNumbers, bonusNumber);
        return lottoService.drawWinning(command);
    }

    private List<Integer> readWinningNumbers() {
        outputView.displayWinningInputGuide();
        List<Integer> winningNumbers = inputView.readIntegers();
        outputView.displayEmptyLine();
        return winningNumbers;
    }

    private Integer readBonusNumber() {
        outputView.displayBonusInputGuide();
        Integer bonusNumber = inputView.readInteger();
        outputView.displayEmptyLine();
        return bonusNumber;
    }

    private void displayLottoResult(LottoResult lottoResult, double yield) {
        outputView.displayLottoResults(lottoResult, yield);
    }
}

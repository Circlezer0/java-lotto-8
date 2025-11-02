package lotto;

import lotto.adapter.inbound.InputView;
import lotto.adapter.inbound.LottoController;
import lotto.adapter.inbound.OutputView;
import lotto.adapter.outbound.RandomNumberGenerator;
import lotto.application.input.LottoService;
import lotto.application.output.NumberGenerator;

public class Application {
    public static void main(String[] args) {
        NumberGenerator numberGenerator = new RandomNumberGenerator();

        LottoService lottoService = new LottoService(numberGenerator);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        LottoController lottoController = new LottoController(inputView, outputView, lottoService);

        lottoController.run();
    }
}

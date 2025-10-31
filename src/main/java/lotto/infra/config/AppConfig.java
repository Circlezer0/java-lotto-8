package lotto.infra.config;

import lotto.adapter.outbound.RandomNumberGenerator;
import lotto.adapter.inbound.InputView;
import lotto.adapter.inbound.LottoController;
import lotto.adapter.inbound.OutputView;
import lotto.application.port.input.LottoService;
import lotto.application.port.output.NumberGenerator;

public class AppConfig {

    public static LottoController lottoController(){
        return new LottoController(inputView(), outputView(), lottoService());
    }

    public static LottoService lottoService(){
        return new LottoService(numberGenerator());
    }

    public static NumberGenerator numberGenerator(){
        return new RandomNumberGenerator();
    }

    public static InputView inputView(){
        return new InputView();
    }

    public static OutputView outputView(){
        return new OutputView();
    }
}

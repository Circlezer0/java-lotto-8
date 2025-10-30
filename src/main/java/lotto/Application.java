package lotto;

import lotto.adapter.inbound.LottoController;
import lotto.infra.config.AppConfig;

public class Application {
    public static void main(String[] args) {
        LottoController lottoController = AppConfig.lottoController();
        lottoController.run();
    }
}

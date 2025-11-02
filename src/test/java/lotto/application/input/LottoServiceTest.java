package lotto.application.input;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import lotto.adapter.outbound.RandomNumberGenerator;
import lotto.domain.collection.Lotteries;
import lotto.domain.collection.LottoResult;
import lotto.domain.model.Lotto;
import lotto.domain.model.PrizeTier;
import lotto.domain.model.WinningNumber;
import lotto.domain.utils.LottoGameRule;
import lotto.domain.vo.Money;
import lotto.domain.vo.Yield;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoServiceTest {

    private LottoService lottoService;

    @BeforeEach
    void setUp() {
        lottoService = new LottoService(new RandomNumberGenerator());
    }

    @Test
    @DisplayName("금액에 맞는 로또 티켓을 발급한다.")
    void buyLotteriesScriptsInOrder() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    // Given
                    Money money = Money.of(3 * LottoGameRule.LOTTO_PRICE);

                    // When
                    Lotteries lotteries = lottoService.buyLotteries(money);

                    // Then
                    assertEquals(3, lotteries.size());

                    Iterator<Lotto> iterator = lotteries.iterator();
                    assertThat(iterator.next().getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
                    assertThat(iterator.next().getNumbers()).containsExactly(7, 8, 9, 10, 11, 12);
                    assertThat(iterator.next().getNumbers()).containsExactly(13, 14, 15, 16, 17, 18);
                },
                List.of(1, 2, 3, 4, 5, 6),
                List.of(7, 8, 9, 10, 11, 12),
                List.of(13, 14, 15, 16, 17, 18)
        );
    }

    @Test
    @DisplayName("당첨 번호와 보너스 번호를 입력받아 WinningNumber 객체를 생성한다.")
    void drawWinningNumber() {
        // Given
        List<Integer> winningNumbers = List.of(3, 11, 15, 29, 35, 44);
        Integer bonusNumber = 7;

        // When
        WinningNumber winningNumber = lottoService.drawWinningNumber(winningNumbers, bonusNumber);

        // Then
        PrizeTier prizeTier = winningNumber.evaluatePrizeTier(new Lotto(List.of(3, 11, 15, 29, 35, 44)));
        assertEquals(PrizeTier.SIX_MATCHES, prizeTier);

        prizeTier = winningNumber.evaluatePrizeTier(new Lotto(List.of(3, 11, 15, 29, 35, 7)));
        assertEquals(PrizeTier.FIVE_WITH_BONUS_MATCHES, prizeTier);
    }


    @Test
    @DisplayName("로또 티켓들과 당첨 번호를 입력받아 LottoResult 객체로 결과를 집계한다.")
    void evaluateLotteries() {
        // Given
        Lotteries lotteries = Lotteries.of(List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),       // NO_RANK
                new Lotto(List.of(7, 8, 9, 10, 11, 12)),    // NO_RANK
                new Lotto(List.of(3, 11, 15, 29, 35, 44)),  // SIX_MATCHES
                new Lotto(List.of(3, 11, 15, 29, 35, 7))    // FIVE_WITH_BONUS_MATCHES
        ));
        WinningNumber winningNumber = lottoService.drawWinningNumber(
                List.of(3, 11, 15, 29, 35, 44), 7);

        // When
        LottoResult lottoResult = lottoService.evaluateLotteries(lotteries, winningNumber);

        // Then
        assertEquals(1, lottoResult.count(PrizeTier.SIX_MATCHES));
        assertEquals(1, lottoResult.count(PrizeTier.FIVE_WITH_BONUS_MATCHES));
        assertEquals(0, lottoResult.count(PrizeTier.FIVE_MATCHES));
        assertEquals(0, lottoResult.count(PrizeTier.FOUR_MATCHES));
        assertEquals(0, lottoResult.count(PrizeTier.THREE_MATCHES));
        assertEquals(2, lottoResult.count(PrizeTier.NO_RANK));
    }

    @Test
    @DisplayName("로또 티켓들과 집계 결과를 입력받아 수익률을 계산한다.")
    void calculateYield() {
        // Given
        Lotteries lotteries = Lotteries.of(List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),       // NO_RANK
                new Lotto(List.of(7, 8, 9, 10, 11, 12)),    // NO_RANK
                new Lotto(List.of(3, 11, 15, 29, 35, 44)),  // SIX_MATCHES
                new Lotto(List.of(3, 11, 15, 29, 35, 7))    // FIVE_WITH_BONUS_MATCHES
        ));
        WinningNumber winningNumber = lottoService.drawWinningNumber(
                List.of(3, 11, 15, 29, 35, 44), 7);
        LottoResult lottoResult = lottoService.evaluateLotteries(lotteries, winningNumber);

        // When
        Yield yield = lottoService.calculateYield(lotteries, lottoResult);

        // Then
        long totalPrizeMoney = PrizeTier.SIX_MATCHES.prizeMoney()
                + PrizeTier.FIVE_WITH_BONUS_MATCHES.prizeMoney();
        BigDecimal expectedYield = BigDecimal.valueOf(totalPrizeMoney)
                .divide(BigDecimal.valueOf((long) LottoGameRule.LOTTO_PRICE * lotteries.size()), 1, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        assertEquals(expectedYield, yield.asPercent());
    }
}

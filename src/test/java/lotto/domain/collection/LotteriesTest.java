package lotto.domain.collection;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import lotto.domain.model.Lotto;
import lotto.domain.model.PrizeTier;
import lotto.domain.model.WinningNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LotteriesTest {

    private List<Lotto> lottoList;
    private WinningNumber winningNumber;

    @BeforeEach
    void setUp() {
        lottoList = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),       // SIX_MATCHES
                new Lotto(List.of(7, 8, 9, 10, 11, 12)),    // NO_RANK
                new Lotto(List.of(13, 14, 15, 16, 17, 18)), // NO_RANK
                new Lotto(List.of(19, 20, 21, 22, 23, 24)), // NO_RANK
                new Lotto(List.of(25, 26, 27, 28, 29, 30)), // NO_RANK
                new Lotto(List.of(31, 32, 33, 34, 35, 36)), // NO_RANK
                new Lotto(List.of(37, 38, 39, 40, 41, 42)), // NO_RANK
                new Lotto(List.of(43, 44, 45, 1, 2, 3)),    // THREE_MATCHES
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),       // FIVE_MATCHES_WITH_BONUS
                new Lotto(List.of(1, 2, 3, 4, 7, 9)),       // FOUR_MATCHES
                new Lotto(List.of(1, 2, 3, 7, 8, 9)),       // THREE_MATCHES
                new Lotto(List.of(1, 2, 7, 8, 9, 10)),      // NO_RANK
                new Lotto(List.of(1, 8, 9, 10, 11, 12))  // NO_RANK
        );

        winningNumber = new WinningNumber(
                List.of(1, 2, 3, 4, 5, 6),
                7
        );
    }

    @Test
    @DisplayName("정상 생성 테스트")
    void createLotteriesTest() {
        // When & Then
        assertDoesNotThrow(() -> Lotteries.of(lottoList));
    }

    @Test
    @DisplayName("개수 계산 테스트")
    void countLottosTest() {
        // Given
        Lotteries lotteries = Lotteries.of(lottoList);

        // When & Then
        assertEquals(lottoList.size(), lotteries.size());
    }

    @Test
    @DisplayName("iterator 테스트")
    void iteratorTest() {
        // Given
        Lotteries lotteries = Lotteries.of(lottoList);

        // When & Then
        int count = 0;
        for (Iterator<Lotto> it = lotteries.iterator(); it.hasNext(); ) {
            Lotto lotto = it.next();
            assertEquals(lottoList.get(count), lotto);
            count++;
        }
    }

    @Test
    @DisplayName("로또 결과 종합 테스트")
    void calculateResultsTest() {
        // Given
        Lotteries lotteries = Lotteries.of(lottoList);

        // When
        LottoResult results = lotteries.evaluateAll(winningNumber);

        // Then
        assertEquals(1, results.count(PrizeTier.SIX_MATCHES));
        assertEquals(1, results.count(PrizeTier.FIVE_WITH_BONUS_MATCHES));
        assertEquals(0, results.count(PrizeTier.FIVE_MATCHES));
        assertEquals(1, results.count(PrizeTier.FOUR_MATCHES));
        assertEquals(2, results.count(PrizeTier.THREE_MATCHES));
        assertEquals(8, results.count(PrizeTier.NO_RANK));
    }

    @Test
    @DisplayName("null 리스트 입력 시 예외 발생 테스트")
    void nullListThrowsIllegalArgumentException() {
        // When & Then
        assertThatThrownBy(() -> Lotteries.of(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}

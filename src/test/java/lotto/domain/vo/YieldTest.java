package lotto.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class YieldTest {

    @Test
    @DisplayName("수익률 계산 테스트")
    void yieldCreateTest() {
        // Given
        long totalPrize = 1_000_000;
        int totalSpent = 1_000;
        BigDecimal expected = new BigDecimal(100_000).setScale(1, RoundingMode.HALF_UP);

        // When
        Yield yield = Yield.from(totalPrize, totalSpent);

        // Then
        assertEquals(expected, yield.asPercent());
    }

    @Test
    @DisplayName("최대값 계산 테스트")
    void yieldMaxTest() {
        // Given
        int totalSpent = 1_000_000_000;
        long totalPrize = 2_000_000_000L * 1_000_000L;
        BigDecimal expected = new BigDecimal(200_000_000).setScale(1, RoundingMode.HALF_UP);

        // When
        Yield yield = Yield.from(totalPrize, totalSpent);

        // Then
        assertEquals(expected, yield.asPercent());
    }

    @Test
    @DisplayName("소숫점 첫째자리로 반올림 테스트")
    void yieldRoundingTest() {
        // Given
        long totalPrize = 1_500_000L;
        int totalSpent = 37_000;
        BigDecimal expected = new BigDecimal("4054.1").setScale(1, RoundingMode.HALF_UP);

        // When
        Yield yield = Yield.from(totalPrize, totalSpent);

        // Then
        assertEquals(expected, yield.asPercent());
    }

    @Test
    @DisplayName("소비 금액이 0 이하인 경우 오류 발생 테스트")
    void yieldInvalidSpentTest() {
        // Given
        long totalPrize = 1_000_000L;
        int totalSpent = 0;

        // When & Then
        assertThatThrownBy(() -> Yield.from(totalPrize, totalSpent))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}

package lotto.domain.vo;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MoneyTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 1000, 10000, 50000, 100000, 1000000000})
    @DisplayName("유효한 금액 테스트")
    void validMoneyTest(int money) {
        // When & Then
        assertDoesNotThrow(() -> Money.of(money));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1000, -1, 1000000001, 0})
    @DisplayName("유효하지 않은 금액 테스트")
    void invalidMoneyTest(int money) {
        // When & Then
        assertThatThrownBy(() -> Money.of(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }


    @ParameterizedTest
    @CsvSource({
            "1000, 1000, 1",
            "5000, 1000, 5",
            "1000000000, 1000, 1000000",
    })
    @DisplayName("로또 구매 수량 계산 테스트")
    void purchaseQuantityTest(int money, int unitPrice, int expectedQuantity) {
        // Given
        Money purchaseMoney = Money.of(money);

        // When
        int actualQuantity = purchaseMoney.purchaseQuantity(unitPrice);

        // Then
        assertEquals(expectedQuantity, actualQuantity);
    }

    @ParameterizedTest
    @CsvSource({
            "500, 1000",
            "1500, 1000",
            "1000, -100",
            "1000, 0"
    })
    @DisplayName("로또 구매 수량 계산 시 유효하지 않은 단가 테스트")
    void invalidUnitPriceTest(int money, int unitPrice) {
        // Given
        Money purchaseMoney = Money.of(money);

        // When & Then
        assertThatThrownBy(() -> purchaseMoney.purchaseQuantity(unitPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}

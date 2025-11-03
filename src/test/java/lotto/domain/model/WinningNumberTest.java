package lotto.domain.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class WinningNumberTest {

    @Test
    @DisplayName("유효한 당첨 번호 샡성 테스트")
    void validWinningNumberCreationTest() {
        // Given
        List<Integer> winningNumbers = List.of(1, 11, 15, 29, 35, 45);
        int bonusNumber = 7;

        // When & Then
        assertDoesNotThrow(() -> new WinningNumber(winningNumbers, bonusNumber));
    }

    @ParameterizedTest
    @MethodSource("prizeTestSet")
    @DisplayName("당첨 등수 확인 테스트")
    void prizeTierTest(List<Integer> winningNumbers, int bonusNumber, List<Integer> playerNumbers, PrizeTier expectedPrizeTier) {
        // Given
        WinningNumber winningNumber = new WinningNumber(winningNumbers, bonusNumber);
        Lotto playerLotto = new Lotto(playerNumbers);

        // When
        PrizeTier actualPrizeTier = winningNumber.evaluatePrizeTier(playerLotto);

        // Then
        assertEquals(expectedPrizeTier, actualPrizeTier);
    }

    private static Stream<Arguments> prizeTestSet(){
        return Stream.of(
                Arguments.of(List.of(3, 11, 15, 29, 35, 45), 7, List.of(3, 11, 15, 29, 35, 45), PrizeTier.SIX_MATCHES),
                Arguments.of(List.of(3, 11, 15, 29, 35, 45), 7, List.of(3, 11, 15, 29, 35, 7), PrizeTier.FIVE_WITH_BONUS_MATCHES),
                Arguments.of(List.of(3, 11, 15, 29, 35, 45), 7, List.of(3, 11, 15, 29, 9, 45), PrizeTier.FIVE_MATCHES),
                Arguments.of(List.of(3, 11, 15, 29, 35, 45), 7, List.of(8, 11, 15, 35, 41, 45), PrizeTier.FOUR_MATCHES),
                Arguments.of(List.of(3, 11, 15, 29, 35, 45), 7, List.of(3, 9, 15, 25, 33, 45), PrizeTier.THREE_MATCHES),
                Arguments.of(List.of(3, 11, 15, 29, 35, 45), 7, List.of(15, 22, 28, 30, 33, 44), PrizeTier.NO_RANK),
                Arguments.of(List.of(3, 11, 15, 29, 35, 45), 7, List.of(1, 2, 4, 5, 6, 7), PrizeTier.NO_RANK)
        );
    }

    @Test
    @DisplayName("당첨 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    void duplicateNumberInWinningNumbersTest() {
        // Given
        List<Integer> winningNumbers = List.of(1, 11, 15, 29, 35, 35);
        int bonusNumber = 7;

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    void bonusNumberDuplicateWithWinningNumbersTest() {
        // Given
        List<Integer> winningNumbers = List.of(1, 11, 15, 29, 35, 45);
        int bonusNumber = 15;

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    @DisplayName("당첨 번호가 null이면 예외가 발생한다.")
    void winningNumbersNullTest() {
        // Given
        int bonusNumber = 7;

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(null, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    @DisplayName("당첨 번호가 빈 리스트이면 예외가 발생한다.")
    void winningNumbersEmptyTest() {
        // Given
        List<Integer> winningNumbers = List.of();
        int bonusNumber = 7;

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    @DisplayName("당첨 번호 개수가 6개가 아니면 예외가 발생한다.")
    void winningNumberCountNotSixTest() {
        // Given
        List<Integer> lessWinningNumbers = List.of(1, 11, 15, 29, 35);
        final int bonusNumber = 7;

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(lessWinningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");

        // Given
        List<Integer> moreWinningNumbers = List.of(1, 11, 15, 29, 35, 40, 42);

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(moreWinningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 46, -5, 100})
    @DisplayName("당첨 번호에 범위를 벗어난 숫자가 있으면 예외가 발생한다.")
    void winningNumberOutOfRangeTest(int outOfRangeNumber) {
        // Given
        List<Integer> winningNumbers = List.of(1, 11, 15, 29, 35, outOfRangeNumber);
        int bonusNumber = 7;

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 46, -3, 99})
    @DisplayName("보너스 번호가 범위를 벗어나면 예외가 발생한다.")
    void bonusNumberOutOfRangeTest(int outOfRangeBonus) {
        // Given
        List<Integer> winningNumbers = List.of(1, 11, 15, 29, 35, 45);

        // When & Then
        assertThatThrownBy(() -> new WinningNumber(winningNumbers, outOfRangeBonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}

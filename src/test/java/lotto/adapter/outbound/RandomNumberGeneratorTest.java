package lotto.adapter.outbound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RandomNumberGeneratorTest {

    @Test
    @DisplayName("랜덤한 범위 내에서 중복되지 않는 숫자들을 생성한다.")
    void generateUniqueRandomNumbersTest() {
        // Given
        RandomNumberGenerator generator = new RandomNumberGenerator();

        // When & Then
        for (int i = 0; i < 1000; i++) {
            List<Integer> numbers = generator.uniqueNumbersInRange(1, 45, 6);

            assertThat(numbers)
                    .hasSize(6)
                    .allMatch(num -> num >= 1 && num <= 45)
                    .doesNotHaveDuplicates();
        }
    }

    @ParameterizedTest
    @DisplayName("범위 내에서 중복되지 않는 숫자를 생성할 수 없을 때 예외 발생")
    @CsvSource({
            "1, 5, 10",
            "10, 15, 20",
            "30, 35, 10",
    })
    void generateNumbersExceedingRangeTest(int min, int max, int count) {
        // Given
        RandomNumberGenerator generator = new RandomNumberGenerator();

        // When & Then
        assertThatThrownBy(() -> generator.uniqueNumbersInRange(min, max, count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @ParameterizedTest
    @DisplayName("유효하지 않은 파라미터가 온 경우 예외 발생")
    @CsvSource({
            "45, 1, 6",
            "1, 5, -3"
    })
    void invalidParametersTest(int min, int max, int count) {
        // Given
        RandomNumberGenerator generator = new RandomNumberGenerator();

        // When & Then
        assertThatThrownBy(() -> generator.uniqueNumbersInRange(min, max, count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}

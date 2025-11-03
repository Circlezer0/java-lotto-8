package lotto.adapter.outbound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RandomNumberGeneratorTest {

    @ParameterizedTest
    @DisplayName("유효한 정수 엣지 케이스들 테스트")
    @CsvSource({
            "1, 1, 1",
            "1, 2, 2",
            "45, 45, 1",
            "44, 45, 2",
            "1, 45, 0",
            "1, 45, 45"
    })
    void generateNumbersEdgeCasesTest(int min, int max, int count) {
        // Given
        RandomNumberGenerator generator = new RandomNumberGenerator();

        // When
        List<Integer> numbers = generator.uniqueNumbersInRange(min, max, count);

        // Then
        assertThat(numbers)
                .hasSize(count)
                .allMatch(num -> num >= min && num <= max)
                .doesNotHaveDuplicates();
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

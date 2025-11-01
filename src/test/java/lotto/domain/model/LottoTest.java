package lotto.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("로또 번호가 특정 숫자를 가지고 있는지 확인")
    void containsNumberTest() {
        // Given
        Lotto lotto = new Lotto(List.of(1, 12, 23, 34, 41, 45));

        // When
        int containsCount = 0;
        for(int i = 1; i <= 45; i++) {
            if(lotto.contains(i)) {
                containsCount++;
            }
        }

        // Then
        assertEquals(6, containsCount);
    }

    @Test
    @DisplayName("로또는 정렬된 상태로 저장된다.")
    void lottoSortedTest() {
        // Given
        Lotto lotto = new Lotto(List.of(34, 12, 45, 1, 23, 41));
        List<Integer> expectedOrder = List.of(1, 12, 23, 34, 41, 45);

        // When
        List<Integer> actualOrder = lotto.getNumbers();

        // Then
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    @DisplayName("로또 번호 개수가 6개보다 작으면 예외가 발생한다.")
    void numberCountLessThanSix() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
        assertThatThrownBy(() -> new Lotto(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    @DisplayName("로또 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    void numberOutOfRange() {
        assertThatThrownBy(() -> new Lotto(List.of(0, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");

        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}

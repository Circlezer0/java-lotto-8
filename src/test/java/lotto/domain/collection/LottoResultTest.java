package lotto.domain.collection;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import lotto.domain.model.PrizeTier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LottoResultTest {

    @Test
    @DisplayName("등수 리스트를 등수별 개수로 집계한다")
    void aggregatesCountsTest() {
        // Given
        List<PrizeTier> tiers = List.of(
                PrizeTier.THREE_MATCHES,
                PrizeTier.THREE_MATCHES,
                PrizeTier.FOUR_MATCHES,
                PrizeTier.NO_RANK,
                PrizeTier.FIVE_WITH_BONUS_MATCHES,
                PrizeTier.FIVE_WITH_BONUS_MATCHES,
                PrizeTier.FIVE_MATCHES
        );

        // When
        LottoResult result = LottoResult.from(tiers);

        // Then
        assertEquals(2, result.count(PrizeTier.THREE_MATCHES));
        assertEquals(1, result.count(PrizeTier.FOUR_MATCHES));
        assertEquals(1, result.count(PrizeTier.FIVE_MATCHES));
        assertEquals(2, result.count(PrizeTier.FIVE_WITH_BONUS_MATCHES));
        assertEquals(1, result.count(PrizeTier.NO_RANK));
        // 없는 등수는 0
        assertEquals(0, result.count(PrizeTier.SIX_MATCHES));
    }

    @Test
    @DisplayName("빈 리스트도 허용되며 합계는 0, 모든 등수 카운트는 0")
    void emptyListTest() {
        // Given
        List<PrizeTier> empty = List.of();

        // When
        LottoResult result = LottoResult.from(empty);

        // Then
        assertEquals(0L, result.totalPrize());
        for (PrizeTier t : PrizeTier.values()) {
            assertEquals(0, result.count(t));
        }
    }

    @Test
    @DisplayName("null 리스트 입력 시 IllegalArgumentException 발생")
    void nullListThrowsIllegalArgumentException() {
        // When & Then
        assertThatThrownBy(() -> LottoResult.from(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[Error]");
    }

    @ParameterizedTest(name = "tiers={0} → expectedTotal={1}")
    @MethodSource("totalPrizeCases")
    @DisplayName("등수별 상금, 개수를 모두 합산하여 총 상금 계산")
    void totalPrizeSumsTest(List<PrizeTier> tiers, long expectedTotal) {
        // Given
        LottoResult result = LottoResult.from(tiers);

        // When & Then
        assertEquals(expectedTotal, result.totalPrize());
    }

    static Stream<Arguments> totalPrizeCases() {
        return Stream.of(
                Arguments.of(
                        List.of(PrizeTier.SIX_MATCHES),
                        PrizeTier.SIX_MATCHES.getPrizeMoney()
                ),
                Arguments.of(
                        List.of(
                                PrizeTier.FIVE_WITH_BONUS_MATCHES,
                                PrizeTier.FIVE_MATCHES, PrizeTier.FIVE_MATCHES,
                                PrizeTier.FOUR_MATCHES, PrizeTier.FOUR_MATCHES, PrizeTier.FOUR_MATCHES,
                                PrizeTier.NO_RANK, PrizeTier.NO_RANK
                        ),
                        PrizeTier.FIVE_WITH_BONUS_MATCHES.getPrizeMoney()
                                + 2L * PrizeTier.FIVE_MATCHES.getPrizeMoney()
                                + 3L * PrizeTier.FOUR_MATCHES.getPrizeMoney()
                                + 2L * PrizeTier.NO_RANK.getPrizeMoney() // = 0
                ),
                Arguments.of(
                        List.of(
                                PrizeTier.THREE_MATCHES, PrizeTier.THREE_MATCHES, PrizeTier.THREE_MATCHES,
                                PrizeTier.FOUR_MATCHES,
                                PrizeTier.FIVE_WITH_BONUS_MATCHES,
                                PrizeTier.NO_RANK
                        ),
                        3L * PrizeTier.THREE_MATCHES.getPrizeMoney()
                                + PrizeTier.FOUR_MATCHES.getPrizeMoney()
                                + PrizeTier.FIVE_WITH_BONUS_MATCHES.getPrizeMoney()
                                + PrizeTier.NO_RANK.getPrizeMoney()
                )
        );
    }
}

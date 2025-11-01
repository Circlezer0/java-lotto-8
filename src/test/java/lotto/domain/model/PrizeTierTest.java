package lotto.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PrizeTierTest {

    @ParameterizedTest(name = "matchCount: {0}, bonusMatch: {1} => PrizeTier: {2}")
    @DisplayName("당첨 등수 판별 테스트")
    @CsvSource({
            "6, false, SIX_MATCHES",
            "5, true, FIVE_WITH_BONUS_MATCHES",
            "5, false, FIVE_MATCHES",
            "4, true, FOUR_MATCHES",
            "4, false, FOUR_MATCHES",
            "3, true, THREE_MATCHES",
            "3, false, THREE_MATCHES",
            "2, true, NO_RANK",
            "2, false, NO_RANK",
            "1, true, NO_RANK",
            "1, false, NO_RANK",
            "0, true, NO_RANK",
            "0, false, NO_RANK"
    })
    void prizeTierDeterminationTest(int matchCount, boolean bonusMatch, PrizeTier expectedRank) {
        // When
        PrizeTier actualRank = PrizeTier.from(matchCount, bonusMatch);

        // Then
        assertEquals(expectedRank, actualRank);
    }

    @ParameterizedTest(name = "PrizeTier: {0} => PrizeMoney: {1}")
    @DisplayName("당첨 등수에 따른 상금 테스트")
    @CsvSource({
            "SIX_MATCHES, 2000000000",
            "FIVE_WITH_BONUS_MATCHES, 30000000",
            "FIVE_MATCHES, 1500000",
            "FOUR_MATCHES, 50000",
            "THREE_MATCHES, 5000",
            "NO_RANK, 0"
    })
    void prizeMoneyTest(PrizeTier rank, long expectedPrizeMoney) {
        // When
        long actualPrizeMoney = rank.getPrizeMoney();

        // Then
        assertEquals(expectedPrizeMoney, actualPrizeMoney);
    }
}

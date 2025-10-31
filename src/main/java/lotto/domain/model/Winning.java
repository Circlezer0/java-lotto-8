package lotto.domain.model;

import java.util.List;
import lotto.domain.policy.NumberPolicy;

public class Winning {
    private final List<Integer> numbers;
    private final int bonusNumber;

    public Winning(List<Integer> numbers, int bonusNumber) {
        NumberPolicy.validateWinning(numbers, bonusNumber);
        this.numbers = List.copyOf(numbers);
        this.bonusNumber = bonusNumber;
    }

    public Rank evaluateRank(Lotto lotto) {
        int matchCount = (int) lotto.stream()
                .filter(numbers::contains)
                .count();
        boolean bonusMatch = lotto.stream().anyMatch(num -> num == bonusNumber);
        return Rank.of(matchCount, bonusMatch);
    }
}

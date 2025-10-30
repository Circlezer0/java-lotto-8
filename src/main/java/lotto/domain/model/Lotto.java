package lotto.domain.model;

import java.util.List;
import lotto.domain.policy.NumberPolicy;

public class Lotto {
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 45;
    public static final int NUMBER_COUNT = 6;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        NumberPolicy.validateLotto(numbers);
        this.numbers = List.copyOf(numbers);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public Rank evaluateRank(Winning winning) {
        int matchCount = (int) numbers.stream()
                .filter(num -> winning.getNumbers().contains(num))
                .count();
        boolean bonusMatch = numbers.contains(winning.getBonusNumber());
        return Rank.of(matchCount, bonusMatch);
    }
}

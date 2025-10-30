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

    public List<Integer> getNumbers() {
        return numbers;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }
}

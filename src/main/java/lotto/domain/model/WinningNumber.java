package lotto.domain.model;

import java.util.List;
import lotto.domain.utils.LottoNumberValidator;

public class WinningNumber {
    private final List<Integer> numbers;
    private final int bonusNumber;

    public WinningNumber(List<Integer> numbers, int bonusNumber) {
        LottoNumberValidator.validateWinning(numbers, bonusNumber);
        this.numbers = numbers.stream().sorted().toList();
        this.bonusNumber = bonusNumber;
    }

    public PrizeTier evaluatePrizeTier(Lotto lotto) {
        int matchCount = (int) numbers.stream()
                .filter(lotto::contains)
                .count();
        boolean bonusMatch = lotto.contains(bonusNumber);
        return PrizeTier.from(matchCount, bonusMatch);
    }
}

package lotto.domain.model;

import java.util.List;
import lotto.domain.utils.LottoNumberValidator;

public class WinningNumber {
    private final List<Integer> numbers;
    private final int bonusNumber;

    public WinningNumber(List<Integer> numbers, int bonusNumber) {
        validate(numbers, bonusNumber);
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

    private void validate(List<Integer> numbers, int bonusNumber) {
        LottoNumberValidator.validateSize(numbers);
        LottoNumberValidator.validateDuplicate(numbers);
        numbers.forEach(LottoNumberValidator::validateRange);
        LottoNumberValidator.validateRange(bonusNumber);
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}

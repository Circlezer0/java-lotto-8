package lotto.domain.utils;

import java.util.List;

public class LottoNumberValidator {

    private LottoNumberValidator() {}

    public static void validateNumbers(List<Integer> numbers) {
        validateSize(numbers);
        numbers.forEach(LottoNumberValidator::validateRange);
        validateDuplicate(numbers);
    }

    public static void validateWinning(List<Integer> numbers, int bonusNumber) {
        validateNumbers(numbers);
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public static void validateSize(List<Integer> numbers) {
        if (numbers.size() != LottoGameRule.PICK_COUNT) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    public static void validateRange(int number) {
        if (number < LottoGameRule.MIN_NUMBER || number > LottoGameRule.MAX_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    public static void validateDuplicate(List<Integer> numbers) {
        long distinctCount = numbers.stream().distinct().count();
        if (distinctCount != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
    }
}

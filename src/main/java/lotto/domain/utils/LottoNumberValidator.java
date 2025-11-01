package lotto.domain.utils;

import java.util.List;

public class LottoNumberValidator {
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 45;
    public static final int PICK_COUNT = 6;

    private LottoNumberValidator() {}

    public static void validateNumbers(List<Integer> numbers) {
        LottoNumberValidator.validateSize(numbers);
        numbers.forEach(LottoNumberValidator::validateRange);
        LottoNumberValidator.validateDuplicate(numbers);
    }

    public static void validateWinning(List<Integer> numbers, int bonusNumber) {
        LottoNumberValidator.validateNumbers(numbers);
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public static void validateSize(List<Integer> numbers) {
        if (numbers.size() != PICK_COUNT) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    public static void validateRange(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
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

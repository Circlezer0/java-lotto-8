package lotto.domain.utils;

import java.util.List;
import lotto.exception.LottoException;
import lotto.exception.code.DomainErrorCode;

public class LottoNumberValidator {

    private LottoNumberValidator() {}

    public static void validateNumbers(List<Integer> numbers) {
        if(numbers == null){
            throw new LottoException(DomainErrorCode.NUMBERS_CANNOT_BE_NULL);
        }
        validateSize(numbers);
        numbers.forEach(LottoNumberValidator::validateRange);
        validateDuplicate(numbers);
    }

    public static void validateWinning(List<Integer> numbers, int bonusNumber) {
        validateNumbers(numbers);
        validateRange(bonusNumber);
        if (numbers.contains(bonusNumber)) {
            throw new LottoException(DomainErrorCode.BONUS_NUMBER_CANNOT_DUPLICATE);
        }
    }

    public static void validateSize(List<Integer> numbers) {
        if (numbers.size() != LottoGameRule.PICK_COUNT) {
            throw new LottoException(DomainErrorCode.INVALID_NUMBER_COUNT);
        }
    }

    public static void validateRange(int number) {
        if (number < LottoGameRule.MIN_NUMBER || number > LottoGameRule.MAX_NUMBER) {
            throw new LottoException(DomainErrorCode.INVALID_NUMBER);
        }
    }

    public static void validateDuplicate(List<Integer> numbers) {
        long distinctCount = numbers.stream().distinct().count();
        if (distinctCount != numbers.size()) {
            throw new LottoException(DomainErrorCode.NUMBERS_CANNOT_DUPLICATE);
        }
    }
}

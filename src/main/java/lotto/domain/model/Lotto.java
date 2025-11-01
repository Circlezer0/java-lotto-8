package lotto.domain.model;

import java.util.List;
import lotto.domain.utils.LottoNumberValidator;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers.stream().sorted().toList();
    }

    private void validate(List<Integer> numbers) {
        LottoNumberValidator.validateSize(numbers);
        LottoNumberValidator.validateDuplicate(numbers);
        numbers.forEach(LottoNumberValidator::validateRange);
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}

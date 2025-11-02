package lotto.domain.model;

import java.util.List;
import lotto.domain.utils.LottoNumberValidator;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        LottoNumberValidator.validateNumbers(numbers);
        this.numbers = numbers.stream().sorted().toList();
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}

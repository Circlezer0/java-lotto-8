package lotto.domain.model;

import java.util.List;
import java.util.stream.Stream;
import lotto.domain.policy.NumberPolicy;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        NumberPolicy.validateLotto(numbers);
        this.numbers = List.copyOf(numbers);
    }

    public Stream<Integer> stream() {
        return numbers.stream();
    }
}

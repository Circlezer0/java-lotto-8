package lotto.application.output;

import java.util.List;

@FunctionalInterface
public interface NumberGenerator {
    List<Integer> uniqueNumbersInRange(int min, int max, int count);
}

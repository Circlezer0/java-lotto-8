package lotto.domain.policy;

import java.util.List;

public class NumberPolicy {

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 45;
    public static final int PICK_COUNT = 6;

    private NumberPolicy() {}

    public static void validateLotto(List<Integer> numbers) {
        validateSize(numbers);
        numbers.forEach(NumberPolicy::validateRange);
        validateDuplicate(numbers);
    }

    public static void validateWinning(List<Integer> numbers, int bonusNumber) {
        validateLotto(numbers);
        validateRange(bonusNumber);
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    private static void validateSize(List<Integer> numbers) {
        if (numbers.size() != PICK_COUNT) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    private static void validateRange(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private static void validateDuplicate(List<Integer> numbers) {
        long distinctCount = numbers.stream().distinct().count();
        if (distinctCount != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
    }
}

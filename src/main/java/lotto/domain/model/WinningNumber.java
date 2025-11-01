package lotto.domain.model;

import java.util.List;

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
        validateSize(numbers);
        validateDuplicate(numbers);
        numbers.forEach(WinningNumber::validateNumberRange);
        validateNumberRange(bonusNumber);
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    private static void validateSize(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("[ERROR] 로또 번호 리스트는 null일 수 없습니다.");
        }
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    private static void validateNumberRange(Integer number) {
        if (number < 1 || number > 45) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private static void validateDuplicate(List<Integer> numbers) {
        long distinctCount = numbers.stream()
                .distinct()
                .count();
        if(numbers.size() != distinctCount) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
    }
}

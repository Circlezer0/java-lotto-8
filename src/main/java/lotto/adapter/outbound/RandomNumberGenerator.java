package lotto.adapter.outbound;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.application.output.NumberGenerator;

public class RandomNumberGenerator implements NumberGenerator {

    @Override
    public List<Integer> uniqueNumbersInRange(int min, int max, int count) {
        if (min > max) {
            throw new IllegalArgumentException("[ERROR] 최소값, 최대값 범위 오류입니다.");
        }

        if (count < 0) {
            throw new IllegalArgumentException("[ERROR] 생성할 숫자 개수는 음수일 수 없습니다.");
        }

        if (min + count - 1 > max) {
            throw new IllegalArgumentException("[ERROR] 범위 내에서 고유한 숫자를 생성할 수 없습니다.");
        }

        return Randoms.pickUniqueNumbersInRange(min, max, count);
    }
}

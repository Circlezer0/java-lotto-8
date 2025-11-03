package lotto.adapter.outbound;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.application.port.output.NumberGenerator;
import lotto.exception.LottoException;
import lotto.exception.code.AdapterErrorCode;

public class RandomNumberGenerator implements NumberGenerator {

    @Override
    public List<Integer> uniqueNumbersInRange(int min, int max, int count) {
        if (min > max) {
            throw new LottoException(AdapterErrorCode.INVALID_NUMBER_RANGE);
        }

        if (count < 0) {
            throw new LottoException(AdapterErrorCode.INVALID_NUMBER_COUNT);
        }

        if (min + count - 1 > max) {
            throw new LottoException(AdapterErrorCode.INSUFFICIENT_UNIQUE_NUMBERS);
        }

        return Randoms.pickUniqueNumbersInRange(min, max, count);
    }
}

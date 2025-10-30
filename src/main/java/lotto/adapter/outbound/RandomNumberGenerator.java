package lotto.adapter.outbound;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.domain.model.NumberGenerator;
import lotto.domain.policy.NumberPolicy;

public class RandomNumberGenerator implements NumberGenerator {

    @Override
    public List<Integer> uniqueSixInts() {
        return Randoms.pickUniqueNumbersInRange(
                NumberPolicy.MIN_NUMBER,
                NumberPolicy.MAX_NUMBER,
                NumberPolicy.PICK_COUNT
        );
    }
}

package lotto.domain.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lotto.exception.LottoException;
import lotto.exception.code.DomainErrorCode;

public record Yield(BigDecimal percentage) {
    private static final BigDecimal MULTIPLIER = BigDecimal.valueOf(100);

    public static Yield from(long totalPrize, int spentMoney) {
        if (spentMoney <= 0) {
            throw new LottoException(DomainErrorCode.INVALID_SPENT_MONEY);
        }
        BigDecimal prize = BigDecimal.valueOf(totalPrize);
        BigDecimal spent = BigDecimal.valueOf(spentMoney);
        BigDecimal p = prize.multiply(MULTIPLIER)
                .divide(spent, 4, RoundingMode.HALF_UP); // 계산은 넉넉히
        return new Yield(p);
    }

    public BigDecimal asPercent() {
        return percentage.setScale(1, RoundingMode.HALF_UP);
    }
}

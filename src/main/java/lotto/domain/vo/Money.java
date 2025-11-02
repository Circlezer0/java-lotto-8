package lotto.domain.vo;

import lotto.exception.LottoException;
import lotto.exception.code.DomainErrorCode;

public record Money(int money) {
    private static final int MIN = 0;
    private static final int MAX = 1_000_000_000;

    public Money {
        if (money <= MIN || money > MAX) {
            throw new LottoException(DomainErrorCode.INVALID_MONEY_AMOUNT);
        }
    }

    public static Money of(int money) {
        return new Money(money);
    }

    public int purchaseQuantity(int unitPrice) {
        if (unitPrice <= 0) {
            throw new LottoException(DomainErrorCode.INVALID_UNIT_PRICE);
        }
        if (money < unitPrice) {
            throw new LottoException(DomainErrorCode.INSUFFICIENT_MONEY);
        }
        if (money % unitPrice != 0) {
            throw new LottoException(DomainErrorCode.AMOUNT_NOT_DIVISIBLE);
        }
        return money / unitPrice;
    }
}

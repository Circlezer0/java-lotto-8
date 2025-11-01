package lotto.domain.vo;

public record Money(int money) {
    private static final int MIN = 0;
    private static final int MAX = 1_000_000_000;

    public Money {
        if (money <= MIN || money > MAX) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 금액입니다.");
        }
    }

    public static Money of(int money) {
        return new Money(money);
    }

    public int purchaseQuantity(int unitPrice) {
        if (unitPrice <= 0) throw new IllegalArgumentException("[ERROR] 유효하지 않은 단가입니다.");
        if (money < unitPrice) {
            throw new IllegalArgumentException("[ERROR] 로또를 구매할 수 없습니다.");
        }
        if (money % unitPrice != 0) {
            throw new IllegalArgumentException("[ERROR] 금액이 나누어 떨어지지 않습니다.");
        }
        return money / unitPrice;
    }
}

package lotto.domain.vo;

public record Money(int money) {
    private static final int MIN = 0;

    public Money {
        if (money < MIN) {
            throw new IllegalArgumentException("금액은 0원보다 커야 합니다.");
        }
    }

    public static Money of(int money) {
        return new Money(money);
    }

    public int availableLottoCount(int lottoPrice) {
        if (lottoPrice <= 0) throw new IllegalArgumentException("[ERROR] 유효하지 않은 단가입니다.");
        if (money < lottoPrice) {
            throw new IllegalArgumentException("[ERROR] 로또 구매 금액은 최소 " + lottoPrice + "원 이상이어야 합니다.");
        }
        if (money % lottoPrice != 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 " + lottoPrice + "원 단위로 입력해야 합니다.");
        }
        return money / lottoPrice;
    }
}

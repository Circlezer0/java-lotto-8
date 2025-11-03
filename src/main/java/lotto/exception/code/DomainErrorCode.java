package lotto.exception.code;

import lotto.domain.vo.Money;
import lotto.exception.BaseErrorCode;

public enum DomainErrorCode implements BaseErrorCode {
    // LottoNumberValidator
    NUMBERS_CANNOT_BE_NULL("번호 리스트는 null일 수 없습니다."),
    BONUS_NUMBER_CANNOT_DUPLICATE("보너스 번호는 당첨 번호와 중복될 수 없습니다."),
    INVALID_NUMBER_COUNT("로또 번호의 개수가 유효하지 않습니다."),
    INVALID_NUMBER("유효한 로또 번호가 아닙니다."),
    NUMBERS_CANNOT_DUPLICATE("로또 번호는 중복될 수 없습니다."),

    // Lotteries
    LOTTO_LIST_CANNOT_BE_NULL_OR_EMPTY("로또 목록은 null이거나 비어있을 수 없습니다."),

    // LottoResult
    PRIZE_LIST_CANNOT_BE_NULL_OR_EMPTY("당첨 결과 목록은 null이거나 비어있을 수 없습니다."),

    // Money
    INVALID_MONEY_AMOUNT("유효하지 않은 금액입니다."),
    INVALID_UNIT_PRICE("유효하지 않은 단가입니다."),
    INSUFFICIENT_MONEY("로또를 구매할 수 있는 금액이 아닙니다."),
    AMOUNT_NOT_DIVISIBLE("금액이 나누어 떨어지지 않습니다."),

    // Yield
    INVALID_SPENT_MONEY("유효하지 않은 지출 금액입니다.")
    ;

    private final String message;

    DomainErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package lotto.exception.code;

import lotto.exception.BaseErrorCode;

public enum AdapterErrorCode implements BaseErrorCode {
    // InputView
    INVALID_INPUT_NUMBER("입력한 숫자가 유효하지 않습니다."),

    // RandomNumberGenerator
    INVALID_NUMBER_RANGE("숫자 생성기의 최소값, 최대값 범위 오류입니다."),
    INVALID_NUMBER_COUNT("생성할 숫자 개수는 음수일 수 없습니다."),
    INSUFFICIENT_UNIQUE_NUMBERS("범위 내에서 고유한 숫자를 생성할 수 없습니다."),
    CONTROLLER_ALREADY_CLOSED("컨트롤러가 이미 종료되었습니다."),
    ;

    private final String message;

    AdapterErrorCode(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}

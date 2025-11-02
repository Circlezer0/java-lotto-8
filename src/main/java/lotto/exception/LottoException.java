package lotto.exception;

public class LottoException extends IllegalArgumentException {

    private static final String MESSAGE_PREFIX = "[ERROR] ";
    public LottoException(BaseErrorCode baseErrorCode) {
        super(MESSAGE_PREFIX + baseErrorCode.getMessage());
    }
}

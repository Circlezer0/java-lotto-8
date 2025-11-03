package lotto.adapter.inbound;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import lotto.exception.LottoException;
import lotto.exception.code.AdapterErrorCode;

public class InputView {

    private static final String DELIMITER = ",";

    public Integer readInteger() {
        return tryParseInteger(Console.readLine());
    }

    public List<Integer> readIntegers() {
        String input = Console.readLine();
        return Arrays.stream(input.split(DELIMITER))
                .map(this::tryParseInteger)
                .toList();
    }

    private Integer tryParseInteger(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new LottoException(AdapterErrorCode.INVALID_INPUT_NUMBER);
        }
    }
}

package lotto.adapter.inbound;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;

public class InputView {

    private static final String DELIMITER = ",";

    public Integer readInteger() {
        return parseIntegerOrThrow(Console.readLine());
    }

    public List<Integer> readIntegers() {
        String input = Console.readLine();
        return Arrays.stream(input.split(DELIMITER))
                .map(this::parseIntegerOrThrow)
                .toList();
    }

    private Integer parseIntegerOrThrow(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력한 숫자가 유효하지 않습니다.");
        }
    }
}

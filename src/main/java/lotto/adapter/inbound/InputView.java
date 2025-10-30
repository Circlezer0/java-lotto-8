package lotto.adapter.inbound;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;

public class InputView {

    public Integer readInteger() {
        return parseIntegerOrThrow(Console.readLine());
    }

    public List<Integer> readIntegers() {
        String input = Console.readLine();
        return Arrays.stream(input.split(","))
                .map(this::parseIntegerOrThrow)
                .toList();
    }

    private Integer parseIntegerOrThrow(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 숫자여야 합니다.");
        }
    }
}

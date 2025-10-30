package lotto.application.dto;

import java.util.List;

public record DrawWinningCommand(List<Integer> numbers, int bonusNumber) { }

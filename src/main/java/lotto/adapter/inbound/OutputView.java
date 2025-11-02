package lotto.adapter.inbound;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lotto.domain.collection.Lotteries;
import lotto.domain.collection.LottoResult;
import lotto.domain.model.Lotto;
import lotto.domain.model.PrizeTier;
import lotto.domain.vo.Yield;

public class OutputView {
    private static final String BOUGHT_COUNT_FORMAT = "%d개를 구매했습니다.";
    private static final Collector<CharSequence, ?, String> LOTTO_FORMAT = Collectors.joining(", ", "[", "]");
    private static final List<PrizeTier> DISPLAY_PRIZE_ORDER = List.of(
            PrizeTier.THREE_MATCHES,
            PrizeTier.FOUR_MATCHES,
            PrizeTier.FIVE_MATCHES,
            PrizeTier.FIVE_WITH_BONUS_MATCHES,
            PrizeTier.SIX_MATCHES
    );
    private static final String RANK_RESULT_FORMAT = "%d개 일치%s %s - %d개";
    private static final String PRIZE_MONEY_FORMAT = "(%,d원)";
    private static final String BONUS_STRING = ", 보너스 볼 일치";
    private static final String YIELD_FORMAT = "총 수익률은 %s%%입니다.";

    public void displayErrorMessage(IllegalArgumentException exception) {
        System.out.println(exception.getMessage());
        System.out.println();
    }

    public void displayEmptyLine() {
        System.out.println();
    }

    public void displayMoneyInputGuide() {
        System.out.println("구입금액을 입력해 주세요.");
    }

    public void displayBoughtLotto(Lotteries lotteries) {
        System.out.printf(BOUGHT_COUNT_FORMAT, lotteries.size());
        System.out.println();
        lotteries.iterator()
                .forEachRemaining(lotto -> System.out.println(formatLottoNumbers(lotto)));
    }

    public void displayWinningInputGuide() {
        System.out.println("당첨 번호를 입력해 주세요.");
    }

    public void displayBonusInputGuide() {
        System.out.println("보너스 번호를 입력해 주세요.");
    }

    public void displayResult(LottoResult results, Yield yield) {
        System.out.println("당첨 통계\n---");

        DISPLAY_PRIZE_ORDER.stream()
                .map(prize -> formatPrizeResult(prize, results.count(prize)))
                .forEach(System.out::println);

        System.out.printf(YIELD_FORMAT, yield.asPercent().toPlainString());
    }

    private String formatPrizeResult(PrizeTier prizeTier, int count) {
        String prize = String.format(PRIZE_MONEY_FORMAT, prizeTier.prizeMoney());
        String bonus = "";
        if(prizeTier.hasBonus()){
            bonus = BONUS_STRING;
        }
        return String.format(RANK_RESULT_FORMAT, prizeTier.matchCount(), bonus, prize, count);
    }

    private String formatLottoNumbers(Lotto lotto) {
        return lotto.getNumbers()
                .stream()
                .map(String::valueOf)
                .collect(LOTTO_FORMAT);
    }
}

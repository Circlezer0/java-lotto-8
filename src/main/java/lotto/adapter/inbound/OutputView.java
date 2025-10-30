package lotto.adapter.inbound;

import java.util.List;
import java.util.stream.Collectors;
import lotto.domain.collection.LottoResult;
import lotto.domain.collection.Lottos;
import lotto.domain.model.Lotto;
import lotto.domain.model.Rank;

public class OutputView {
    private static final String BOUGHT_COUNT_FORMAT = "%d개를 구매했습니다.";
    private static final List<Rank> DISPLAY_RANK_ORDER = List.of(
            Rank.THREE_MATCHES,
            Rank.FOUR_MATCHES,
            Rank.FIVE_MATCHES,
            Rank.FIVE_WITH_BONUS_MATCHES,
            Rank.SIX_MATCHES
    );
    private static final String RANK_RESULT_FORMAT = "%d개 일치%s %s - %d개";
    private static final String PRIZE_MONEY_FORMAT = "(%,d원)";
    private static final String BONUS_STRING = ", 보너스 볼 일치";
    private static final String YIELD_FORMAT = "총 수익률은 %.2f%%";

    public void displayEmptyLine() {
        System.out.println();
    }

    public void displayMoneyInputGuide() {
        System.out.println("구입금액을 입력해 주세요.");
    }

    public void displayBoughtLotto(Lottos lottos) {
        System.out.printf(BOUGHT_COUNT_FORMAT, lottos.count());
        System.out.println();
        lottos.stream()
                .forEach(lotto -> System.out.println(formatLottoNumbers(lotto)));
    }

    public void displayWinningInputGuide() {
        System.out.println("당첨 번호를 입력해 주세요.");
    }

    public void displayBonusInputGuide() {
        System.out.println("보너스 번호를 입력해 주세요.");
    }

    public void displayLottoResults(LottoResult results, double yield) {
        System.out.println("당첨 통계\n---");

        DISPLAY_RANK_ORDER.stream()
                .map(rank -> formatRankResult(rank, results.count(rank)))
                .forEach(System.out::println);

        System.out.printf(YIELD_FORMAT, yield);
    }

    private String formatRankResult(Rank rank, int count) {
        String prize = String.format(PRIZE_MONEY_FORMAT, rank.getPrizeMoney());
        String bonus = "";
        if(rank.isHasBonus()){
            bonus = BONUS_STRING;
        }
        return String.format(RANK_RESULT_FORMAT, rank.getMatchCount(), bonus, prize, count);
    }

    private String formatLottoNumbers(Lotto lotto) {
        return lotto.getNumbers()
                .stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
    }
}

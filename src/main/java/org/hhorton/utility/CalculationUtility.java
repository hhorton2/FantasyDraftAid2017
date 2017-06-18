package org.hhorton.utility;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/17/17.
 */
public class CalculationUtility {
    public static void calculatePercentDiff(List<Map<String, Object>> players, double lastYearAverage, double threeYearAverage, double fiveYearAverage) {
        for (Map<String, Object> player : players) {
            double percentDiff = Math.round(100 * (((long) player.get("points") - lastYearAverage) / lastYearAverage));
            double threeYearAveragePercentDiff = Math.round(100 * (((long) player.get("three_year_average") - threeYearAverage) / threeYearAverage));
            double fiveYearAveragePercentDiff = Math.round(100 * (((long) player.get("five_year_average") - fiveYearAverage) / fiveYearAverage));
            player.put("percent_diff_top_25", percentDiff);
            player.put("percent_diff_top_25_three_year", threeYearAveragePercentDiff);
            player.put("percent_diff_top_25_five_year", fiveYearAveragePercentDiff);

        }
    }
}

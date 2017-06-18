package org.hhorton.services;

import org.hhorton.queries.receiving.GetRegularSeasonReceivingStatsByPlayerIDAndSeason;
import org.hhorton.rules.PointsRules;
import org.hhorton.utility.CalculationUtility;
import org.hhorton.utility.RarityUltility;
import org.hhorton.utility.SortUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@Service
public class ReceivingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReceivingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getReceivingStatsByPlayerIDAndSeason(String playerID, int season) {
        return new GetRegularSeasonReceivingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season);
    }

    public List<Map<String, Object>> getReceivingStatsForPlayersBySeason(int season, List<Map<String, Object>> players) {
        getPointsFromStats(season, players);
        SortUtility.sortList(players, "points");
        getTopTwentyFivePercentPercentDiff(players);
        return players;
    }

    private void getPointsFromStats(int season, List<Map<String, Object>> players) {
        for (Map<String, Object> wr : players) {
            wr.put("points", getWRPoints(wr));
            wr.put("ppg", getWRPoints(wr) / (long) wr.get("games_played"));
            getPreviousSeasonsAveragePointsForWR(season, wr);
        }
        RarityUltility.getPositionRarity(players);

    }

    private void getTopTwentyFivePercentPercentDiff(List<Map<String, Object>> players) {
        long topTwentyFivePercent = Math.round(players.size() * .25);

        long sumOfPoints = 0L;
        long threeYearAverageSumOfPoints = 0L;
        long fiveYearAverageSumOfPoints = 0L;

        long limit = topTwentyFivePercent;
        for (Map<String, Object> map : players) {
            if (map.get("receptions") != null && (long) map.get("receptions") > 40) {
                if (limit-- == 0) break;
                sumOfPoints += (long) map.get("points");
                threeYearAverageSumOfPoints += (long) map.get("three_year_average");
                fiveYearAverageSumOfPoints += (long) map.get("five_year_average");
            }
        }
        double lastYearAverage = sumOfPoints / (double) topTwentyFivePercent;
        double threeYearAverage = threeYearAverageSumOfPoints / (double) topTwentyFivePercent;
        double fiveYearAverage = fiveYearAverageSumOfPoints / (double) topTwentyFivePercent;

        CalculationUtility.calculatePercentDiff(players, lastYearAverage, threeYearAverage, fiveYearAverage);
    }

    private void getPreviousSeasonsAveragePointsForWR(int season, Map<String, Object> wr) {
        double threeYearPointSum = 0.0;
        double fiveYearPointSum = 0.0;
        for (int i = 5; i != 0; i--) {
            Map<String, Object> prior_players = new GetRegularSeasonReceivingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) wr.get("player_id"), season - i);

            threeYearPointSum += i > 2 ? getWRPoints(prior_players) : 0;
            fiveYearPointSum += getWRPoints(prior_players);
        }
        wr.put("three_year_average", (long) threeYearPointSum / 3);
        wr.put("five_year_average", (long) fiveYearPointSum / 5);
    }

    private long getWRPoints(Map<String, Object> wr) {
        long rushingYards = wr.get("rushing_yards") != null ? (long) wr.get("rushing_yards") : 0L;
        long rushingTds = wr.get("rushing_touchdowns") != null ? (long) wr.get("rushing_touchdowns") : 0L;
        long receivingYards = wr.get("receiving_yards") != null ? (long) wr.get("receiving_yards") : 0L;
        long receivingTds = wr.get("receiving_touchdowns") != null ? (long) wr.get("receiving_touchdowns") : 0L;
        long receptions = wr.get("receptions") != null ? (long) wr.get("receptions") : 0L;
        long fumbles = wr.get("fumbles") != null ? (long) wr.get("fumbles") : 0L;
        long returnTds = wr.get("return_touchdowns") != null ? (long) wr.get("return_touchdowns") : 0L;

        long rushingYardPoints = (rushingYards / 10) * PointsRules.Every_10_rushing_yards;
        long rushingTdPoints = rushingTds * PointsRules.Each_rushing_TD;
        long receivingYardPoints = (receivingYards / 10) * PointsRules.Every_10_receiving_yards;
        long receivingTouchdownPoints = receivingTds * PointsRules.Each_receiving_TD;
        long receptionPoints = receptions * PointsRules.POINTS_PER_RECEPTION;
        long fumblePoints = fumbles * PointsRules.Each_fumble_lost;
        long returnTdPoints = returnTds * PointsRules.Each_return_TD;

        return receivingTouchdownPoints + receivingYardPoints + receptionPoints + fumblePoints + returnTdPoints + rushingTdPoints + rushingYardPoints;
    }
}

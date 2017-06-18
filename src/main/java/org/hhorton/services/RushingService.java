package org.hhorton.services;

import org.hhorton.queries.rushing.GetRegularSeasonRushingStatsByPlayerIDAndSeason;
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
 * Created by hunterhorton on 6/5/17.
 */
@Service
public class RushingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RushingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getRushingStatsByPlayerIDAndSeason(String playerID, int season) {
        return new GetRegularSeasonRushingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season);
    }

    public List<Map<String, Object>> getRushingStatsForPlayersBySeason(int season, List<Map<String, Object>> players) {
        getPointsFromStats(season, players);
        SortUtility.sortList(players, "points");
        getTopTwentyFivePercentPercentDiff(players);
        return players;
    }

    private void getPointsFromStats(int season, List<Map<String, Object>> players) {
        for (Map<String, Object> rb : players) {
            rb.put("points", getRBPoints(rb));
            rb.put("ppg", getRBPoints(rb) / (long) rb.get("games_played"));
            getPreviousSeasonsAveragePointsForRB(season, rb);
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
            if (map.get("rushing_attempts") != null && (long) map.get("rushing_attempts") > 100) {
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

    private void getPreviousSeasonsAveragePointsForRB(int season, Map<String, Object> rb) {
        double threeYearPointSum = 0.0;
        double fiveYearPointSum = 0.0;
        for (int i = 5; i != 0; i--) {
            Map<String, Object> prior_players = new GetRegularSeasonRushingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) rb.get("player_id"), season - i);

            threeYearPointSum += i > 2 ? getRBPoints(prior_players) : 0;
            fiveYearPointSum += getRBPoints(prior_players);
        }
        rb.put("three_year_average", (long) threeYearPointSum / 3);
        rb.put("five_year_average", (long) fiveYearPointSum / 5);
    }

    private long getRBPoints(Map<String, Object> rb) {
        long rushingYards = rb.get("rushing_yards") != null ? (long) rb.get("rushing_yards") : 0L;
        long rushingTds = rb.get("rushing_touchdowns") != null ? (long) rb.get("rushing_touchdowns") : 0L;
        long receivingYards = rb.get("receiving_yards") != null ? (long) rb.get("receiving_yards") : 0L;
        long receivingTds = rb.get("receiving_touchdowns") != null ? (long) rb.get("receiving_touchdowns") : 0L;
        long receptions = rb.get("receptions") != null ? (long) rb.get("receptions") : 0L;
        long fumbles = rb.get("fumbles") != null ? (long) rb.get("fumbles") : 0L;
        long returnTds = rb.get("return_touchdowns") != null ? (long) rb.get("return_touchdowns") : 0L;

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
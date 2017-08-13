package org.hhorton.services;

import org.hhorton.queries.passing.GetRegularSeasonPassingStatsByPlayerIDAndSeason;
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
public class PassingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PassingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getPassingStatsByPlayerIDAndSeason(String playerID, int season) {
        return new GetRegularSeasonPassingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season);
    }

    public List<Map<String, Object>> getPassingStatsForPlayersBySeason(int season, List<Map<String, Object>> players) {
        getPointsFromStats(season, players);
        SortUtility.sortList(players, "points");
        getTopTwentyFivePercentPercentDiff(players);
        return players;
    }

    private void getPointsFromStats(int season, List<Map<String, Object>> players) {
        for (Map<String, Object> qb : players) {
            qb.put("points", getQBPoints(qb));
            qb.put("ppg", getQBPoints(qb)/(long)qb.get("games_played"));
            getPreviousSeasonsAveragePointsForQB(season, qb);
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
            if (map.get("passing_attempts") != null && (long) map.get("passing_attempts") > 100) {
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

    private void getPreviousSeasonsAveragePointsForQB(int season, Map<String, Object> qb) {
        double threeYearPointSum = 0.0;
        double fiveYearPointSum = 0.0;
        for (int i = 5; i != 0; i--) {
            Map<String, Object> prior_players = new GetRegularSeasonPassingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) qb.get("player_id"), season - i);

            threeYearPointSum += i > 2 ? getQBPoints(prior_players) : 0;
            fiveYearPointSum += getQBPoints(prior_players);
        }
        qb.put("three_year_average", (long) threeYearPointSum / 3);
        qb.put("five_year_average", (long) fiveYearPointSum / 5);
    }

    private long getQBPoints(Map<String, Object> qb) {
        long passingYards = qb.get("passing_yards") != null ? (long) qb.get("passing_yards") : 0L;
        long passingTds = qb.get("passing_touchdowns") != null ? (long) qb.get("passing_touchdowns") : 0L;
        long interceptions = qb.get("passing_interceptions") != null ? (long) qb.get("passing_interceptions") : 0L;
        long rushingYards = qb.get("rushing_yards") != null ? (long) qb.get("rushing_yards") : 0L;
        long rushingTds = qb.get("rushing_touchdowns") != null ? (long) qb.get("rushing_touchdowns") : 0L;
        long passingYardPoints = (passingYards / 25) * PointsRules.Every_25_passing_yards;
        long passingTdPoints = passingTds * PointsRules.Each_passing_TD;
        long interceptionPoints = interceptions * PointsRules.Each_interception_thrown;
        long rushingYardPoints = (rushingYards/10) * PointsRules.Every_10_rushing_yards;
        long rushingTdPoints = rushingTds * PointsRules.Each_rushing_TD;
        return passingYardPoints + passingTdPoints + interceptionPoints + rushingTdPoints + rushingYardPoints;
    }

}
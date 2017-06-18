package org.hhorton.services;

import org.hhorton.queries.kicking.GetRegularSeasonKickingStatsByPlayerIDAndSeason;
import org.hhorton.queries.rushing.GetRegularSeasonRushingStatsByPlayerIDAndSeason;
import org.hhorton.rules.PointsRules;
import org.hhorton.utility.CalculationUtility;
import org.hhorton.utility.SortUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/17/17.
 */
@Service
public class KickingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public KickingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getKickingStatsByPlayerIDAndSeason(String playerID, int season) {
        return new GetRegularSeasonRushingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season);
    }


    public List<Map<String, Object>> getKickingStatsForPlayersBySeason(int season, List<Map<String, Object>> players) {
        getPointsFromStats(season, players);
        SortUtility.sortList(players, "points");
        getTopTwentyFivePercentPercentDiff(players);
        return players;
    }

    private void getPointsFromStats(int season, List<Map<String, Object>> players) {
        for (Map<String, Object> k : players) {
            k.put("points", getKPoints(k));
            getPreviousSeasonsAveragePointsForK(season, k);
        }
    }

    private void getTopTwentyFivePercentPercentDiff(List<Map<String, Object>> players) {
        long topTwentyFivePercent = Math.round(players.size() * .25);

        long sumOfPoints = 0L;
        long threeYearAverageSumOfPoints = 0L;
        long fiveYearAverageSumOfPoints = 0L;

        long limit = topTwentyFivePercent;
        for (Map<String, Object> map : players) {
            if (map.get("fgm") != null && (long) map.get("fgm") > 20) {
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

    private void getPreviousSeasonsAveragePointsForK(int season, Map<String, Object> k) {
        double threeYearPointSum = 0.0;
        double fiveYearPointSum = 0.0;
        for (int i = 5; i != 0; i--) {
            Map<String, Object> prior_players;
            try {
                prior_players = new GetRegularSeasonKickingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) k.get("player_id"), season - i);
            } catch (EmptyResultDataAccessException ex) {
                continue;
            }
            threeYearPointSum += i > 2 ? getKPoints(prior_players) : 0;
            fiveYearPointSum += getKPoints(prior_players);
        }
        k.put("three_year_average", (long) threeYearPointSum / 3);
        k.put("five_year_average", (long) fiveYearPointSum / 5);
    }

    private long getKPoints(Map<String, Object> k) {
        long xp = k.get("extra_points") != null ? (long) k.get("extra_points") : 0L;
        long fg1 = k.get("fg_0_to_19") != null ? (long) k.get("fg_0_to_19") : 0L;
        long fg2 = k.get("fg_20_to_29") != null ? (long) k.get("fg_20_to_29") : 0L;
        long fg3 = k.get("fg_30_to_39") != null ? (long) k.get("fg_30_to_39") : 0L;
        long fg4 = k.get("fg_40_to_49") != null ? (long) k.get("fg_40_to_49") : 0L;
        long fg5 = k.get("fg_50_plus") != null ? (long) k.get("fg_50_plus") : 0L;

        long xpPoints = xp * PointsRules.Each_extra;
        long fg1Points = fg1 * PointsRules.Field_goal_0_to_19_yards_;
        long fg2Points = fg2 * PointsRules.Field_goal_20_to_29_yards_;
        long fg3Points = fg3 * PointsRules.Field_goal_30_to_39_yards_;
        long fg4Points = fg4 * PointsRules.Field_goal_40_to_49_yards_;
        long fg5Points = fg5 * PointsRules.Field_goal_50_plus_yards_;

        return xpPoints + fg1Points + fg2Points + fg3Points + fg4Points + fg5Points;
    }
}

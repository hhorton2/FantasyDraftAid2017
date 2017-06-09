package org.hhorton.services;

import org.hhorton.queries.lists.*;
import org.hhorton.queries.passing.GetRegularSeasonPassingStatsByPlayerIDAndSeason;
import org.hhorton.queries.receiving.GetRegularSeasonReceivingStatsByPlayerIDAndYear;
import org.hhorton.queries.rushing.GetRegularSeasonRushingStatsByPlayerIDAndSeason;
import org.hhorton.rules.PointsRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@Service
public class PlayerService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PlayerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getAllQuarterBacksFromSeason(int season) {
        return new GetAllQuarterbacksWhoPlayedInSeason(this.jdbcTemplate).execute(season);
    }

    public List<Map<String, Object>> getAllQuarterBacksFromSeasonWithStats(int season) {
        List<Map<String, Object>> result = new GetAllQuarterbacksWhoPlayedInSeasonWithStats(this.jdbcTemplate).execute(season);
        for (Map<String, Object> qb : result) {
            long passing_yards = (long) qb.get("passing_yards");
            long passing_tds = (long) qb.get("passing_touchdowns");
            qb.put("points", getQBPoints(passing_yards, passing_tds));

            double threeYearPointSum = 0.0;
            double fiveYearPointSum = 0.0;
            for (int i = 5; i != 0; i--) {
                Map<String, Object> prior_result = new GetRegularSeasonPassingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) qb.get("player_id"), season - i);
                long prior_passing_yards = prior_result.get("passing_yards") != null ? (long) prior_result.get("passing_yards") : 0L;
                long prior_passing_tds = prior_result.get("passing_touchdowns") != null ? (long) prior_result.get("passing_touchdowns") : 0L;
                threeYearPointSum += i > 2 ? getQBPoints(prior_passing_yards, prior_passing_tds) : 0;
                fiveYearPointSum += getQBPoints(prior_passing_yards, prior_passing_tds);
            }
            qb.put("three_year_average", (long) threeYearPointSum / 3);
            qb.put("five_year_average", (long) fiveYearPointSum / 5);
        }
        sortList(result, "points");

        long toptwentyfivepercent = Math.round(result.size() * .25);

        long sumOfPoints = 0L;
        long limit = toptwentyfivepercent;
        for (Map<String, Object> map : result) {
            if (map.get("passing_attempts") != null && (long) map.get("passing_attempts") > 100) {
                if (limit-- == 0) break;
                sumOfPoints += (long) map.get("points");
            }
        }

        long three_year_averagesumOfPoints = 0L;
        limit = toptwentyfivepercent;
        for (Map<String, Object> map : result) {
            if (map.get("passing_attempts") != null && (long) map.get("passing_attempts") > 100) {
                if (limit-- == 0) break;
                three_year_averagesumOfPoints += (long) map.get("three_year_average");
            }
        }

        long five_year_averagesumOfPoints = 0L;
        limit = toptwentyfivepercent;
        for (Map<String, Object> map : result) {
            if (map.get("passing_attempts") != null && (long) map.get("passing_attempts") > 100) {
                if (limit-- == 0) break;
                five_year_averagesumOfPoints += (long) map.get("five_year_average");
            }
        }
        double lastYearAverage = sumOfPoints / (double) toptwentyfivepercent;
        double threeYearAverage = three_year_averagesumOfPoints / (double) toptwentyfivepercent;
        double fiveYearAverage = five_year_averagesumOfPoints / (double) toptwentyfivepercent;

        for (Map<String, Object> qb : result) {
            double percentDiff = Math.round(100 * (((long) qb.get("points") - lastYearAverage) / lastYearAverage));
            double threeYearAveragepercentDiff = Math.round(100 * (((long) qb.get("three_year_average") - threeYearAverage) / threeYearAverage));
            double fiveYearAveragepercentDiff = Math.round(100 * (((long) qb.get("five_year_average") - fiveYearAverage) / fiveYearAverage));
            qb.put("percent_diff_top_25", percentDiff);
            qb.put("percent_diff_top_25_three_year", threeYearAveragepercentDiff);
            qb.put("percent_diff_top_25_five_year", fiveYearAveragepercentDiff);

        }
        return result;
    }

    private long getQBPoints(long yards, long tds) {
        long yardpoints = (yards / 25) * PointsRules.Every_25_passing_yards;
        long tdpoints = tds * PointsRules.Each_passing_TD;
        return yardpoints + tdpoints;
    }

    public List<Map<String, Object>> getAllRunningBacksFromSeason(int season) {
        List<Map<String, Object>> result = new GetAllRunningBacksWhoPlayedInSeason(this.jdbcTemplate).execute(season);
        for (Map<String, Object> rb : result) {
            rb.putAll(new GetRegularSeasonRushingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) rb.get("player_id"), season));
        }
        sortList(result, "rushing_yards");
        return result;
    }

    public List<Map<String, Object>> getAllWideReceiversFromSeason(int season) {
        List<Map<String, Object>> result = new GetAllWideReceiversWhoPlayedInSeason(this.jdbcTemplate).execute(season);
        for (Map<String, Object> wr : result) {
            wr.putAll(new GetRegularSeasonReceivingStatsByPlayerIDAndYear(this.jdbcTemplate).execute((String) wr.get("player_id"), season));
        }
        sortList(result, "receiving_yards");
        return result;
    }

    public List<Map<String, Object>> getAllTideEndsFromSeason(int season) {
        List<Map<String, Object>> result = new GetAllTideEndsWhoPlayedInSeason(this.jdbcTemplate).execute(season);
        for (Map<String, Object> te : result) {
            te.putAll(new GetRegularSeasonReceivingStatsByPlayerIDAndYear(this.jdbcTemplate).execute((String) te.get("player_id"), season));
        }
        sortList(result, "receiving_yards");
        return result;
    }

    private void sortList(List<Map<String, Object>> result, String sortByColumn) {
        result.sort((Map<String, Object> map, Map<String, Object> map2) -> {
            long m1SortColumn = (long) map.get(sortByColumn);
            long m2SortColumn = (long) map2.get(sortByColumn);

            if (m1SortColumn < m2SortColumn) {
                return 1;
            } else if (m1SortColumn > m2SortColumn) {
                return -1;
            } else {
                return 0;
            }
        });
    }
}

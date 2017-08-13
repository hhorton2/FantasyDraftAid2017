package org.hhorton.services;

import org.hhorton.queries.defense.GetRegularSeasonDefensivePointsAllowedByTeamAndSeason;
import org.hhorton.queries.defense.GetRegularSeasonDefensiveStatsByTeamAndSeason;
import org.hhorton.rules.PointsRules;
import org.hhorton.utility.CalculationUtility;
import org.hhorton.utility.RarityUltility;
import org.hhorton.utility.SortUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/18/17.
 */
@Service
public class DefenseService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DefenseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getDefensiveStatsByTeamAndSeason(String team, int season) {
        Map<String, Object> stats = new GetRegularSeasonDefensiveStatsByTeamAndSeason(this.jdbcTemplate).execute(team, season);
        getPointsAllowedStats(season, stats);
        return stats;
    }


    public List<Map<String, Object>> getDefensiveStatsForTeamsBySeason(int season, List<Map<String, Object>> teams) {
        getPointsFromStats(season, teams);
        SortUtility.sortList(teams, "points");
        getTopTwentyFivePercentPercentDiff(teams);
        return teams;
    }

    private void getPointsFromStats(int season, List<Map<String, Object>> teams) {
        for (Map<String, Object> team : teams) {
            getPointsAllowedStats(season, team);
            team.put("points", getDefPoints(team));
            team.put("ppg", getDefPoints(team) / (long) team.get("games_played"));
            getPreviousSeasonsAveragePointsForTeam(season, team);
        }
        RarityUltility.getPositionRarity(teams);
    }

    private void getPointsAllowedStats(int season, Map<String, Object> team) {
        List<Map<String, Object>> pointsAllowed = new GetRegularSeasonDefensivePointsAllowedByTeamAndSeason(this.jdbcTemplate).execute(season, (String) team.get("team"));
        long shutouts = 0L;
        long xs = 0L;
        long s = 0L;
        long m = 0L;
        long l = 0L;
        long xl = 0L;
        long xxl = 0L;
        for (Map<String, Object> game : pointsAllowed) {
            int pa = (int) game.get("points_allowed");
            if (pa == 0) {
                shutouts++;
            } else if (pa < 7) {
                xs++;
            } else if (pa < 14) {
                s++;
            } else if (pa < 21) {
                m++;
            } else if (pa < 28) {
                l++;
            } else if (pa < 35) {
                xl++;
            } else {
                xxl++;
            }
        }
        team.put("pa_1_to_6", xs);
        team.put("pa_7_to_13", s);
        team.put("pa_14_to_20", m);
        team.put("pa_21_to_27", l);
        team.put("pa_28_to_34", xl);
        team.put("pa_35_plus", xxl);
        team.put("shutouts", shutouts);
    }

    private void getTopTwentyFivePercentPercentDiff(List<Map<String, Object>> teams) {
        long topTwentyFivePercent = Math.round(teams.size() * .25);

        long sumOfPoints = 0L;
        long threeYearAverageSumOfPoints = 0L;
        long fiveYearAverageSumOfPoints = 0L;

        long limit = topTwentyFivePercent;
        for (Map<String, Object> map : teams) {
            if (limit-- == 0) break;
            sumOfPoints += (long) map.get("points");
            threeYearAverageSumOfPoints += (long) map.get("three_year_average");
            fiveYearAverageSumOfPoints += (long) map.get("five_year_average");

        }
        double lastYearAverage = sumOfPoints / (double) topTwentyFivePercent;
        double threeYearAverage = threeYearAverageSumOfPoints / (double) topTwentyFivePercent;
        double fiveYearAverage = fiveYearAverageSumOfPoints / (double) topTwentyFivePercent;

        CalculationUtility.calculatePercentDiff(teams, lastYearAverage, threeYearAverage, fiveYearAverage);
    }

    private void getPreviousSeasonsAveragePointsForTeam(int season, Map<String, Object> team) {
        double threeYearPointSum = 0.0;
        double fiveYearPointSum = 0.0;
        for (int i = 5; i != 0; i--) {
            Map<String, Object> prior_team;
            try {
                prior_team = new GetRegularSeasonDefensiveStatsByTeamAndSeason(this.jdbcTemplate).execute((String) team.get("full_name"), season - i);
                getPointsAllowedStats(season - i, prior_team);
            } catch (EmptyResultDataAccessException ex) {
                continue;
            }
            threeYearPointSum += i > 2 ? getDefPoints(prior_team) : 0;
            fiveYearPointSum += getDefPoints(prior_team);
        }
        team.put("three_year_average", (long) threeYearPointSum / 3);
        team.put("five_year_average", (long) fiveYearPointSum / 5);
    }

    private long getDefPoints(Map<String, Object> def) {
        long interceptions = def.get("interceptions") != null ? (long) def.get("interceptions") : 0L;
        long fumbles = def.get("fumble_recoveries") != null ? (long) def.get("fumble_recoveries") : 0L;
        float sacks = def.get("sacks") != null ? (float) def.get("sacks") : 0;
        long defensiveTd = def.get("defensive_touchdowns") != null ? (long) def.get("defensive_touchdowns") : 0L;
        long blockedFieldGoals = def.get("blocked_fgs") != null ? (long) def.get("blocked_fgs") : 0L;
        long safeties = def.get("safeties") != null ? (long) def.get("safeties") : 0L;
        long pa = def.get("pa_1_to_6") != null ? (long) def.get("pa_1_to_6") : 0L;
        long pa1 = def.get("pa_7_to_13") != null ? (long) def.get("pa_7_to_13") : 0L;
        long pa2 = def.get("pa_14_to_20") != null ? (long) def.get("pa_14_to_20") : 0L;
        long pa3 = def.get("pa_21_to_27") != null ? (long) def.get("pa_21_to_27") : 0L;
        long pa4 = def.get("pa_28_to_34") != null ? (long) def.get("pa_28_to_34") : 0L;
        long pa5 = def.get("pa_35_plus") != null ? (long) def.get("pa_35_plus") : 0L;
        long shutouts = def.get("shutouts") != null ? (long) def.get("shutouts") : 0L;

        long interceptionPoints = interceptions * PointsRules.PA_1_to_6_allowed;
        long fumblePoints = fumbles * PointsRules.Each_fumble_recovery;
        long sackPoints = (long) (sacks * PointsRules.Each_sack);
        long defensiveTdPoints = defensiveTd * PointsRules.Each_Off_Fum_Ret_TD;
        long blockedFgPoints = blockedFieldGoals * PointsRules.Each_blocked_kick;
        long safetyPoints = safeties * PointsRules.Each_safety;
        long paPoints = pa * PointsRules.PA_1_to_6_allowed;
        long pa1Points = pa1 * PointsRules.PA_7_to_13_allowed;
        long pa2Points = pa2 * PointsRules.PA_14_to_20_allowed;
        long pa3Points = pa3 * PointsRules.PA_21_to_27_allowed;
        long pa4Points = pa4 * PointsRules.PA_28_to_34_allowed;
        long pa5Points = pa5 * PointsRules.PA_35_plus_allowed;
        long shutoutPoints = shutouts * PointsRules.Shutout;

        return paPoints +
                pa1Points +
                pa2Points +
                pa3Points +
                pa4Points +
                pa5Points +
                shutoutPoints +
                safetyPoints +
                interceptionPoints +
                fumblePoints +
                sackPoints +
                defensiveTdPoints +
                blockedFgPoints;
    }
}

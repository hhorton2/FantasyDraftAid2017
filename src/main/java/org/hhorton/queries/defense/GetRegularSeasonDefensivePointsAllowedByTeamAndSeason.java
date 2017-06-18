package org.hhorton.queries.defense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/18/17.
 */
@Repository
public class GetRegularSeasonDefensivePointsAllowedByTeamAndSeason {
        private JdbcTemplate jdbcTemplate;

        @Autowired
        public GetRegularSeasonDefensivePointsAllowedByTeamAndSeason(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public List<Map<String, Object>> execute(int season, String team) {
            return this.jdbcTemplate.queryForList("SELECT scores.points_allowed\n" +
                    "FROM (SELECT away_score AS points_allowed\n" +
                    "      FROM game\n" +
                    "      WHERE home_team = ?\n" +
                    "            AND season_year = ?\n" +
                    "            AND season_type = 'Regular') AS scores\n" +
                    "UNION ALL (SELECT home_score AS points_allowed\n" +
                    "       FROM game\n" +
                    "       WHERE away_team = ?\n" +
                    "             AND season_year = ?\n" +
                    "             AND season_type = 'Regular')", team, season, team, season);
        }
}

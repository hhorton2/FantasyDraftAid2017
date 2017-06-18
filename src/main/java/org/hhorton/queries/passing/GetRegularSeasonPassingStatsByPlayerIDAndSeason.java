package org.hhorton.queries.passing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Created by hunterhorton on 6/5/17.
 */
public class GetRegularSeasonPassingStatsByPlayerIDAndSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetRegularSeasonPassingStatsByPlayerIDAndSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Map<String, Object> execute(String playerID, int season) {
        return this.jdbcTemplate.queryForMap("SELECT sum(passing_yds) as passing_yards, sum(passing_cmp) as passing_completions,  sum(passing_att) AS passing_attempts, sum(passing_tds) as passing_touchdowns " +
                "FROM play_player " +
                "  LEFT JOIN game " +
                "    ON play_player.gsis_id = game.gsis_id " +
                "WHERE season_type = 'Regular' " +
                "      AND season_year = ? " +
                "      AND player_id = ?", season, playerID);
    }
}

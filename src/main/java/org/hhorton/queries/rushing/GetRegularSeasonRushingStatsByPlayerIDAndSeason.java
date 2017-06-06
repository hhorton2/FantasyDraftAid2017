package org.hhorton.queries.rushing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Created by hunterhorton on 6/5/17.
 */
public class GetRegularSeasonRushingStatsByPlayerIDAndSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetRegularSeasonRushingStatsByPlayerIDAndSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Map<String, Object> execute(String playerID, int season) {
        return this.jdbcTemplate.queryForMap("SELECT sum(rushing_yds) as rushing_yards, sum(rushing_att) as rushing_attemps, sum(rushing_tds) as rushing_touchdowns " +
                "FROM play_player " +
                "  LEFT JOIN game " +
                "    ON play_player.gsis_id = game.gsis_id " +
                "WHERE season_type = 'Regular' " +
                "      AND season_year = ? " +
                "      AND player_id = ?", season, playerID);
    }
}

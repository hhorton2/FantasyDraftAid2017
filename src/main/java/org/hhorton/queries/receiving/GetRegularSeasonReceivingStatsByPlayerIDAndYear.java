package org.hhorton.queries.receiving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Created by hunterhorton on 6/5/17.
 */
public class GetRegularSeasonReceivingStatsByPlayerIDAndYear {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetRegularSeasonReceivingStatsByPlayerIDAndYear(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Map<String, Object> execute(String playerID, int season) {
        return this.jdbcTemplate.queryForMap("SELECT sum(receiving_yds) AS receiving_yards, sum(receiving_tds) AS receiving_touchdowns, sum(receiving_rec) AS receptions, sum(receiving_tar) AS receiving_targets " +
                "FROM play_player" +
                "  LEFT JOIN game" +
                "    ON play_player.gsis_id = game.gsis_id " +
                "WHERE season_type = 'Regular'" +
                "      AND season_year = ?" +
                "      AND player_id = ?", season, playerID);
    }
}

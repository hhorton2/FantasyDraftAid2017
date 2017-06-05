package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
public class GetAllQuarterbacksWhoPlayedInSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllQuarterbacksWhoPlayedInSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> execute(int season) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT player.player_id, full_name " +
                "FROM play_player " +
                "LEFT JOIN player " +
                "ON play_player.player_id = player.player_id " +
                "LEFT JOIN game " +
                "ON play_player.gsis_id = game.gsis_id " +
                "WHERE player.position = 'QB' " +
                "AND season_type = 'Regular' " +
                "AND season_year = ?", season);
    }
}

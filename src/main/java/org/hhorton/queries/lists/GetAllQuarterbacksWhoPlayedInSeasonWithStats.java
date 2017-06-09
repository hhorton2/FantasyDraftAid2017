package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/8/17.
 */
public class GetAllQuarterbacksWhoPlayedInSeasonWithStats {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllQuarterbacksWhoPlayedInSeasonWithStats(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> execute(int season) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT player.player_id, full_name, player.position, sum(passing_yds) as passing_yards, sum(passing_cmp) as passing_completions,  sum(passing_att) AS passing_attempts, sum(passing_tds) as passing_touchdowns   " +
                "FROM play_player " +
                "LEFT JOIN player " +
                "ON play_player.player_id = player.player_id " +
                "LEFT JOIN game " +
                "ON play_player.gsis_id = game.gsis_id " +
                "WHERE player.position = 'QB' " +
                "AND season_type = 'Regular' " +
                "AND season_year = ? " +
                "GROUP BY player.player_id, player.full_name", season);
    }
}

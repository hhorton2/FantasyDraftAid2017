package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/5/17.
 */
@Repository
public class GetAllTideEndsWhoPlayedInSeason {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllTideEndsWhoPlayedInSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> execute(int season) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT " +
                "  player.player_id, " +
                "  player.full_name," +
                " player.position " +
                "FROM play_player " +
                "  LEFT JOIN player " +
                "    ON play_player.player_id = player.player_id " +
                "  LEFT JOIN game " +
                "    ON play_player.gsis_id = game.gsis_id " +
                "WHERE season_type = 'Regular' " +
                "      AND season_year = ? " +
                "      AND position = 'TE'", season);
    }
}

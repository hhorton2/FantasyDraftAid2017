package org.hhorton.queries.receiving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@Repository
public class GetRegularSeasonReceptionsByPlayerIDAndSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetRegularSeasonReceptionsByPlayerIDAndSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Map<String, Object> execute(String playerID, int season) {
        return this.jdbcTemplate.queryForMap("SELECT sum(play_player.receiving_rec) AS receptions " +
                "FROM play_player " +
                "LEFT JOIN game " +
                "ON play_player.gsis_id = game.gsis_id " +
                "WHERE player_id = ?" +
                "AND season_year = ?", playerID, season);
    }
}

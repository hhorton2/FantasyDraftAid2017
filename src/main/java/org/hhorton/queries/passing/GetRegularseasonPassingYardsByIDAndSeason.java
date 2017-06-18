package org.hhorton.queries.passing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@Repository
public class GetRegularseasonPassingYardsByIDAndSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetRegularseasonPassingYardsByIDAndSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Map<String, Object> execute(String playerID, int season) {
        return this.jdbcTemplate.queryForMap("SELECT sum(passing_yds) AS passing_yards " +
                "FROM play_player " +
                "LEFT JOIN game " +
                "ON play_player.gsis_id = game.gsis_id " +
                "WHERE season_type = 'Regular' " +
                "AND season_year = ? " +
                "AND player_id = ?", season, playerID);
    }
}

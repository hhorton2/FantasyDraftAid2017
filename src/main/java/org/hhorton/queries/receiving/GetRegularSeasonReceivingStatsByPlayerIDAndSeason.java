package org.hhorton.queries.receiving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Created by hunterhorton on 6/5/17.
 */
public class GetRegularSeasonReceivingStatsByPlayerIDAndSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetRegularSeasonReceivingStatsByPlayerIDAndSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Map<String, Object> execute(String playerID, int season) {
        return this.jdbcTemplate.queryForMap("SELECT " +
                "sum(rushing_att)                    AS rushing_attempts,\n" +
                "sum(receiving_yds)                  AS receiving_yards,\n" +
                "sum(receiving_tds)                  AS receiving_touchdowns,\n" +
                "sum(receiving_rec)                  AS receptions,\n" +
                "sum(kickret_tds) + sum(puntret_tds) AS return_touchdowns,\n" +
                "sum(fumbles_lost)                   AS fumbles," +
                "sum(rushing_yds)                    AS rushing_yards,\n" +
                "sum(rushing_tds)                    AS rushing_touchdowns " +
                "FROM play_player " +
                "  LEFT JOIN game " +
                "    ON play_player.gsis_id = game.gsis_id " +
                "WHERE season_type = 'Regular' " +
                "      AND season_year = ? " +
                "      AND player_id = ?", season, playerID);
    }
}

package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/17/17.
 */
public class GetAllRunningBacksWhoPlayedInSeasonWithStats {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllRunningBacksWhoPlayedInSeasonWithStats(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> execute(int season) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT\n" +
                "player.player_id AS player_id, " +
                "  player.player_id,\n" +
                "  player.full_name,\n" +
                "player.position," +
                "count(DISTINCT play_player.gsis_id) AS games_played, " +
                "  sum(rushing_yds)                    AS rushing_yards,\n" +
                "  sum(rushing_tds)                    AS rushing_touchdowns,\n" +
                "  sum(rushing_att)                    AS rushing_attempts,\n" +
                "  sum(receiving_yds)                  AS receiving_yards,\n" +
                "  sum(receiving_tds)                  AS receiving_touchdowns,\n" +
                "  sum(receiving_rec)                  AS receptions,\n" +
                "  sum(kickret_tds) + sum(puntret_tds) AS return_touchdowns,\n" +
                "  sum(fumbles_lost) AS fumbles\n" +
                "FROM play_player\n" +
                "  LEFT JOIN player\n" +
                "    ON play_player.player_id = player.player_id\n" +
                "  LEFT JOIN game\n" +
                "    ON play_player.gsis_id = game.gsis_id\n" +
                "WHERE season_type = 'Regular'\n" +
                "      AND season_year = ?\n" +
                "      AND position = 'RB'\n" +
                " AND player.drafted = FALSE " +
                "GROUP BY player.player_id, player.full_name, player.player_id", season);
    }
}

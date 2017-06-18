package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/17/17.
 */
public class GetAllKickersWhoPlayedInSeasonWithStats {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllKickersWhoPlayedInSeasonWithStats(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> execute(int season) {
        return this.jdbcTemplate.queryForList("SELECT\n" +
                "  player.player_id,\n" +
                "  full_name,\n" +
                "player.position, " +
                "count(DISTINCT play_player.gsis_id) AS games_played, " +
                " coalesce(sum(play_player.kicking_xpmade),0) AS extra_points,\n" +
                "  coalesce(sum(play_player.kicking_fgm), 0) + coalesce(fg1.fgm,0) + coalesce(fg2.fgm,0) + coalesce(fg3.fgm,0) + coalesce(fg4.fgm,0) AS fgm,\n" +
                "  coalesce(sum(play_player.kicking_fgm),0) AS fg_0_to_19,\n" +
                "  coalesce(fg1.fgm ,0)                     AS fg_20_to_29,\n" +
                "  coalesce(fg2.fgm ,0)                     AS fg_30_to_39,\n" +
                "  coalesce(fg3.fgm ,0)                     AS fg_40_to_49,\n" +
                "  coalesce(fg4.fgm,0)                     AS fg_50_plus\n" +
                "FROM play_player\n" +
                "  LEFT JOIN (SELECT\n" +
                "               sum(play_player.kicking_fgm) AS fgm,\n" +
                "               play_player.player_id\n" +
                "             FROM play_player\n" +
                "               LEFT JOIN player\n" +
                "                 ON play_player.player_id = player.player_id\n" +
                "               LEFT JOIN game\n" +
                "                 ON play_player.gsis_id = game.gsis_id\n" +
                "             WHERE kicking_fgm_yds >= 20 AND kicking_fgm_yds < 29\n" +
                "                   AND season_year = ?\n" +
                "                   AND season_type = 'Regular'\n" +
                "                   AND position = 'K'\n" +
                "             GROUP BY play_player.player_id) AS fg1\n" +
                "    ON fg1.player_id = play_player.player_id\n" +
                "  LEFT JOIN (SELECT\n" +
                "               sum(play_player.kicking_fgm) AS fgm,\n" +
                "               play_player.player_id\n" +
                "             FROM play_player\n" +
                "               LEFT JOIN player\n" +
                "                 ON play_player.player_id = player.player_id\n" +
                "               LEFT JOIN game\n" +
                "                 ON play_player.gsis_id = game.gsis_id\n" +
                "             WHERE kicking_fgm_yds >= 30 AND kicking_fgm_yds < 39\n" +
                "                   AND season_year = ?\n" +
                "                   AND season_type = 'Regular'\n" +
                "                   AND position = 'K'\n" +
                "             GROUP BY play_player.player_id) AS fg2\n" +
                "    ON fg2.player_id = play_player.player_id\n" +
                "  LEFT JOIN (SELECT\n" +
                "               sum(play_player.kicking_fgm) AS fgm,\n" +
                "               play_player.player_id\n" +
                "             FROM play_player\n" +
                "               LEFT JOIN player\n" +
                "                 ON play_player.player_id = player.player_id\n" +
                "               LEFT JOIN game\n" +
                "                 ON play_player.gsis_id = game.gsis_id\n" +
                "             WHERE kicking_fgm_yds >= 40 AND kicking_fgm_yds < 49\n" +
                "                   AND season_year = ?\n" +
                "                   AND season_type = 'Regular'\n" +
                "                   AND position = 'K'\n" +
                "             GROUP BY play_player.player_id) AS fg3\n" +
                "    ON fg3.player_id = play_player.player_id\n" +
                "  LEFT JOIN (SELECT\n" +
                "               sum(play_player.kicking_fgm) AS fgm,\n" +
                "               play_player.player_id\n" +
                "             FROM play_player\n" +
                "               LEFT JOIN player\n" +
                "                 ON play_player.player_id = player.player_id\n" +
                "               LEFT JOIN game\n" +
                "                 ON play_player.gsis_id = game.gsis_id\n" +
                "             WHERE kicking_fgm_yds > 50\n" +
                "                   AND season_year = ?\n" +
                "                   AND season_type = 'Regular'\n" +
                "                   AND position = 'K'\n" +
                "             GROUP BY play_player.player_id) AS fg4\n" +
                "    ON fg4.player_id = play_player.player_id\n" +
                "  LEFT\n" +
                "  JOIN player\n" +
                "    ON play_player.player_id = player.player_id\n" +
                "  LEFT JOIN game\n" +
                "    ON play_player.gsis_id = game.gsis_id\n" +
                "WHERE play_player.kicking_fgm_yds < 20\n" +
                "      AND season_year = ?\n" +
                "      AND season_type = 'Regular'\n" +
                "      AND position = 'K'\n" +
                "GROUP BY player.player_id, full_name, fg1.fgm, fg2.fgm, fg3.fgm, fg4.fgm", season, season, season, season, season);
    }
}

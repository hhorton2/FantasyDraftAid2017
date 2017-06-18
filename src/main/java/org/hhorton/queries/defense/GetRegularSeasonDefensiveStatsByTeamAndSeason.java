package org.hhorton.queries.defense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hunterhorton on 6/18/17.
 */
@Repository
public class GetRegularSeasonDefensiveStatsByTeamAndSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetRegularSeasonDefensiveStatsByTeamAndSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Map<String, Object> execute(String team, int season) {
        return this.jdbcTemplate.queryForMap("SELECT\n" +
                "  play_player.team,\n" +
                "  sum(play_player.defense_int)                                                                             AS interceptions,\n" +
                "  sum(play_player.defense_sk)                                                                              AS sacks,\n" +
                "  sum(\n" +
                "      play_player.defense_frec)                                                                            AS fumble_recoveries,\n" +
                "  sum(play_player.defense_frec_tds) + sum(play_player.defense_int_tds) + sum(\n" +
                "      play_player.defense_misc_tds)                                                                        AS defensive_touchdowns,\n" +
                "  sum(\n" +
                "      play_player.defense_fgblk)                                                                           AS blocked_fgs,\n" +
                "  sum(play_player.defense_safe)                                                                            AS safeties\n" +
                "FROM play_player\n" +
                "  LEFT JOIN player\n" +
                "    ON play_player.player_id = player.player_id\n" +
                "  LEFT JOIN game\n" +
                "    ON play_player.gsis_id = game.gsis_id\n" +
                "WHERE season_type = 'Regular'\n" +
                "      AND season_year = ?\n" +
                " AND play_player.team = ?" +
                "GROUP BY play_player.team", season, team);
    }

}

package org.hhorton.queries.draft;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@SuppressWarnings("SpellCheckingInspection")
@Repository
public class UndraftPlayer {
    private JdbcTemplate jdbcTemplate;
    public UndraftPlayer(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer execute(String playerId){
        return this.jdbcTemplate.update("UPDATE player SET drafted = FALSE WHERE player_id = ?", playerId.toUpperCase());
    }
}
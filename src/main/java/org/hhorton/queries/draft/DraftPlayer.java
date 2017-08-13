package org.hhorton.queries.draft;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DraftPlayer {
    private JdbcTemplate jdbcTemplate;
    public DraftPlayer(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer execute(String playerId){
        return this.jdbcTemplate.update("UPDATE player SET drafted = TRUE WHERE player_id = ?", playerId.toUpperCase());
    }
}

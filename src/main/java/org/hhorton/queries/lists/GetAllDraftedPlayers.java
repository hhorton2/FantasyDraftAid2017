package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GetAllDraftedPlayers {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllDraftedPlayers(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> execute() {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT\n" +
                "  player.player_id,\n" +
                "  player.full_name\n" +
                "FROM player\n" +
                "WHERE  player.drafted = TRUE");
    }
}

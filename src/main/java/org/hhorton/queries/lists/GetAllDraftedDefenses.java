package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GetAllDraftedDefenses {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllDraftedDefenses(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> execute() {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT\n" +
                "team.team_id AS player_id, " +
                "  team.name as full_name\n" +
                "FROM team\n" +
                "WHERE  team.drafted = TRUE");
    }
}

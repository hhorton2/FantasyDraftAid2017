package org.hhorton.queries.draft;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DraftDefense {
    private JdbcTemplate jdbcTemplate;
    public DraftDefense(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public Integer execute(String teamId){
        return this.jdbcTemplate.update("UPDATE team SET drafted = TRUE WHERE team_id = ?", teamId.toUpperCase());
    }
}

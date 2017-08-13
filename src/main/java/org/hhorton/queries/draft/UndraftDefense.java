package org.hhorton.queries.draft;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@SuppressWarnings("SpellCheckingInspection")
@Repository
public class UndraftDefense {
    private JdbcTemplate jdbcTemplate;
    public UndraftDefense(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public Integer execute(String teamId){
        return this.jdbcTemplate.update("UPDATE team SET drafted = FALSE WHERE team_id = ?", teamId.toUpperCase());
    }
}

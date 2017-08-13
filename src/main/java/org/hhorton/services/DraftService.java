package org.hhorton.services;

import org.hhorton.queries.draft.DraftDefense;
import org.hhorton.queries.draft.DraftPlayer;
import org.hhorton.queries.draft.UndraftDefense;
import org.hhorton.queries.draft.UndraftPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DraftService {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public DraftService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer draftPlayer(String playerId){
        if(playerId.length() <= 3){
            return new DraftDefense(this.jdbcTemplate).execute(playerId);
        }else{
            return new DraftPlayer(this.jdbcTemplate).execute(playerId);
        }
    }

    public Integer undraftPlayer(String playerId){
        if(playerId.length() <= 3){
            return new UndraftDefense(this.jdbcTemplate).execute(playerId);
        }else{
            return new UndraftPlayer(this.jdbcTemplate).execute(playerId);
        }
    }
}

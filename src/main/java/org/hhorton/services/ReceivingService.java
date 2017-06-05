package org.hhorton.services;

import org.hhorton.queries.receiving.GetRegularSeasonReceivingTargetsByPlayerIDAndSeason;
import org.hhorton.queries.receiving.GetRegularSeasonReceivingTouchdownsByPlayerIDAndSeason;
import org.hhorton.queries.receiving.GetRegularSeasonReceivingYardsByPlayerIDAndSeason;
import org.hhorton.queries.receiving.GetRegularSeasonReceptionsByPlayerIDAndSeason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@Service
public class ReceivingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReceivingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getReceivingStatsByPlayerIDAndSeason(String playerID, int season) {
        Map<String, Object> result = new GetRegularSeasonReceptionsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season);
        result.putAll(new GetRegularSeasonReceivingYardsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season));
        result.putAll(new GetRegularSeasonReceivingTargetsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season));
        result.putAll(new GetRegularSeasonReceivingTouchdownsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season));
        return result;
    }
}

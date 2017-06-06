package org.hhorton.services;

import org.hhorton.queries.receiving.GetRegularSeasonReceivingStatsByPlayerIDAndYear;
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
        return new GetRegularSeasonReceivingStatsByPlayerIDAndYear(this.jdbcTemplate).execute(playerID, season);
    }
}

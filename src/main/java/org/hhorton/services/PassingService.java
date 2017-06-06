package org.hhorton.services;

import org.hhorton.queries.passing.GetRegularSeasonPassingStatsByPlayerIDAndSeason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@Service
public class PassingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PassingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getPassingStatsByPlayerIDAndSeason(String playerID, int season) {
        return new GetRegularSeasonPassingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season);
    }
}

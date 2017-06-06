package org.hhorton.services;

import org.hhorton.queries.rushing.GetRegularSeasonRushingStatsByPlayerIDAndSeason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hunterhorton on 6/5/17.
 */
@Service
public class RushingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RushingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getRushingStatsByPlayerIDAndSeason(String playerID, int season) {
        return new GetRegularSeasonRushingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute(playerID, season);
    }
}

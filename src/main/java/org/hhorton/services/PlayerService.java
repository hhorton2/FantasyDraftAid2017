package org.hhorton.services;

import org.hhorton.queries.lists.*;
import org.hhorton.queries.receiving.GetRegularSeasonReceivingStatsByPlayerIDAndSeason;
import org.hhorton.utility.SortUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@Service
public class PlayerService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PlayerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getAllQuarterBacksFromSeason(int season) {
        return new GetAllQuarterbacksWhoPlayedInSeason(this.jdbcTemplate).execute(season);
    }

    public List<Map<String, Object>> getAllQuarterBacksFromSeasonWithStats(int season) {
        return new PassingService(this.jdbcTemplate)
                .getPassingStatsForPlayersBySeason(season, 
                        new GetAllQuarterbacksWhoPlayedInSeasonWithStats(this.jdbcTemplate).execute(season));
    }

    public List<Map<String, Object>> getAllRunningBacksFromSeason(int season) {
        return new GetAllRunningBacksWhoPlayedInSeason(this.jdbcTemplate).execute(season);

    }

    public List<Map<String, Object>> getAllRunningBacksFromSeasonWithStats(int season) {
        return new RushingService(this.jdbcTemplate)
                .getRushingStatsForPlayersBySeason(season,
                        new GetAllRunningBacksWhoPlayedInSeasonWithStats(this.jdbcTemplate).execute(season));
    }

    public List<Map<String, Object>> getAllWideReceiversFromSeason(int season) {
        List<Map<String, Object>> result = new GetAllWideReceiversWhoPlayedInSeason(this.jdbcTemplate).execute(season);
        for (Map<String, Object> wr : result) {
            wr.putAll(new GetRegularSeasonReceivingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) wr.get("player_id"), season));
        }
        SortUtility.sortList(result, "receiving_yards");
        return result;
    }

    public List<Map<String, Object>> getAllWideReceiversFromSeasonWithStats(int season) {
        return new ReceivingService(this.jdbcTemplate)
                .getReceivingStatsForPlayersBySeason(season,
                        new GetAllWideReceiversWhoPlayedInSeasonWithStats(this.jdbcTemplate).execute(season));
    }

    public List<Map<String, Object>> getAllTideEndsFromSeason(int season) {
        List<Map<String, Object>> result = new GetAllTightEndsWhoPlayedInSeason(this.jdbcTemplate).execute(season);
        for (Map<String, Object> te : result) {
            te.putAll(new GetRegularSeasonReceivingStatsByPlayerIDAndSeason(this.jdbcTemplate).execute((String) te.get("player_id"), season));
        }
        SortUtility.sortList(result, "receiving_yards");
        return result;
    }

}

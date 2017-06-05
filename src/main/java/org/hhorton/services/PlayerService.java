package org.hhorton.services;

import org.hhorton.queries.lists.GetAllQuarterbacksWhoPlayedInSeason;
import org.hhorton.queries.passing.GetRegularseasonPassingYardsByIDAndSeason;
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

    public List<Map<String, Object>> getAllQuarterBacksFromSeason(int season){
        List<Map<String, Object>> result =  new GetAllQuarterbacksWhoPlayedInSeason(this.jdbcTemplate).execute(season);
        for(Map<String, Object> qb: result){
            qb.putAll(new GetRegularseasonPassingYardsByIDAndSeason(this.jdbcTemplate).execute((String) qb.get("player_id"), season));
        }
        result.sort((Map<String,Object> map, Map<String,Object> map2) -> {
            long m1Passing = (long) map.get("passing_yards");
            long m2Passing = (long) map2.get("passing_yards");

            if(m1Passing < m2Passing){
                return 1;
            }else if(m1Passing > m2Passing){
                return -1;
            }else{
                return 0;
            }
        });
        return result;
    }
}

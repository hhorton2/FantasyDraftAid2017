package org.hhorton.queries.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/18/17.
 */
@Repository
public class GetAllDefensesWhoPlayedInSeason {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GetAllDefensesWhoPlayedInSeason(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> execute(int season) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT \n" +
                "  game.home_team as team\n" +
                "FROM game " +
                "WHERE season_type = 'Regular'\n" +
                "      AND season_year = ?\n", season);
    }
}

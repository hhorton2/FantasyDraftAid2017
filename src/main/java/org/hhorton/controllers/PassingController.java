package org.hhorton.controllers;

import org.hhorton.services.PassingService;
import org.hhorton.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/4/17.
 */
@RestController
@RequestMapping("/passing")
public class PassingController {
    private PassingService passingService;
    private PlayerService playerService;

    @Autowired
    PassingController(PassingService passingService, PlayerService playerService) {
        this.passingService = passingService;
        this.playerService = playerService;
    }


    @RequestMapping("/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllQbs(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllQuarterBacksFromSeason(season));
    }

    @RequestMapping("/{season}/stats")
    public ResponseEntity<List<Map<String, Object>>> getAllQbsWithStats(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllQuarterBacksFromSeasonWithStats(season));
    }

    @RequestMapping("/{season}/{playerID}")
    public ResponseEntity<Map<String, Object>> getPlayerPassingStats(@PathVariable int season, @PathVariable String playerID) {
        return ResponseEntity.ok(this.passingService.getPassingStatsByPlayerIDAndSeason(playerID, season));
    }
}

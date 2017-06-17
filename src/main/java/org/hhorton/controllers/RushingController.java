package org.hhorton.controllers;

import org.hhorton.services.PlayerService;
import org.hhorton.services.RushingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/5/17.
 */
@RestController
@RequestMapping("/rushing")
public class RushingController {
    private RushingService rushingService;
    private PlayerService playerService;

    @Autowired
    public RushingController(RushingService rushingService, PlayerService playerService) {
        this.rushingService = rushingService;
        this.playerService = playerService;
    }

    @RequestMapping("/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllRbs(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllRunningBacksFromSeason(season));
    }

    @RequestMapping("/{season}/stats")
    public ResponseEntity<List<Map<String, Object>>> getAllRbsWithStats(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllRunningBacksFromSeasonWithStats(season));
    }

    @RequestMapping("/{season}/{playerID}")
    public ResponseEntity<Map<String, Object>> getPlayerRushingStats(@PathVariable int season, @PathVariable String playerID) {
        return ResponseEntity.ok(this.rushingService.getRushingStatsByPlayerIDAndSeason(playerID, season));
    }
}

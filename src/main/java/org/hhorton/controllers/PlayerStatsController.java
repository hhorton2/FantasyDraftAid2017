package org.hhorton.controllers;

import org.hhorton.services.PassingService;
import org.hhorton.services.PlayerService;
import org.hhorton.services.ReceivingService;
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
public class PlayerStatsController {
    private ReceivingService receivingService;
    private PassingService passingService;
    private PlayerService playerService;

    @Autowired
    PlayerStatsController(ReceivingService receivingService, PassingService passingService, PlayerService playerService) {
        this.receivingService = receivingService;
        this.passingService = passingService;
        this.playerService = playerService;
    }

    @RequestMapping("/receiving/{season}/{playerID}")
    public ResponseEntity<Map<String, Object>> getPlayerReceivingStats(@PathVariable int season, @PathVariable String playerID) {
        return ResponseEntity.ok(this.receivingService.getReceivingStatsByPlayerIDAndSeason(playerID, season));
    }

    @RequestMapping("/passing/{season}/{playerID}")
    public ResponseEntity<Map<String, Object>> getPlayerPassingStats(@PathVariable int season, @PathVariable String playerID) {
        return ResponseEntity.ok(this.passingService.getPassingStatsByPlayerIDAndSeason(playerID, season));
    }

    @RequestMapping("qb/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllQbs(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllQuarterBacksFromSeason(season));
    }
}

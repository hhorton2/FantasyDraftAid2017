package org.hhorton.controllers;

import org.hhorton.services.DefenseService;
import org.hhorton.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/18/17.
 */
@RestController
@RequestMapping("/defense")
public class DefenseController {
    private DefenseService defenseService;
    private PlayerService playerService;

    @Autowired
    public DefenseController(DefenseService defenseService, PlayerService playerService) {
        this.defenseService = defenseService;
        this.playerService = playerService;
    }

    @RequestMapping("/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllDefenses(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllDefensesFromSeason(season));
    }

    @RequestMapping("/{season}/stats")
    public ResponseEntity<List<Map<String, Object>>> getAllDefensesWithStats(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllDefensesFromSeasonWithStats(season));
    }

    @RequestMapping("/{season}/{team}")
    public ResponseEntity<Map<String, Object>> getTeamDefensiveStats(@PathVariable int season, @PathVariable String team) {
        return ResponseEntity.ok(this.defenseService.getDefensiveStatsByTeamAndSeason(team, season));
    }

}

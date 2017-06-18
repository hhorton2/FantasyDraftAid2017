package org.hhorton.controllers;

import org.hhorton.services.KickingService;
import org.hhorton.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/17/17.
 */
@RestController
@RequestMapping("/kicking")
public class KickingController {
    private KickingService kickingService;
    private PlayerService playerService;

    @Autowired
    public KickingController(KickingService kickingService, PlayerService playerService) {
        this.kickingService = kickingService;
        this.playerService = playerService;
    }

    @RequestMapping("/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllKs(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllKsFromSeason(season));
    }

    @RequestMapping("/{season}/stats")
    public ResponseEntity<List<Map<String, Object>>> getAllKsWithStats(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllKsFromSeasonWithStats(season));
    }

    @RequestMapping("/{season}/{playerID}")
    public ResponseEntity<Map<String, Object>> getPlayerKickingStats(@PathVariable int season, @PathVariable String playerID) {
        throw new NotImplementedException();
    }
}

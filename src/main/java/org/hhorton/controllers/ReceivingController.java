package org.hhorton.controllers;

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
 * Created by hunterhorton on 6/5/17.
 */
@RestController
@RequestMapping("/receiving")
public class ReceivingController {
    private ReceivingService receivingService;
    private PlayerService playerService;

    @Autowired
    ReceivingController(ReceivingService receivingService, PlayerService playerService) {
        this.receivingService = receivingService;
        this.playerService = playerService;
    }

    @RequestMapping("/wr/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllWrs(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllWideReceiversFromSeason(season));
    }

    @RequestMapping("/te/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllTes(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllTideEndsFromSeason(season));
    }

    @RequestMapping("/{season}/{playerID}")
    public ResponseEntity<Map<String, Object>> getPlayerReceivingStats(@PathVariable int season, @PathVariable String playerID) {
        return ResponseEntity.ok(this.receivingService.getReceivingStatsByPlayerIDAndSeason(playerID, season));
    }
}

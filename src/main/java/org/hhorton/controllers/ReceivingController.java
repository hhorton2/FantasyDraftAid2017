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

    @RequestMapping("/wr/{season}/stats")
    public ResponseEntity<List<Map<String, Object>>> getAllWrsWithStats(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllWideReceiversFromSeasonWithStats(season));
    }

    @RequestMapping("/te/{season}")
    public ResponseEntity<List<Map<String, Object>>> getAllTes(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllTightEndsFromSeason(season));
    }

    @RequestMapping("/te/{season}/stats")
    public ResponseEntity<List<Map<String, Object>>> getAllTesWithStats(@PathVariable int season) {
        return ResponseEntity.ok(this.playerService.getAllTightEndsFromSeasonWithStats(season));
    }
}

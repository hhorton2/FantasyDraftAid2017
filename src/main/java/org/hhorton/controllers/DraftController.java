package org.hhorton.controllers;

import org.hhorton.services.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/draft")
public class DraftController {
    private DraftService draftService;

    @Autowired
    public DraftController(DraftService draftService) {
        this.draftService = draftService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Map<String,Object>>> getAllDrafted() {
        return ResponseEntity.ok(this.draftService.getDraftedPlayers());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Integer> draftPlayer(@RequestBody String playerId) {
        return ResponseEntity.ok(this.draftService.draftPlayer(playerId));
    }

}
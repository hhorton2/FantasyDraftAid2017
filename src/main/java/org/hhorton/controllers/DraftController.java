package org.hhorton.controllers;

import org.hhorton.services.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DraftController {
    private DraftService draftService;

    @Autowired
    public DraftController(DraftService draftService) {
        this.draftService = draftService;
    }

    @RequestMapping(value = "/draft", method = RequestMethod.POST)
    public ResponseEntity<Integer> getAllDefenses(@RequestBody String playerId) {
        return ResponseEntity.ok(this.draftService.draftPlayer(playerId));
    }

}
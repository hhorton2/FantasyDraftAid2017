package org.hhorton.controllers;

import org.hhorton.services.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UndraftController {
    private DraftService draftService;

    @Autowired
    public UndraftController(DraftService draftService) {
        this.draftService = draftService;
    }

    @RequestMapping(value = "/undraft", method = RequestMethod.POST)
    public ResponseEntity<Integer> getAllDefenses(@RequestBody String playerId) {
        return ResponseEntity.ok(this.draftService.undraftPlayer(playerId));
    }

}
package org.hhorton.services;

import org.hhorton.queries.draft.DraftDefense;
import org.hhorton.queries.draft.DraftPlayer;
import org.hhorton.queries.draft.UndraftDefense;
import org.hhorton.queries.draft.UndraftPlayer;
import org.hhorton.queries.lists.GetAllDraftedDefenses;
import org.hhorton.queries.lists.GetAllDraftedPlayers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DraftService {
    private DraftDefense draftDefense;
    private DraftPlayer draftPlayer;
    private UndraftDefense undraftDefense;
    private UndraftPlayer undraftPlayer;
    private GetAllDraftedDefenses getAllDraftedDefenses;
    private GetAllDraftedPlayers getAllDraftedPlayers;

    @Autowired
    public DraftService(DraftPlayer draftPlayer, DraftDefense draftDefense, UndraftPlayer undraftPlayer,
                        UndraftDefense undraftDefense, GetAllDraftedPlayers getAllDraftedPlayers,
                        GetAllDraftedDefenses getAllDraftedDefenses) {
        this.draftDefense = draftDefense;
        this.draftPlayer = draftPlayer;
        this.undraftDefense = undraftDefense;
        this.undraftPlayer = undraftPlayer;
        this.getAllDraftedDefenses = getAllDraftedDefenses;
        this.getAllDraftedPlayers = getAllDraftedPlayers;
    }

    public List<Map<String, Object>> getDraftedPlayers() {
        List<Map<String, Object>> players = this.getAllDraftedPlayers.execute();
        players.addAll(this.getAllDraftedDefenses.execute());
        return players;
    }

    public Integer draftPlayer(String playerId) {
        if (playerId.length() <= 3) {
            return this.draftDefense.execute(playerId);
        } else {
            return this.draftPlayer.execute(playerId);
        }
    }

    public Integer undraftPlayer(String playerId) {
        if (playerId.length() <= 3) {
            return this.undraftDefense.execute(playerId);
        } else {
            return this.undraftPlayer.execute(playerId);
        }
    }
}

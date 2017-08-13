package org.hhorton.utility;

import java.util.List;
import java.util.Map;

/**
 * Created by hunterhorton on 6/18/17.
 */
public class RarityUltility {
    public static void getPositionRarity(List<Map<String, Object>> players) {
        for (Map<String, Object> player : players) {
            long playerPPG = (long) player.get("ppg");
            long playerTwentyFivePercent = Math.round(playerPPG * .8);
            int count = 0;
            for (Map<String, Object> playerCompare : players) {
                if(playerCompare.get("full_name").equals(player.get("full_name"))){
                    continue;
                }
                long comparePPG = (long) playerCompare.get("ppg");
                if(comparePPG >= playerTwentyFivePercent){
                    count++;
                }
            }
            player.put("comparable_players", count);
        }
    }
}

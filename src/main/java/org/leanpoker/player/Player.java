package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.leanpoker.player.models.GameStatePOJO;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonNode request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        GameStatePOJO gameStatePOJO = objectMapper.treeToValue(request, GameStatePOJO.class);

        // Now you can work with the GameStatePOJO object
        System.out.println("Tournament ID: " + gameStatePOJO.getTournament_id());
        System.out.println("Game ID: " + gameStatePOJO.getGame_id());

        return 0;
    }

    public static void showdown(JsonNode game) {
    }
}

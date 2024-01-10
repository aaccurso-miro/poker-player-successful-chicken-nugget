package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.leanpoker.player.models.GameStatePOJO;
import org.leanpoker.player.service.PokerService;

public class Player {

    static PokerService pokerService = new PokerService();

    static ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    static final String VERSION = "Default Java folding player";


    public static int betRequest(JsonNode request) throws JsonProcessingException {

        System.out.println(request.toString());
        GameStatePOJO gameStatePOJO = objectMapper.treeToValue(request, GameStatePOJO.class);

        System.out.println("Tournament ID: " + gameStatePOJO.getTournament_id());
        System.out.println("Game ID: " + gameStatePOJO.getGame_id());

        return pokerService.prepareBet(gameStatePOJO);
    }

    public static void showdown(JsonNode game) throws JsonProcessingException {
    }
}

package org.leanpoker.player.service;

import org.leanpoker.player.models.CardPOJO;
import org.leanpoker.player.models.GameStatePOJO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PokerService {

    public static final String SUCCESSFUL_CHICKEN_NUGGET = "Successful Chicken Nugget";

    public Integer prepareBet(GameStatePOJO gameStatePOJO) {
        var ourPlayer = gameStatePOJO.getPlayers().stream().filter(player -> player.getName().equals(SUCCESSFUL_CHICKEN_NUGGET)).findFirst();

        List<CardPOJO> cards = ourPlayer.get().getHole_cards();
        CardPOJO firstCard = cards.get(0);
        CardPOJO secondCard = cards.get(1);
        var communityCards = getCommunityCards(gameStatePOJO);
        var allCards = communityCards.addAll(List.of(firstCard, secondCard));

        if (firstCard.getRank().equals(secondCard.getRank())) {
            if (gameStatePOJO.getCurrent_buy_in() == 0) {
                System.out.println("No buy in, betting 4x small blind:["+gameStatePOJO.getSmall_blind() * 4+"]");
                return gameStatePOJO.getSmall_blind() * 4;
            } else {
                System.out.println("Matching current buy_in:["+ gameStatePOJO.getCurrent_buy_in()+"]");
                return gameStatePOJO.getCurrent_buy_in();
            }
        }



        if (firstCard.getRank().ordinal() >= 9 && secondCard.getRank().ordinal() >= 9) {
            return gameStatePOJO.getCurrent_buy_in() == 0 ? gameStatePOJO.getSmall_blind() * 4 : gameStatePOJO.getCurrent_buy_in();
        }


        if (firstCard.getRank().ordinal() >= 9 || secondCard.getRank().ordinal() >= 9) {
            return gameStatePOJO.getCurrent_buy_in() == 0 ? gameStatePOJO.getSmall_blind(): gameStatePOJO.getCurrent_buy_in();
        }

        return 0;
    }

    private static List<CardPOJO> getCommunityCards(GameStatePOJO gameStatePOJO) {
        return Objects.isNull(gameStatePOJO.getCommunity_cards()) ? new ArrayList<>() : gameStatePOJO.getCommunity_cards();
    }


}

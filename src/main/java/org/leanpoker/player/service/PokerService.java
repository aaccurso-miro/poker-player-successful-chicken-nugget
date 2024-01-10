package org.leanpoker.player.service;

import org.leanpoker.player.models.CardPOJO;
import org.leanpoker.player.models.CardRank;
import org.leanpoker.player.models.CardSuit;
import org.leanpoker.player.models.GameStatePOJO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PokerService {

    public static final String SUCCESSFUL_CHICKEN_NUGGET = "Successful Chicken Nugget";

    public Integer prepareBet(GameStatePOJO gameStatePOJO) {
        var ourPlayer = gameStatePOJO.getPlayers().stream().filter(player -> player.getName().equals(SUCCESSFUL_CHICKEN_NUGGET)).findFirst();

        List<CardPOJO> cards = ourPlayer.get().getHole_cards();
        CardPOJO firstCard = cards.get(0);
        CardPOJO secondCard = cards.get(1);
        var communityCards = getCommunityCards(gameStatePOJO);
        communityCards.addAll(List.of(firstCard, secondCard));

        Map<CardRank, Long> collectByRank = communityCards.stream().collect(Collectors.groupingBy(CardPOJO::getRank, Collectors.counting()));
        Map<CardSuit, Long> collectBySuit = communityCards.stream().collect(Collectors.groupingBy(CardPOJO::getSuit, Collectors.counting()));

        // triple or more
        if (collectByRank.containsValue(3) || collectByRank.containsValue(4)){
            return ourPlayer.get().getStack();
        }
        var doublesOrTriplesCount = collectByRank.entrySet()
                .stream()
                .filter(entry -> entry.getValue() >= 2)
                .toList()
                .size();
        if (doublesOrTriplesCount >= 2){
            return ourPlayer.get().getStack();
        }

        //flush
        if (collectBySuit.containsValue(5) || collectBySuit.containsValue(6) || collectBySuit.containsValue(7)) {
            return ourPlayer.get().getStack();
        }


        if (firstCard.getRank().equals(secondCard.getRank())) {

            if (firstCard.getRank().ordinal() >= 9 && secondCard.getRank().ordinal() >= 9) {
                return ourPlayer.get().getStack();
            }


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
            if (gameStatePOJO.getMinimum_raise() >= ourPlayer.get().getStack() / 2) {
                return 0;
            }
            return gameStatePOJO.getCurrent_buy_in() == 0 ? gameStatePOJO.getSmall_blind(): 0;
        }

        return 0;
    }

    private static List<CardPOJO> getCommunityCards(GameStatePOJO gameStatePOJO) {
        return Objects.isNull(gameStatePOJO.getCommunity_cards()) ? new ArrayList<>() : gameStatePOJO.getCommunity_cards();
    }


}

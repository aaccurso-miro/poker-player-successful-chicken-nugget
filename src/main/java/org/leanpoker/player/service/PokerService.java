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
        var betToMatch = gameStatePOJO.getCurrent_buy_in() - ourPlayer.get().getBet();
        var maxRaiseForLowHand = gameStatePOJO.getSmall_blind() * 10;


        List<CardPOJO> cards = ourPlayer.get().getHole_cards();
        CardPOJO firstCard = cards.get(0);
        CardPOJO secondCard = cards.get(1);
        var ourCards = List.of(firstCard, secondCard);
        var communityCards = getCommunityCards(gameStatePOJO);
        communityCards.addAll(List.of(firstCard, secondCard));

        Map<CardRank, Long> collectByRank = communityCards.stream().collect(Collectors.groupingBy(CardPOJO::getRank, Collectors.counting()));
        Map<CardSuit, Long> collectBySuit = communityCards.stream().collect(Collectors.groupingBy(CardPOJO::getSuit, Collectors.counting()));

        boolean doublesOrTriplesWithOurCards = checkDoublesOrTripletsIncludingOurCards(ourCards, communityCards);
        // triple or more
        boolean triplesOrMore = collectByRank.containsValue(3) || collectByRank.containsValue(4);
        if (doublesOrTriplesWithOurCards && triplesOrMore){
            return ourPlayer.get().getStack();
        }
        var doublesOrTriplesCount = collectByRank.entrySet()
                .stream()
                .filter(entry -> entry.getValue() >= 2)
                .toList()
                .size();
        if (doublesOrTriplesWithOurCards && doublesOrTriplesCount >= 2){
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


        // Two high cards
        if (firstCard.getRank().ordinal() >= 9 && secondCard.getRank().ordinal() >= 9) {
            return betToMatch == 0 ? gameStatePOJO.getSmall_blind() * 4 : gameStatePOJO.getCurrent_buy_in();
        }

        // One low card and one high card
        if (firstCard.getRank().ordinal() >= 9 || secondCard.getRank().ordinal() >= 9) {
            // We fold if bet to match is higher than we want
            var bet = betToMatch > maxRaiseForLowHand ? 0 : betToMatch;
            // We raise if no one else raised
            return betToMatch == 0 ? gameStatePOJO.getSmall_blind() * 2 : bet;
        }

        return 0;
    }

    private static boolean checkDoublesOrTripletsIncludingOurCards(List<CardPOJO> ourCards, List<CardPOJO> communityCards) {
        var communityCardsRanks = communityCards
                .stream()
                .map(CardPOJO::getRank)
                .collect(Collectors.toList());
        var ourCardsRanks = ourCards.stream().map(CardPOJO::getRank).collect(Collectors.toList());
        var size = ourCardsRanks.stream()
                .filter(card -> communityCardsRanks.contains(card))
                .collect(Collectors.toList())
                .size();
        System.out.println(size);
        return size>=2;
    }

    private static List<CardPOJO> getCommunityCards(GameStatePOJO gameStatePOJO) {
        return Objects.isNull(gameStatePOJO.getCommunity_cards()) ? new ArrayList<>() : gameStatePOJO.getCommunity_cards();
    }
}

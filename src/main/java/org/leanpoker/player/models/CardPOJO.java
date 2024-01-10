package org.leanpoker.player.models;

public class CardPOJO {
    // Rank of the card. Possible values are
    // numbers 2-10 and J,Q,K,A
    private CardRank rank;

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    // Suit of the card. Possible values are:
    // clubs,spades,hearts,diamonds
    private CardSuit suit;

    // Getter and Setter methods
}

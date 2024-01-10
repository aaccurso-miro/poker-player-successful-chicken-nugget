package org.leanpoker.player.models;

public class CardPOJO {
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    private String rank;
    private String suit;

    // Getter and Setter methods
}

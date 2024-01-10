package org.leanpoker.player.models;

public enum CardSuit {
    CLUBS("clubs"),
    SPADES("spades"),
    HEARTS("hearts"),
    DIAMONDS("diamonds");

    private final String value;

    CardSuit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
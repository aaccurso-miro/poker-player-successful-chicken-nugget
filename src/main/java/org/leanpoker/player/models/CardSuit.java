package org.leanpoker.player.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CardSuit {
    CLUBS("clubs"),
    SPADES("spades"),
    HEARTS("hearts"),
    DIAMONDS("diamonds");

    private final String value;

    CardSuit(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
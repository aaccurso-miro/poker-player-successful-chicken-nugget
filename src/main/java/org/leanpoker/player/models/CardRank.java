package org.leanpoker.player.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CardRank {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    private final String value;

    CardRank(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
}

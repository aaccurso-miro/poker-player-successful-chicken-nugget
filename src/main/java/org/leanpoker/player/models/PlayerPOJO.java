package org.leanpoker.player.models;

import java.util.List;

public class PlayerPOJO {
    private int id;
    private String name;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public List<CardPOJO> getHole_cardPOJOS() {
        return hole_cardPOJOS;
    }

    public void setHole_cardPOJOS(List<CardPOJO> hole_cardPOJOS) {
        this.hole_cardPOJOS = hole_cardPOJOS;
    }

    private String version;
    private int stack;
    private int bet;
    private List<CardPOJO> hole_cardPOJOS;

    // Getter and Setter methods
}

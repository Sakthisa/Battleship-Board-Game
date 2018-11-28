package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

import java.lang.reflect.Field;

public class AttackGameAction {

    @JsonProperty private Game game;
    @JsonProperty private int x;
    @JsonProperty private char y;
    @JsonProperty private boolean radar;
    @JsonProperty private String fleet;


    public Game getGame() {
        return game;
    }

    public int getActionRow() {
        return x;
    }

    public char getActionColumn() {
        return y;
    }

    public String getFleet(){
        return fleet;
    }

    public boolean isRadar() { return radar; }

    public boolean isFleet(){
        return(!fleet.isEmpty());
    }
}

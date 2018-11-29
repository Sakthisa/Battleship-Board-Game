package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.*;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import java.awt.*;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        Game game = g.getGame();
        boolean result;
        Ship ship;
        if (g.getShipType().equals("MINESWEEPER")) {
            ship = new Minesweeper();
        } else if (g.getShipType().equals("DESTROYER")) {
            ship = new Destroyer();
        } else if (g.getShipType().equals("BATTLESHIP")){
            ship = new Battleship();
        }
        else{
            ship = new Submarine();
        }

        if(g.isSubmerged() == false){
            result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        }
        else{
            result = game.placeSubShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical(), g.isSubmerged());
        }
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result;

        if(g.isRadar()){
            result = game.radarAttack(g.getActionRow(), g.getActionColumn());
        }
        else{
            result = game.attack(g.getActionRow(), g.getActionColumn());
        }

        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest().html();
        }
    }

    public Result move(Context context, AttackGameAction g){
        Game game = g.getGame();
        boolean result = true;

        if(g.isFleet()){
            result = game.moveShips(g.getFleet());
            game.opponentAttack();
        }
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest().html();
        }
    }
}

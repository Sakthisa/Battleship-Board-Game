package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        int count = 0;
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }

    // This will be a random character from A-J to indicate a column
    private char randCol() {
        Random rand = new Random();
        char randomColumn = (char)(rand.nextInt(10) + 'A');
        return randomColumn;
    }


    // This will be a random integer from 1-10 to indicate a row
    private int randRow() {
        Random rand = new Random();
        int randomRow = rand.nextInt(9);
        return randomRow;
    }

    // This will be either a random true or false boolean value (true for vertical, false for horizontal)
    private boolean randVertical() {
        Random rand = new Random();
        return rand.nextBoolean();
    }
}

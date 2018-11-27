package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();
    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);

        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randBool());
        } while (!opponentPlacedSuccessfully);



        return true;
    }


    public boolean placeSubShip(Ship ship, int x, char y, boolean isVertical, boolean isSubmerged) {
        boolean successful = playersBoard.placeSubShip(ship, x, y, isVertical, isSubmerged);

        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            if(randBool()){
                opponentPlacedSuccessfully = opponentsBoard.placeSubShip(ship, randRow(), randCol(), randBool(), true);
            }
            else{
                opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randBool());
            }
        } while (!opponentPlacedSuccessfully);

        return true;
    }


    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack;
        playerAttack = opponentsBoard.attack(x, y);

        if (!isValid(playerAttack)) {
            return false;
        }

        // opponent's board is attacked until a valid result is reached
        opponentAttack();

        return true;
    }

    public boolean radarAttack(int x, char  y) {
        Result playerAttack;
        playerAttack = opponentsBoard.radarAttack(x, y);

        if (!isValid(playerAttack)) {
            return false;
        }

        // opponent's board is attacked until a valid result is reached
        opponentAttack();

        return true;
    }

    public boolean moveShips(String direction){
        if(direction.equals("west")){
            moveShipsWest();
            return true;
        }
        return false;
    }

    public void moveShipsWest(){
        playersBoard.moveShipsWest();
    }

    private void opponentAttack() {
        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice

            // let it try until it gets it right
            if(randBool()) {
                opponentAttackResult = playersBoard.attack(randRow(), randCol());
            }
            else{
                opponentAttackResult = playersBoard.radarAttack(randRow(), randCol());
            }
        } while(!isValid(opponentAttackResult));
    }

    // This will be a random character from A-J to indicate a column
    private char randCol() {
        Random rand = new Random();
        char randomColumn = (char)(rand.nextInt(10) + 'B');
        return randomColumn;
    }


    // This will be a random integer from 1-10 to indicate a row
    private int randRow() {
        Random rand = new Random();
        int randomRow = rand.nextInt(10) + 2;
        return randomRow;
    }

    // This will be either a random true or false boolean value (true for vertical, false for horizontal)
    private boolean randBool() {
        Random rand = new Random();
        return rand.nextBoolean();
    }

    private boolean isValid(Result r) {
        for (AtackStatus a : r.getResults()) {
            if (a == AtackStatus.INVALID) {
                return false;
            }
        }
        return true;
    }
}

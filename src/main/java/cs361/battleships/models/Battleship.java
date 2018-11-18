package cs361.battleships.models;

public class Battleship extends Ship {

    public Battleship() {
        this.shipSize = 4;
        this.kind = "BATTLESHIP";
        this.underwater = false;
    }
}

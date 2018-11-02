package cs361.battleships.models;

public class Battleship extends Ship {

    public Battleship() {
        this.shipSize = 4;
        this.hits = 0;
        this.kind = "BATTLESHIP";
    }
}

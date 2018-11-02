package cs361.battleships.models;

public class Minesweeper extends Ship {
    public Minesweeper () {
        this.shipSize = 2;
        this.hits = 0;
        this.kind = "MINESWEEPER";
    }
}

package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {


    //Checking invalid Minesweeper placement
    @Test
    public void testInvalidPlacementMineSweeper() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', false));
    }

    //Checking valid Destroyer placement
    @Test
    public void testValidPlaceShipMineSweeper(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 5, 'C', false));
    }

    //Checking invalid Destroyer placement
    @Test
    public void testInvalidPlaceShipDestroyer(){
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("DESTROYER"), 10, 'I', false));
    }

    //Checking valid Destroyer placement
    @Test
    public void testValidPlaceShipDestroyer(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("DESTROYER"), 3, 'A', true));
    }

    //Checking invalid Battleship placement
    @Test
    public void testInvalidPlaceShipBattleship(){
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 8, 'H', true));
    }

    //Checking valid Battleship placement
    @Test
    public void testValidPlaceShipBattleship(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 10, 'G', false));
    }

    @Test
    public void testInvalidOverlapPlacement(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 10, 'G', false));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 9, 'H', true));
    }

    @Test
    public void testValidGamePlacement(){


    }

}

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
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 12, 'C', false));
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
        assertFalse(board.placeShip(new Ship("DESTROYER"), 12, 'I', false));
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
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 9, 'H', true));
    }

    //Checking valid Battleship placement
    @Test
    public void testValidPlaceShipBattleship(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 9, 'G', false));
    }

    @Test
    public void testInvalidOverlapPlacement(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 1, 'H', true));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 3, 'G', false));
    }

    @Test
    public void testValidGamePlacement(){
        Game game = new Game();
        assertTrue(game.placeShip(new Ship("BATTLESHIP"), 5, 'F', false));

    }

    @Test
    public void testInvalidGamePlacement(){
        Game game = new Game();
        assertTrue(game.placeShip(new Ship("BATTLESHIP"), 1, 'A', false));
        assertTrue(game.placeShip(new Ship("MINESWEEPER"), 5, 'B', true));
        assertTrue(game.placeShip(new Ship("DESTROYER"), 7, 'F', false));

    }

    @Test
    public void testGame(){
        Game game = new Game();
        assertTrue(game.placeShip(new Ship("BATTLESHIP"), 1, 'A', false));
        assertTrue(game.placeShip(new Ship("MINESWEEPER"), 5, 'B', true));
        assertTrue(game.placeShip(new Ship("DESTROYER"), 7, 'F', false));
        assertFalse(game.attack(12, 'A'));
        assertTrue(game.attack(5, 'B'));
        assertTrue(game.attack(6, 'B'));
        assertTrue(game.attack(1, 'A'));
        assertTrue(game.attack(1, 'B'));
        assertTrue(game.attack(1, 'C'));
        assertTrue(game.attack(1, 'D'));
        assertTrue(game.attack(7, 'F'));
        assertTrue(game.attack(7, 'G'));
        assertTrue(game.attack(7, 'H'));
        //NEED BETTER TEST CASES

    }

    @Test
    public void testUniqueShips(){
        Game game = new Game();
        assertTrue(game.placeShip(new Ship("BATTLESHIP"), 1, 'A', false));
        assertFalse(game.placeShip(new Ship("BATTLESHIP"), 2, 'A', false));
        assertTrue(game.placeShip(new Ship("MINESWEEPER"), 5, 'B', true));
        assertFalse(game.placeShip(new Ship("MINESWEEPER"), 5, 'C', true));
        assertTrue(game.placeShip(new Ship("DESTROYER"), 7, 'F', false));
        assertFalse(game.placeShip(new Ship("DESTROYER"), 10, 'F', false));

    }

}

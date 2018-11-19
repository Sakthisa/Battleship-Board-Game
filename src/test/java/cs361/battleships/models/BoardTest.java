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
        assertFalse(board.placeShip(new Minesweeper(), 13, 'C', false));
    }

    //Checking valid Destroyer placement
    @Test
    public void testValidPlaceShipMineSweeper(){
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper(), 5, 'C', false));
    }

    @Test
    public void testValidPlaceShipSubmarine(){
        Board board = new Board();
        assertTrue(board.placeShip(new Submarine(), 5, 'C', false));
    }

    @Test
    public void testInvalidPlaceShipSubmarine(){
        Board board = new Board();
        assertFalse(board.placeShip(new Submarine(), 1, 'C', false));
    }

    //Checking invalid Destroyer placement
    @Test
    public void testInvalidPlaceShipDestroyer(){
        Board board = new Board();
        assertFalse(board.placeShip(new Destroyer(), 12, 'I', false));
    }

    //Checking valid Destroyer placement
    @Test
    public void testValidPlaceShipDestroyer(){
        Board board = new Board();
        assertTrue(board.placeShip(new Destroyer(), 3, 'A', true));
    }

    //Checking invalid Battleship placement
    @Test
    public void testInvalidPlaceShipBattleship(){
        Board board = new Board();
        assertFalse(board.placeShip(new Battleship(), 9, 'H', true));
    }

    //Checking valid Battleship placement
    @Test
    public void testValidPlaceShipBattleship(){
        Board board = new Board();
        assertTrue(board.placeShip(new Battleship(), 9, 'G', false));
    }

    @Test
    public void testInvalidOverlapPlacement(){
        Board board = new Board();
        assertTrue(board.placeShip(new Battleship(), 1, 'H', true));
        assertFalse(board.placeShip(new Minesweeper(), 3, 'G', false));
    }

    @Test
    public void testValidOverlapPlacement(){
        Board board = new Board();
        assertTrue(board.placeSubShip(new Submarine(), 1, 'H', true, true));
        assertTrue(board.placeShip(new Minesweeper(), 3, 'G', false));
    }

    @Test
    public void testValidGamePlacement(){
        Game game = new Game();
        assertTrue(game.placeShip(new Battleship(), 5, 'F', false));

    }

    @Test
    public void testInvalidGamePlacement(){
        Game game = new Game();
        assertTrue(game.placeShip(new Battleship(), 1, 'A', false));
        assertTrue(game.placeShip(new Minesweeper(), 5, 'B', true));
        assertTrue(game.placeShip(new Destroyer(), 7, 'F', false));

    }

    @Test
    public void testGame(){
        Game game = new Game();
        assertTrue(game.placeShip(new Battleship(), 1, 'A', false));
        assertTrue(game.placeShip(new Minesweeper(), 5, 'B', true));
        assertTrue(game.placeShip(new Destroyer(), 7, 'F', false));
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
        assertTrue(game.placeShip(new Battleship(), 1, 'A', false));
        assertFalse(game.placeShip(new Battleship(), 2, 'A', false));
        assertTrue(game.placeShip(new Minesweeper(), 5, 'B', true));
        assertFalse(game.placeShip(new Minesweeper(), 5, 'C', true));
        assertTrue(game.placeShip(new Destroyer(), 7, 'F', false));
        assertFalse(game.placeShip(new Destroyer(), 10, 'F', false));
        assertTrue(game.placeSubShip(new Submarine(), 10, 'F', false, true));
        assertFalse(game.placeShip(new Submarine(), 10, 'F', false));

    }

    // Check to make sure that redundant attacks are valid
    @Test
    public void testRedundant() {
        Game game = new Game();
        assertTrue(game.attack(7, 'F'));
        assertTrue(game.attack(7, 'F'));
    }

}

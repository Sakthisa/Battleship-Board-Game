package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GameTest { // This class has 100% coverage of all lines in Game.java

    @Test
    public void testInvalidShipYPlacement(){
        Ship ship = new Ship("BATTLESHIP");
        Game game = new Game();

        assertFalse(game.placeShip(ship,5, 'Z', true));
    }

    @Test
    public void testInvalidShipXPlacement(){
        Ship ship = new Ship("BATTLESHIP");
        Game game = new Game();

        assertFalse(game.placeShip(ship,-3, 'E', false));
    }

    @Test
    public void testValidShipPlacement(){
        Ship ship = new Ship("MINESWEEPER");
        Game game = new Game();

        assertTrue(game.placeShip(ship,4, 'A', false));
    }

    @Test
    public void testInvalidXAttack(){
        Ship ship = new Ship("MINESWEEPER");
        Game game = new Game();

        assertFalse(game.attack(20, 'D'));
        assertFalse(game.attack(-5, 'D'));
    }

    @Test
    public void testInvalidYAttack(){
        Ship ship = new Ship("MINESWEEPER");
        Game game = new Game();

        assertFalse(game.attack(4, 'X'));
        assertFalse(game.attack(4, '1'));
    }

    @Test
    public void testValidAttack(){
        Ship ship = new Ship("MINESWEEPER");
        Game game = new Game();

        assertTrue(game.attack(10, 'A'));
        assertTrue(game.attack(1, 'B'));
        assertTrue(game.attack(2, 'C'));
        assertTrue(game.attack(3, 'D'));
        assertTrue(game.attack(4, 'E'));
        assertTrue(game.attack(5, 'F'));
        assertTrue(game.attack(6, 'G'));
        assertTrue(game.attack(7, 'H'));
        assertTrue(game.attack(8, 'I'));
        assertTrue(game.attack(9, 'J'));

    }
  

    @Test
    public void testShipPlacement(){ // Test class for ship placement
        Game game = new Game(); // Creates new game
        assertTrue(game.placeShip(new Ship("MINESWEEPER"), 1, 'A', true)); // places minesweeper ship at 1A vertically
        assertTrue(game.placeShip(new Ship("DESTROYER"), 1, 'C', true)); // places destroyer ship at 1A vertically (This test currently fails)
        assertFalse(game.placeShip(new Ship("xyz"), 1, 'F', true)); // not valid ship name
        assertFalse(game.placeShip(new Ship("MINESWEEPER"), 1, 'J', true)); // places 4th ship on board (not possible)
        assertFalse(game.placeShip(new Ship("MINESWEEPER"), 1, 'A', true)); // places ship in already chosen location (not possible)
        Game gamebp = new Game();
        assertFalse(gamebp.placeShip(new Ship("MINESWEEPER"), 0, 'A', true)); // places minesweeper ship at 0A vertically (not possible)
        assertFalse(gamebp.placeShip(new Ship("MINESWEEPER"), 1, 'Z', true)); // places minesweeper ship at 1Z vertically (not possible)
        Game game2 = new Game();
        assertTrue(game2.placeShip(new Ship("MINESWEEPER"), 1, 'A', false)); // places minesweeper ship at 1A horizontally
        assertTrue(game2.placeShip(new Ship("DESTROYER"), 9, 'A', false)); // places destroyer ship at 1A horizontally
        assertFalse(game2.placeShip(new Ship("xyz"), 8, 'A', false)); // not valid ship name
        Game gamebph = new Game();
        assertFalse(gamebph.placeShip(new Ship("MINESWEEPER"), 0, 'A', false)); // places minesweeper ship at 0A horizontally (not possible)
        assertFalse(gamebph.placeShip(new Ship("MINESWEEPER"), 1, 'Z', false)); // places minesweeper ship at 1Z horizontally (not possible)
    }

    @Test
    public void testAttack(){ // Test class to test attacks from player and AI
        Game game = new Game(); // Creates new game
        assertTrue(game.attack(1, 'A')); // attacks at 1A
        assertTrue(game.attack(10, 'J')); // attacks at 10J
        assertFalse(game.attack(11, 'A'));  // attacks at 11A (not possible)
        assertFalse(game.attack(1, 'Z')); // attacks at 1Z (not possible)
        assertFalse(game.attack(1, 'a')); // attacks at 1a (with lowercase so not possible)
        assertFalse(game.attack(-1, 'A')); // attacks at -1A (not possible to have negative coordinates)
    }


}
package cs361.battleships.models;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest { // This class has 100% coverage of all lines in Game.java

    @Test
    public void testShipPlacement(){ // Test class for ship placement
        Game game = new Game(); // Creates new game
        assertTrue(game.placeShip(new Ship("MINESWEEPER"), 1, 'A', true)); // places minesweeper ship at 1A vertically
        assertTrue(game.placeShip(new Ship("DESTROYER"), 1, 'C', true)); // places destroyer ship at 1A vertically (This test currently fails)
        assertTrue(game.placeShip(new Ship("xyz"), 1, 'F', true)); // places other type ship at 1A vertically
        assertFalse(game.placeShip(new Ship("MINESWEEPER"), 1, 'J', true)); // places 4th ship on board (not possible)
        assertFalse(game.placeShip(new Ship("MINESWEEPER"), 1, 'A', true)); // places ship in already chosen location (not possible)
        Game gamebp = new Game();
        assertFalse(gamebp.placeShip(new Ship("MINESWEEPER"), 0, 'A', true)); // places minesweeper ship at 0A vertically (not possible)
        assertFalse(gamebp.placeShip(new Ship("MINESWEEPER"), 1, 'Z', true)); // places minesweeper ship at 1Z vertically (not possible)
        Game game2 = new Game();
        assertTrue(game2.placeShip(new Ship("MINESWEEPER"), 1, 'A', false)); // places minesweeper ship at 1A horizontally
        assertTrue(game2.placeShip(new Ship("DESTROYER"), 9, 'A', false)); // places destroyer ship at 1A horizontally
        assertTrue(game2.placeShip(new Ship("xyz"), 8, 'A', false)); // places other type ship at 1A vertically
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

package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

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


}

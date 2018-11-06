package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.*;


public class GameTest { // This class has 100% coverage of all lines in Game.java

    @Test
    public void testInvalidShipYPlacement(){
        Ship ship = new Battleship();
        Game game = new Game();

        assertFalse(game.placeShip(ship,5, 'Z', true));
    }

    @Test
    public void testInvalidShipXPlacement(){
        Ship ship = new Battleship();
        Game game = new Game();

        assertFalse(game.placeShip(ship,-3, 'E', false));
    }

    @Test
    public void testValidShipPlacement(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertTrue(game.placeShip(ship,4, 'A', false));
    }

    @Test
    public void testInvalidXAttack(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertFalse(game.attack(20, 'D', false));
        assertFalse(game.attack(-5, 'D', false));
    }

    @Test
    public void testInvalidYAttack(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertFalse(game.attack(4, 'X', false));
        assertFalse(game.attack(4, '1', false));
    }

    @Test
    public void testValidAttack(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertTrue(game.attack(10, 'A', false));
        assertTrue(game.attack(1, 'B', false));
        assertTrue(game.attack(2, 'C', false));
        assertTrue(game.attack(3, 'D', false));
        assertTrue(game.attack(4, 'E', false));
        assertTrue(game.attack(5, 'F', false));
        assertTrue(game.attack(6, 'G', false));
        assertTrue(game.attack(7, 'H', false));
        assertTrue(game.attack(8, 'I', false));
        assertTrue(game.attack(9, 'J', false));

    }
  

    @Test
    public void testShipPlacement(){ // Test class for ship placement
        Game game = new Game(); // Creates new game
        assertTrue(game.placeShip(new Minesweeper(), 1, 'A', true)); // places minesweeper ship at 1A vertically
        assertTrue(game.placeShip(new Destroyer(), 1, 'C', true)); // places destroyer ship at 1A vertically (This test currently fails)
        assertFalse(game.placeShip(new Minesweeper(), 1, 'J', true)); // places 4th ship on board (not possible)
        assertFalse(game.placeShip(new Minesweeper(), 1, 'A', true)); // places ship in already chosen location (not possible)
        Game gamebp = new Game();
        assertFalse(gamebp.placeShip(new Minesweeper(), 0, 'A', true)); // places minesweeper ship at 0A vertically (not possible)
        assertFalse(gamebp.placeShip(new Minesweeper(), 1, 'Z', true)); // places minesweeper ship at 1Z vertically (not possible)
        Game game2 = new Game();
        assertTrue(game2.placeShip(new Minesweeper(), 1, 'A', false)); // places minesweeper ship at 1A horizontally
        assertTrue(game2.placeShip(new Destroyer(), 9, 'A', false)); // places destroyer ship at 1A horizontally
        Game gamebph = new Game();
        assertFalse(gamebph.placeShip(new Minesweeper(), 0, 'A', false)); // places minesweeper ship at 0A horizontally (not possible)
        assertFalse(gamebph.placeShip(new Minesweeper(), 1, 'Z', false)); // places minesweeper ship at 1Z horizontally (not possible)
    }

    @Test
    public void testAttack(){ // Test class to test attacks from player and AI
        Game game = new Game(); // Creates new game
        assertTrue(game.attack(1, 'A', false)); // attacks at 1A
        assertTrue(game.attack(10, 'J', false)); // attacks at 10J
        assertFalse(game.attack(12, 'A', false));  // attacks at 11A (not possible)
        assertFalse(game.attack(1, 'Z', false)); // attacks at 1Z (not possible)
        assertFalse(game.attack(1, 'a', false)); // attacks at 1a (with lowercase so not possible)
        assertFalse(game.attack(-1, 'A', false)); // attacks at -1A (not possible to have negative coordinates)
    }

    @Test
    public void testMultipleRadar() {
        Game game = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 1, 'A', false);
        board.placeShip(new Minesweeper(), 3, 'A', false);
        board.placeShip(new Battleship(), 5, 'A', false);
        assert (AtackStatus.HIT == board.attack(1, 'A', false).getResult());
        assert (AtackStatus.MISS == board.attack(1, 'B', false).getResult());
        assert (AtackStatus.HIT == board.attack(1, 'C', false).getResult());
        assert (AtackStatus.SUNK == board.attack(1, 'B', false).getResult());
        assert (AtackStatus.RADAR == board.attack(4, 'D', true).getResult());
        assert (AtackStatus.RADAR == board.attack(6, 'A', true).getResult());
        assert (AtackStatus.INVALID == board.attack(2, 'C', true).getResult());
    }

    @Test
    public void testEarlyRadar() {
        Game game = new Game();
        game.placeShip(new Destroyer(), 1, 'A', false);
        game.placeShip(new Minesweeper(), 3, 'A', false);
        game.placeShip(new Battleship(), 5, 'A', false);
        assertTrue(game.attack(1, 'A', false));
        assertFalse(game.attack(2, 'C', true));
    }


}

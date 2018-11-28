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
    public void testInvalidSubmarineYPlacement(){
        Ship ship = new Submarine();
        Game game = new Game();

        assertFalse(game.placeShip(ship,1, 'K', true));
    }

    @Test
    public void testInvalidShipSubmarinePlacement(){
        Ship ship = new Submarine();
        Game game = new Game();

        assertFalse(game.placeShip(ship,1, 'E', false));
    }

    @Test
    public void testValidShipPlacement(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertTrue(game.placeShip(ship,4, 'B', false));
    }

    @Test
    public void testInvalidXAttack(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertFalse(game.attack(20, 'D'));
        assertFalse(game.attack(-5, 'D'));
    }

    @Test
    public void testInvalidYAttack(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertFalse(game.attack(4, 'X'));
        assertFalse(game.attack(4, '1'));
    }

    @Test
    public void testValidAttack(){
        Ship ship = new Minesweeper();
        Game game = new Game();

        assertTrue(game.attack(10, 'B'));
        assertTrue(game.attack(2, 'B'));
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
        assertTrue(game.placeShip(new Minesweeper(), 2, 'B', true)); // places minesweeper ship at 1A vertically
        assertTrue(game.placeShip(new Destroyer(), 5, 'C', true)); // places destroyer ship at 1A vertically (This test currently fails)
        assertFalse(game.placeShip(new Minesweeper(), 1, 'J', true)); // places 4th ship on board (not possible)
        assertFalse(game.placeShip(new Minesweeper(), 1, 'A', true)); // places ship in already chosen location (not possible)
        assertTrue(game.placeSubShip(new Submarine(), 5, 'C', false, true));
        assertFalse(game.placeShip(new Submarine(), 3, 'C', false));
        Game gamebp = new Game();
        assertFalse(gamebp.placeShip(new Minesweeper(), 0, 'A', true)); // places minesweeper ship at 0A vertically (not possible)
        assertFalse(gamebp.placeShip(new Minesweeper(), 1, 'Z', true)); // places minesweeper ship at 1Z vertically (not possible)
        Game game2 = new Game();
        assertTrue(game2.placeShip(new Minesweeper(), 2, 'B', false)); // places minesweeper ship at 1A horizontally
        assertTrue(game2.placeShip(new Destroyer(), 9, 'B', false)); // places destroyer ship at 1A horizontally
        Game gamebph = new Game();
        assertFalse(gamebph.placeShip(new Minesweeper(), 0, 'A', false)); // places minesweeper ship at 0A horizontally (not possible)
        assertFalse(gamebph.placeShip(new Minesweeper(), 1, 'Z', false)); // places minesweeper ship at 1Z horizontally (not possible)
        Game nGame = new Game();
        assertTrue(nGame.placeShip(new Submarine(), 3, 'E', false));
        assertFalse(nGame.placeShip(new Submarine(), 7, 'E', true));
    }

    @Test
    public void testAttack(){ // Test class to test attacks from player and AI
        Game game = new Game(); // Creates new game
        assertTrue(game.attack(2, 'B')); // attacks at 1A
        assertTrue(game.attack(10, 'J')); // attacks at 10J
        assertFalse(game.attack(12, 'A'));  // attacks at 11A (not possible)
        assertFalse(game.attack(1, 'Z')); // attacks at 1Z (not possible)
        assertFalse(game.attack(1, 'a')); // attacks at 1a (with lowercase so not possible)
        assertFalse(game.attack(-1, 'A')); // attacks at -1A (not possible to have negative coordinates)
    }

    @Test
    public void testMultipleRadar() {
        Game game = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 2, 'B', false);
        board.placeShip(new Minesweeper(), 3, 'B', false);
        board.placeShip(new Battleship(), 5, 'B', false);
        board.placeSubShip(new Submarine(), 5, 'B', false, true);
        assert (AtackStatus.HIT == board.attack(2, 'B').getResults().get(0));
        assert (AtackStatus.MISS == board.attack(2, 'C').getResults().get(0));
        assert (AtackStatus.HIT == board.attack(2, 'B').getResults().get(0));
        assert (AtackStatus.SUNK == board.attack(2, 'C').getResults().get(0));
        assert (AtackStatus.RADAR == board.radarAttack(4, 'E').getResults().get(0));
        assert (AtackStatus.RADAR == board.radarAttack(6, 'B').getResults().get(0));
        assert (AtackStatus.INVALID == board.radarAttack(2, 'D').getResults().get(0));
    }

    @Test
    public void testEarlyRadar() {
        Game game = new Game();
        game.placeShip(new Destroyer(), 2, 'B', false);
        game.placeShip(new Minesweeper(), 3, 'B', false);
        game.placeShip(new Battleship(), 5, 'B', false);
        assertTrue(game.attack(2, 'B'));
        assertFalse(game.radarAttack(2, 'C'));
    }

    @Test
    public void attackCQ() {
        Game game = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 2, 'B', false);
        board.placeShip(new Minesweeper(), 3, 'B', true);
        board.placeShip(new Battleship(), 5, 'B', false);
        board.placeSubShip(new Submarine(), 3, 'B', false, true);
        assert (AtackStatus.HIT == board.attack(2, 'B').getResults().get(0));
        assert (AtackStatus.MISS == board.attack(2, 'C').getResults().get(0));
        assert (AtackStatus.MISS == board.attack(8, 'C').getResults().get(0));
        assert (AtackStatus.HIT == board.attack(2, 'D').getResults().get(0));
        assert (AtackStatus.SUNK == board.attack(2, 'C').getResults().get(0));
        assertEquals(AtackStatus.HIT, board.attack(3, 'D').getResults().get(0));
        assert (AtackStatus.SUNK == board.attack(3, 'B').getResults().get(0));
        assert (AtackStatus.RADAR == board.radarAttack(4, 'E').getResults().get(0));
        assert (AtackStatus.RADAR == board.radarAttack(6, 'B').getResults().get(0));
        assert (AtackStatus.INVALID == board.radarAttack(2, 'D').getResults().get(0));
        assert (AtackStatus.MISS == board.attack(5, 'D').getResults().get(0));
        assert (AtackStatus.SUNK == board.attack(5, 'D').getResults().get(0));
        assert (AtackStatus.MISS == board.attack(3, 'E').getResults().get(0));
        assert (AtackStatus.SURRENDER == board.attack(3, 'E').getResults().get(0));

    }


}

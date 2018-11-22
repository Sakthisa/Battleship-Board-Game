package cs361.battleships.models;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LaserTest {
    @Test
    public void multipleAttack() {
        Game g = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 2, 'A', false);
        board.placeShip(new Minesweeper(), 3, 'A', false);
        board.placeShip(new Battleship(), 5, 'A', false);
        board.placeSubShip(new Submarine(), 5, 'A', false, true);

        assertEquals(AtackStatus.SUNK, board.attack(3,'A').getResults().get(0));
        List<AtackStatus> a = board.attack(5,'A').getResults();
        assertEquals(AtackStatus.HIT, a.get(0));
        assertEquals(AtackStatus.HIT, a.get(1));
    }

    @Test
    public void multipleCQAttack() {
        Game g = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 2, 'A', false);
        board.placeShip(new Minesweeper(), 3, 'A', false);
        board.placeShip(new Battleship(), 5, 'B', false);
        board.placeSubShip(new Submarine(), 5, 'A', false, true);

        assertEquals(AtackStatus.SUNK, board.attack(3,'A').getResults().get(0));
        List<AtackStatus> a = board.attack(5,'D').getResults();
        assertEquals(AtackStatus.MISS, a.get(0));
        assertEquals(AtackStatus.MISS, a.get(1));
        a = board.attack(5, 'D').getResults();
        assertEquals(AtackStatus.SUNK, a.get(0));
        assertEquals(AtackStatus.SUNK, a.get(1));
    }

    @Test
    public void testSunkMultipleShips() {
        Game g = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 4, 'A', false);
        board.placeShip(new Minesweeper(), 3, 'A', false);
        board.placeShip(new Battleship(), 5, 'B', false);
        board.placeSubShip(new Submarine(), 5, 'A', false, true);

        assertEquals(AtackStatus.SUNK, board.attack(3,'A').getResults().get(0));
        assertEquals(AtackStatus.MISS, board.attack(4,'B').getResults().get(0));
        assertEquals(AtackStatus.SUNK, board.attack(4,'B').getResults().get(0));;
        List<AtackStatus> a = board.attack(5,'D').getResults();
        assertEquals(AtackStatus.MISS, a.get(0));
        assertEquals(AtackStatus.MISS, a.get(1));
        a = board.attack(5, 'D').getResults();
        assertEquals(AtackStatus.SUNK, a.get(0));
        assertEquals(AtackStatus.SURRENDER, a.get(1));

    }
}

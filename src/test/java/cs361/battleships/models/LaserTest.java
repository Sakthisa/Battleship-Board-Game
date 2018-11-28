package cs361.battleships.models;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LaserTest {
    @Test
    public void multipleAttack() {
        Game g = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 2, 'B', false);
        board.placeShip(new Minesweeper(), 3, 'B', false);
        board.placeShip(new Battleship(), 5, 'B', false);
        board.placeSubShip(new Submarine(), 5, 'B', false, true);

        assertEquals(AtackStatus.SUNK, board.attack(3,'B').getResults().get(0));
        List<AtackStatus> a = board.attack(5,'B').getResults();
        assertEquals(AtackStatus.HIT, a.get(0));
        assertEquals(AtackStatus.HIT, a.get(1));
    }

    @Test
    public void multipleCQAttack() {
        Game g = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 2, 'B', false);
        board.placeShip(new Minesweeper(), 3, 'B', false);
        board.placeShip(new Battleship(), 5, 'C', false);
        board.placeSubShip(new Submarine(), 5, 'B', false, true);

        assertEquals(AtackStatus.SUNK, board.attack(3,'B').getResults().get(0));
        List<AtackStatus> a = board.attack(5,'E').getResults();
        assertEquals(AtackStatus.MISS, a.get(0));
        assertEquals(AtackStatus.MISS, a.get(1));
        a = board.attack(5, 'E').getResults();
        assertEquals(AtackStatus.SUNK, a.get(0));
        assertEquals(AtackStatus.SUNK, a.get(1));
    }

    @Test
    public void testSunkMultipleShips() {
        Game g = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 4, 'B', false);
        board.placeShip(new Minesweeper(), 3, 'B', false);
        board.placeShip(new Battleship(), 5, 'C', false);
        board.placeSubShip(new Submarine(), 5, 'B', false, true);

        assertEquals(AtackStatus.SUNK, board.attack(3,'B').getResults().get(0));
        assertEquals(AtackStatus.MISS, board.attack(4,'C').getResults().get(0));
        assertEquals(AtackStatus.SUNK, board.attack(4,'C').getResults().get(0));;
        List<AtackStatus> a = board.attack(5,'E').getResults();
        assertEquals(AtackStatus.MISS, a.get(0));
        assertEquals(AtackStatus.MISS, a.get(1));
        a = board.attack(5, 'E').getResults();
        assertEquals(AtackStatus.SUNK, a.get(0));
        assertEquals(AtackStatus.SURRENDER, a.get(1));

    }
}

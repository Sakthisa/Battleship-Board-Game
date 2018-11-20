package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubmarineTest {

    @Test
    public void testSubmarineCreation(){
        Submarine s = new Submarine();
        s.setUnderwater();
        assertEquals(5, s.getShipSize());
        assertEquals("SUBMARINE", s.getKind());
        assertEquals(true, s.getUnderwater());
    }
    @Test
    public void testSubmarineAttack() {
        Game g = new Game();
        Board board = new Board();
        board.placeShip(new Destroyer(), 1, 'A', false);
        board.placeShip(new Minesweeper(), 3, 'A', false);
        board.placeShip(new Battleship(), 5, 'A', false);
        board.placeSubShip(new Submarine(), 8, 'A', false, true);

        assertEquals(AtackStatus.MISS, board.attack(8,'B').getResults().get(0));
    }

}

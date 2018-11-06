package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinesweeperTest {

    @Test
    public void testBattleshipCreation(){
        Minesweeper m = new Minesweeper();
        assertEquals(2, m.getShipSize());
        assertEquals("MINESWEEPER", m.getKind());
    }
}

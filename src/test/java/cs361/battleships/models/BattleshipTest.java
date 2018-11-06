package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleshipTest {

    @Test
    public void testBattleshipCreation(){
        Battleship b = new Battleship();
        assertEquals(4, b.getShipSize());
        assertEquals("BATTLESHIP", b.getKind());
    }
}

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
}

package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DestroyerTest {

    @Test
    public void testDestroyerCreation(){
        Destroyer d = new Destroyer();
        assertEquals(3, d.getShipSize());
        assertEquals("DESTROYER", d.getKind());
        assertEquals(false, d.getUnderwater());
    }
}

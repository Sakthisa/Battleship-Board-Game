package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResultTest {

    @Test
    public void testResults(){
        Result r = new Result();
        AtackStatus a = AtackStatus.INVALID;
        r.setResult(a);
        assertEquals(AtackStatus.INVALID, r.getResult());
        a = AtackStatus.MISS;
        r.setResult(a);
        assertEquals(AtackStatus.MISS, r.getResult());
        a = AtackStatus.HIT;
        r.setResult(a);
        assertEquals(AtackStatus.HIT, r.getResult());
        a = AtackStatus.RADAR;
        r.setResult(a);
        assertEquals(AtackStatus.RADAR, r.getResult());
        a = AtackStatus.SUNK;
        r.setResult(a);
        assertEquals(AtackStatus.SUNK, r.getResult());
        a = AtackStatus.SURRENDER;
        r.setResult(a);
        assertEquals(AtackStatus.SURRENDER, r.getResult());
    }
}

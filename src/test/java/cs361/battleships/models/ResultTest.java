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
        assertEquals(AtackStatus.INVALID, r.getResults().get(0));
        a = AtackStatus.MISS;
        r.setResult(a);
        assertEquals(AtackStatus.MISS, r.getResults().get(1));
        a = AtackStatus.HIT;
        r.setResult(a);
        assertEquals(AtackStatus.HIT, r.getResults().get(2));
        a = AtackStatus.RADAR;
        r.setResult(a);
        assertEquals(AtackStatus.RADAR, r.getResults().get(3));
        a = AtackStatus.SUNK;
        r.setResult(a);
        assertEquals(AtackStatus.SUNK, r.getResults().get(4));
        a = AtackStatus.SURRENDER;
        r.setResult(a);
        assertEquals(AtackStatus.SURRENDER, r.getResults().get(5));
    }

    @Test
    public void testResultShip(){
        Result r = new Result();
        Ship s = new Ship();
        r.setShip(s);
        assertEquals(s, r.getShips().get(0));
    }

    @Test
    public void testLocationShip(){
        Result r = new Result();
        Square sq = new Square();
        r.setLocation(sq);
        Square sqi = new Square(0, 'A');
        r.setLocation(sqi);
        assertEquals(sqi, r.getLocation());
    }
}

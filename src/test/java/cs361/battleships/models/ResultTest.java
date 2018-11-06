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

    @Test
    public void testResultShip(){
        Result r = new Result();
        Ship s = new Ship();
        r.setShip(s);
        assertEquals(s, r.getShip());
        s = new Battleship();
        r.setShip(s);
        assertEquals(s, r.getShip());
        s = new Destroyer();
        r.setShip(s);
        assertEquals(s, r.getShip());
        s = new Minesweeper();
        r.setShip(s);
        assertEquals(s, r.getShip());
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

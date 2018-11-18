package cs361.battleships.models;

import org.junit.Test;

import javax.validation.constraints.Null;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class ShipTest {

    @Test
    public void testValidShipConstructorMinesweeper() {
        Ship ship;
        ship = new Minesweeper();
        assertEquals("MINESWEEPER", ship.getKind());
    }

    @Test
    public void testShipConstructorDestroyer() {
        Ship ship;
        ship = new Destroyer();
        assertEquals("DESTROYER", ship.getKind());
    }

    @Test
    public void testShipConstructorBattleship() {
        Ship ship;
        ship = new Battleship();
        assertEquals("BATTLESHIP", ship.getKind());

    }

    @Test
    public void testShipConstructorSubmarine() {
        Ship ship;
        ship = new Submarine();
        assertEquals("SUBMARINE", ship.getKind());

    }



    @Test
    //tests both get and set occupied squares
    public void testShipSizes(){
        Ship ship = new Battleship();
        assertEquals(ship.getShipSize(), 4);

        ship = new Destroyer();
        assertEquals(ship.getShipSize(), 3);

        ship = new Minesweeper();
        assertEquals(ship.getShipSize(),2);

        ship = new Submarine();
        assertEquals(ship.getShipSize(),5);
    }

    @Test
    public void testHit(){
        Ship s = new Ship();
        assertEquals(0, s.getHits());
        s.setHit();
        assertEquals(1, s.getHits());
        s.setHit();
        assertEquals(2, s.getHits());
        Battleship b = new Battleship();
        assertEquals(0, b.getHits());
        b.setHit();
        assertEquals(1, b.getHits());
        b.setHit();
        assertEquals(2, b.getHits());
        b.setHit();
        assertEquals(3, b.getHits());
        b.setHit();
        assertEquals(4, b.getHits());
        b.setHit();
        assertEquals(5, b.getHits());
        Destroyer d = new Destroyer();
        assertEquals(0, d.getHits());
        d.setHit();
        assertEquals(1, d.getHits());
        d.setHit();
        assertEquals(2, d.getHits());
        d.setHit();
        assertEquals(3, d.getHits());
        d.setHit();
        assertEquals(4, d.getHits());
        Minesweeper m = new Minesweeper();
        assertEquals(0, m.getHits());
        m.setHit();
        assertEquals(1, m.getHits());
        m.setHit();
        assertEquals(2, m.getHits());
        m.setHit();
        assertEquals(3, m.getHits());
        Submarine su = new Submarine();
        assertEquals(0, su.getHits());
        su.setHit();
        assertEquals(1, su.getHits());
        su.setHit();
        assertEquals(2, su.getHits());
        su.setHit();
        assertEquals(3, su.getHits());
    }

}
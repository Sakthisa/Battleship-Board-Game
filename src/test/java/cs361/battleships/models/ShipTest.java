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

    @Test
    public void testVertical(){
        Ship s = new Ship();
        s.setVertical(true);
        assertTrue(s.isVertical());
        s.setVertical(false);
        assertFalse(s.isVertical());
    }

    @Test
    public void testMoves(){
        Ship s = new Ship();
        s.setInitcol('A');
        s.setInitrow(1);
        List<Square> os = new ArrayList<>();
        Square sq1 = new Square(1, 'A');
        os.add(sq1);
        Square sq2 = new Square(1, 'B');
        os.add(sq2);
        Square sq3 = new Square(1, 'C');
        os.add(sq3);
        Square sq4 = new Square(1, 'D');
        os.add(sq4);
        s.setOccupiedSquares(os);
        assertEquals('A', s.getInitcol());
        assertEquals(1, s.getInitrow());
        assertTrue(s.moveDown(2, 'A'));
        assertTrue(s.moveUp(1, 'A'));
        assertFalse(s.moveRight(1, 'B'));
        assertFalse(s.moveLeft(1, 'A'));
        assertTrue(s.containsSquare(1, 'A'));
        assertFalse(s.moveLeft(1, 'A'));
        List<Square> os1 = new ArrayList<>();
        Square sq11 = new Square(0, 'A');
        os1.add(sq11);
        Square sq21 = new Square(0, 'B');
        os1.add(sq21);
        Square sq31 = new Square(0, 'C');
        os1.add(sq31);
        Square sq41 = new Square(0, 'D');
        os1.add(sq41);
        s.setOccupiedSquares(os1);
        assertFalse(s.moveUp(0, 'A'));
        List<Square> os2 = new ArrayList<>();
        Square sq12 = new Square(10, 'E');
        os2.add(sq12);
        Square sq22 = new Square(10, 'F');
        os2.add(sq22);
        Square sq32 = new Square(10, 'G');
        os2.add(sq32);
        Square sq42 = new Square(10, 'H');
        os2.add(sq42);
        s.setOccupiedSquares(os2);
        assertFalse(s.moveDown(10, 'A'));
        assertFalse(s.moveRight(1, 'F'));
    }

}
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
        ship = new Ship("MINESWEEPER");
        assertEquals("MINESWEEPER", ship.getKind());
    }

    @Test
    public void testShipConstructorDestroyer() {
        Ship ship;
        ship = new Ship("DESTROYER");
        assertEquals("DESTROYER", ship.getKind());
    }

    @Test
    public void testShipConstructorBattleship() {
        Ship ship;
        ship = new Ship("DESTROYER");
        assertEquals("DESTROYER", ship.getKind());

    }



    @Test
    //tests both get and set occupied squares
    public void testShipSizes(){
        Ship ship = new Ship("BATTLESHIP");
        assertEquals(ship.getShipSize(), 4);

        ship = new Ship("DESTROYER");
        assertEquals(ship.getShipSize(), 3);

        ship = new Ship("badname");
        assertEquals(ship.getShipSize(), 4);

        ship = new Ship("MINESWEEPER");
        assertEquals(ship.getShipSize(),2);
    }


}
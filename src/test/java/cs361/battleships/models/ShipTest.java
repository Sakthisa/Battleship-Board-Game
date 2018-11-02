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
        ship = new Destroyer();
        assertEquals("DESTROYER", ship.getKind());

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
    }


}
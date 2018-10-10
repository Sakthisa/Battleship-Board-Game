package cs361.battleships.models;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class SquareTest { // Covers 66% of lines within the Square.java class.

    @Test
    public void testRows() { // tests to see if getting rows works.
        Square sq = new Square(1, 'A'); // sets new square at 1A.
        assertTrue(1 == sq.getRow()); // checks if row is equal to 1.
        assertFalse(2 == sq.getRow()); // checks if row is equal to 2 (false).
    }

    @Test
    public void testColumns(){ // tests to see if getting columns works.
        Square sq = new Square(1, 'A');// sets new square at 1A.
        assertEquals('A', sq.getColumn()); // checks if column is equal to A.
        assertFalse('B' == sq.getColumn()); // checks if column is equal to B. (false).
    }

    @Test
    public void testSetters(){ // tests setters within function.
        Square sq = new Square(1, 'A'); // creates new square at 1A.
        sq.setRow(2); // sets row to 2.
        sq.setColumn('B'); // sets column to B.
        assertTrue(2 == sq.getRow()); // checks if row is equal to 2.
        assertFalse(1 == sq.getRow()); // checks if row is equal to 1 (false).
        assertTrue('B' == sq.getColumn()); // checks if column is equal to B.
        assertFalse('A' == sq.getColumn()); // checks if column is equal to A (false).
    }
}

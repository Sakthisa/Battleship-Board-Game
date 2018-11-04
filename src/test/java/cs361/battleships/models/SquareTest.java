package cs361.battleships.models;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class SquareTest { // Covers 66% of lines within the Square.java class.

    @Test
    public void testRows() { // tests to see if getting rows works.
        Square sq = new Square(1, 'A'); // sets new square at 1A.
        CaptainQuarter cq = new CaptainQuarter(5, 'B'); // Sets captain quarter 5B
        assertTrue(5 == cq.getRow()); // Checks if row is 5 for captain quarters
        assertFalse(6 == cq.getRow()); // Checks if row is 6 for captain quarters (false)
        assertTrue(1 == sq.getRow()); // checks if row is equal to 1.
        assertFalse(2 == sq.getRow()); // checks if row is equal to 2 (false).
    }

    @Test
    public void testColumns(){ // tests to see if getting columns works.
        Square sq = new Square(1, 'A');// sets new square at 1A.
        CaptainQuarter cq = new CaptainQuarter(5, 'B'); // Sets captain quarter 5B
        assertTrue('B' == cq.getColumn()); // Checks if column is B for captains quarters
        assertFalse('C' == cq.getColumn()); // Checks if column is C for captains quarters (false)
        assertEquals('A', sq.getColumn()); // checks if column is equal to A.
        assertFalse('B' == sq.getColumn()); // checks if column is equal to B. (false).
    }

    @Test
    public void testSetters(){ // tests setters within function.
        Square sq = new Square(1, 'A'); // creates new square at 1A.
        CaptainQuarter cq = new CaptainQuarter(5, 'B'); // Sets captain quarter 5B
        cq.setRow(6); //Sets captain quarter row to 6
        cq.setColumn('D'); //Sets captain quarter column to D
        cq.setTimesHit(2); // Sets captain quarter timesHit to 2
        sq.setTimesHit(1); // Sets normal square timeHit to 2
        sq.setRow(2); // sets row to 2.
        sq.setColumn('B'); // sets column to B.
        assertTrue(2 == sq.getRow()); // checks if row is equal to 2.
        assertFalse(1 == sq.getRow()); // checks if row is equal to 1 (false).
        assertTrue('B' == sq.getColumn()); // checks if column is equal to B.
        assertFalse('A' == sq.getColumn()); // checks if column is equal to A (false).
        assertTrue(6 == cq.getRow()); // checks if row is equal to 6.
        assertFalse(1 == cq.getRow()); // checks if row is equal to 1 (false).
        assertTrue('D' == cq.getColumn()); // checks if column is equal to D.
        assertFalse('A' == cq.getColumn()); // checks if column is equal to A (false).
        assertTrue(1 == sq.getTimesHit()); // Checks if times hit is 1 for normal square
        assertFalse(2 == sq.getTimesHit()); // Checks if times hit is 2 for normal square (false)
        assertTrue(2 == cq.getTimesHit()); // Checks if times hit is 2 for captain quarter
        assertFalse(1 == cq.getTimesHit()); // Checks if times hit is 1 for captain quarter (false)
    }

    @Test
    public void testGetters(){ // tests to see if getting columns works.
        Square sq = new Square(1, 'A');// sets new square at 1A.
        CaptainQuarter cq = new CaptainQuarter(5, 'B');// sets new captains quarter to 5B
        assertTrue("CQ" == cq.getType()); // See if CQ is the captain quarter type
        assertFalse("N" == cq.getType()); // See if N is the normal square type
        assertFalse("CQ" == sq.getType()); // See if CQ is the normal square type (false)
        assertTrue("N" == sq.getType()); // See if N is the captain quarter type (false)
        assertTrue(1 == sq.getHitCount()); //See if 1 is the hit count for normal square type
        assertTrue(2 == cq.getHitCount()); //See if 2 is the hit count for captain quarter
    }
}

package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CaptainQuarterTest {

    @Test
    public void testCQInitialization() {
        CaptainQuarter cq = new CaptainQuarter('1', 'A');
        assertEquals(0, cq.getTimesHit());
        assertEquals(2, cq.getMaxHits());
        assertEquals("CQ", cq.getType());
        CaptainQuarter cq1 = new CaptainQuarter(cq);
        assertEquals(0, cq1.getTimesHit());
        assertEquals(2, cq1.getMaxHits());
        assertEquals("CQ", cq1.getType());
    }
}

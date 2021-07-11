package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;

import comp3350.grs.R;
import comp3350.grs.exceptions.IncorrectFormat;

import static org.junit.Assert.*;

public class ReviewTest extends TestCase {
    @Test
    public void testTypical() {
        Review r = null;
        try {
            r = new Review("best", "Dota2", "User");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertNotNull(r);
        assertEquals(r.getComment(),"best");
        assertEquals(r.getUserID(), "User");
        assertEquals(r.getGameName(), "Dota2");

        Review r2 = null;

        try {
            r2 = new Review(r.getReviewID(), "best", "Dota2", "User");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        assertEquals(r2.getReviewID(), r.getReviewID());
        assertEquals(r2.getComment(), "best");
        assertEquals(r2.getUserID(), "User");
        assertEquals(r2.getGameName(), "Dota2");

        assertTrue(r.equals(r2));
    }

    @Test
    public void testEdge() {
        Review r = null;
        try {
            r = new Review("", "", "");
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }

        Review r1 = new Review();
        assertNull(r1);
    }
}

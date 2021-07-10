package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;
import comp3350.grs.exceptions.IncorrectFormat;

public class RatingTest {

    @Test
    public void testRating1(){
        Rating rate;
        rate=null;
        System.out.println("\nStarting testRating");
        try {
            rate = new Rating(1);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertFalse(rate.validRating());
            assertTrue(rate.getRatingValue() == 1);
        try {
            rate = new Rating(2,"GameA","000001");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertTrue(rate.validRating());
            assertTrue(rate.getRatingValue() == 2);
            assertTrue("GameA".equals(rate.getGameName()));
            assertTrue("000001".equals(rate.getUserID()));
        System.out.println("Finished testRating");
    }
    
}

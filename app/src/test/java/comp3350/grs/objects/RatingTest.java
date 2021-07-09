package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

public class RatingTest extends TestCase {
    public RatingTest(String arg0){
        super(arg0);
    }
    public void testRating1(){
        Rating rate;
        System.out.println("\nStarting testRating");
            rate = new Rating(1);
            assertFalse(rate.validRating());
            assertTrue(rate.getRatingValue() == 1);
            rate = new Rating(2,"GameA","000001");
            assertTrue(rate.validRating());
            assertTrue(rate.getRatingValue() == 2);
            assertTrue("GameA".equals(rate.getGameName()));
            assertTrue("000001".equals(rate.getUserID()));
        System.out.println("Finished testRating");
    }
    
}

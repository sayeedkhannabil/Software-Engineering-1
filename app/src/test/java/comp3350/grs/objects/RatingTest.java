package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;
import comp3350.grs.exceptions.IncorrectFormat;

public class RatingTest {

    @Test
    public void testTypicalRating(){
        Rating testRating=null;
        System.out.println("\nStarting testRating");
        try {
            testRating = new Rating(1.0);
            assertFalse(testRating.validRating());
            assertTrue(testRating.getRatingValue() == 1.0);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            testRating = new Rating(1.5,"GameA","001");
            assertTrue(testRating.validRating());
            assertTrue(testRating.getRatingValue() == 1.5);
            assertTrue(testRating.getGameName().equals("GameA"));
            assertTrue(testRating.getUserID().equals("001"));
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

    }

    @Test
    public void testEqualsRating(){
        Rating rate1=null;//new Rating(3.5,"GameB","002");
        Rating rate2=null;//new Rating(3.5,"GameB","002");
        try {
            rate1 = new Rating(3.5,"GameB","002");
            rate2 = new Rating(3.5,"GameB","002");
            assertTrue(rate1.validRating());
            assertTrue(rate2.validRating());
            assertTrue(rate1.equals(rate2));
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            rate1 = new Rating(3.5,"GameB","002");
            rate2 = new Rating(2.0,"GameB","002");
            assertTrue(rate1.validRating());
            assertTrue(rate2.validRating());
            assertTrue(rate1.equals(rate2));
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            rate1 = new Rating(3.5,"GameB","002");
            rate2 = new Rating(3.5,"GameC","002");
            assertTrue(rate1.validRating());
            assertTrue(rate2.validRating());
            assertFalse(rate1.equals(rate2));
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            rate1 = new Rating(3.5,"GameB","002");
            rate2 = new Rating(3.5,"GameB","003");
            assertTrue(rate1.validRating());
            assertTrue(rate2.validRating());
            assertFalse(rate1.equals(rate2));
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

    }

    @Test
    public void testNullRating(){
        Rating nullRate=null;
        try {
            nullRate = new Rating(1.0);
            assertFalse(nullRate.validRating());
            assertNotNull(nullRate.getRatingValue());
            assertNull(nullRate.getUserID());
            assertNull(nullRate.getGameName());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        System.out.println("Finished testRating");
    }
    
}

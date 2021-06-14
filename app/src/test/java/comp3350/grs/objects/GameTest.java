package comp3350.grs.objects;
import junit.framework.TestCase;

import org.junit.BeforeClass;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest extends TestCase {
    private Game typicalGame = new Game("TypicalGame", "TypicalDeveloper", "TypicalDescription", 20.00);
    private Game typicalGameSimple = new Game("GameWithOnlyAName");
    private Game nullGame = new Game();

    @Test
    public void testTypicalGame()
    {
        //typical test cases for the game with all four parameters (typicalGame)
        assertEquals("TypicalGame", typicalGame.getName());
        assertEquals("TypicalDeveloper", typicalGame.getDev());
        assertEquals("TypicalDescription", typicalGame.getDescription());
        assertEquals(20.00, typicalGame.getPrice(), 0);

        //Test ratings and reviews for a newly initialized Game (not yet rated or reviewed)
        assertEquals("The overall rating for the game should be zero.", 0.0, typicalGame.getRating(), 0);
        assertEquals("The overall rating for the game should be zero.", 0.0, typicalGameSimple.getRating(), 0);
        assertEquals("There should be no existing reviews.", true, typicalGame.getReviews().isEmpty());
        assertEquals("There should be no existing reviews.", true, typicalGameSimple.getReviews().isEmpty());
    }

    @Test
    public void testOneRatingCalculation()
    {
        //Test adding one typical rating to typical Game objects
        typicalGame.addRating(1);
        assertEquals("The overall rating for game "+typicalGame.getName()+" should now be 1 out of 5 stars.", 1, typicalGame.getRating(), 0);
        typicalGameSimple.addRating(3);
        assertEquals("The overall rating for game "+typicalGameSimple.getName()+" should now be 3 out of 5 stars.", 3, typicalGameSimple.getRating(), 0);
    }

    @Test
    public void testMultipleRatingCalculation()
    {
        typicalGame.addRating(5);
        assertEquals("The overall rating for game "+typicalGame.getName()+" should now be 5 out of 5 stars.", 5, typicalGame.getRating(), 0);

        //Test adding typical reviews to typical Game objects (the rating is also updated when adding reviews)
        //first review
        typicalGame.addReview(4, "Good game.");
        assertEquals("The overall rating for game "+typicalGame.getName()+" should now be 4.5 out of 5 stars.", 4.5, typicalGame.getRating(), .01);
        ArrayList<Review> typicalGameReviews = typicalGame.getReviews();
        assertTrue(typicalGameReviews.size() == 1);
        Review firstReview = typicalGameReviews.get(0);
        assertEquals("Good game.", firstReview.getComment());

        //add two more reviews
        typicalGame.addReview(5, "Best game ever." );
        typicalGame.addReview(2, "Game is ok.");
        typicalGameReviews = typicalGame.getReviews();
        assertEquals("Overall rating for game "+typicalGame.getName()+" should now be 4 out of 5 stars.", 4, typicalGame.getRating(), 0.01);
        assertEquals(3, typicalGameReviews.size());
    }

    @Test
    public void testNull()
    {
        //test Games created with default constructor
        assertNull(nullGame.getDev());
        assertNull(nullGame.getDescription());

        //typical test cases for the game with one parameter (typicalGameSimple) -- other variables should return null
        assertEquals("GameWithOnlyAName", typicalGameSimple.getName());
        assertNull(typicalGameSimple.getDev());
        assertNull(typicalGameSimple.getDescription());
        assertEquals(-1, typicalGameSimple.getPrice(), 0);

    }

    @Test
    public void testEqualsSelf()
    {
        //games are equal if they have the same name (or both have a null field for a name), and are both Game objects
        assertEquals(true, nullGame.equals(nullGame));
        assertEquals(true, typicalGameSimple.equals(typicalGameSimple));
        assertEquals(true, typicalGame.equals(typicalGame));
    }

    @Test
    public void testEqualsOtherGame()
    {
        Game sameName = new Game("TypicalGame", "testDev", "TestDesc", 0.0);
        assertTrue(typicalGame.equals(sameName)); //because they have the same name

        assertEquals(false, nullGame.equals(typicalGameSimple));
        assertEquals(false, nullGame.equals(typicalGame));
        assertEquals(false, typicalGameSimple.equals(typicalGame));
    }

    @Test
    public void testInvalidInput()
    {
        Game tester = new Game ("Tester", "TestDev", "TestDesc", 0.0);

        //test adding ratings to a null game
        nullGame.addRating(1);
        nullGame.addRating(3);
        //the rating should not have been recorded, so the number of ratings should be 0, and overallRating should be 0
        assertEquals(0, nullGame.getRating(), 0);
        assertEquals(0, nullGame.getNumRatings(), 0);

        //test adding reviews to a null game
        nullGame.addReview(4, "Good game.");
        nullGame.addReview(5, "Best game ever.");
        assertEquals(0, nullGame.getReviews().size());
        assertEquals(0,nullGame.getNumReviews());

        //test adding invalid ratings and review to a non-null game
        tester.addRating(0); //can't leave a rating of 0 stars!
        assertEquals(0, tester.getNumRatings());
        assertTrue(tester.getRating() == 0);

        tester.addReview(1, ""); //can't leave an empty review!
        tester.addReview(0, "valid review");
        assertEquals(0, tester.getNumRatings());
        assertEquals(0, tester.getNumReviews());
        assertTrue(tester.getReviews().size() == 0);
        assertTrue(tester.getRating() == 0);
    }
}

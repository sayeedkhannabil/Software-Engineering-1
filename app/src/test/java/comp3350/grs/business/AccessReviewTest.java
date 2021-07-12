package comp3350.grs.business;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessStub;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccessReviewTest {
    private static AccessUsers userAccess;
    private static AccessGames gameAccess;
    private static AccessReviews reviewAccess;
    private Game game1, game2;
    private Review newReview1, newReview2;
    private boolean insert1, insert2, update, del;
    private int reviewID1, reviewID2;
    private String userID, guestID;

    @BeforeClass
    public static void beforeClass(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(Main.dbName));

        reviewAccess = new AccessReviews();
        userAccess = new AccessUsers();
        gameAccess = new AccessGames();
    }

    @Before
    public void before(){
        game1 = null;
        game2 = null;
        newReview1 = null;
        newReview2 = null;
        insert1 = false;
        insert2 = false;
        update = false;
        del = false;
        reviewID1 = 0;
        reviewID2 = 0;
        userID = null;
        guestID = null;
    }

    @Test
    public void testTypical() {

        userID = "RegisteredUser";
        guestID = "Guest";
        game1 = gameAccess.getSequential();
        game2 = gameAccess.getSequential();

        String rev1 = "great game";
        String rev2 = "not worth it";

        try {
            newReview1 = new Review(rev1, game1.getName(), guestID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try {
            newReview2 = new Review(rev2, game2.getName(), guestID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        // test inserting
        AccessReviews reviewAccess = new AccessReviews();
        insert1 = reviewAccess.insertReview(newReview1);
        insert2 = reviewAccess.insertReview(newReview2);
        assertTrue(insert1);
        assertTrue(insert2);

        //numbers of review in game1 should be  1
        assertEquals(1, reviewAccess.getReviewNumByGame(game1.getName()));

        List<Review> l = reviewAccess.getReviewsByGame(game1.getName());
        assertEquals(1, l.size());
        assertEquals(reviewAccess.getReviewById(newReview1.getReviewID()), newReview1);

        //inserting review for different games
        l = reviewAccess.getReviewsByGame(game2.getName());
        assertEquals(1, l.size());
        assertEquals(reviewAccess.getReviewById(newReview2.getReviewID()), newReview2);

        //multiple reviews for same game
        try {
            newReview1 = new Review("good", game1.getName(), userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        insert1 = reviewAccess.insertReview(newReview1);
        assertTrue(insert1);

        l = reviewAccess.getReviewsByGame(game1.getName());

        assertEquals(2, l.size());
        assertEquals(newReview1.getComment(), "good");

        //user-based testing
        l = reviewAccess.getReviewsByUser(userID);
        assertEquals(1, l.size());
        assertEquals(userID, l.get(0).getUserID());

        l = reviewAccess.getReviewsByUser(guestID);
        assertEquals(2, l.size());
        assertEquals(l.get(0).getUserID(), guestID);
        assertTrue(l.contains(newReview1));
        assertTrue(l.contains(newReview2));

        //finding reviews by ID
        assertNotNull(newReview1);
        int id = newReview1.getReviewID();
        Review r = reviewAccess.getReviewById(id);
        assertTrue(r.equals(newReview1));


        //update reviews
        String updateComment = "good teammates";
        Review updateReview = null;
        try {
            updateReview = new Review(id, updateComment, game1.getName(), guestID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        update = reviewAccess.updateReview(updateReview);
        assertTrue(update);

        l = reviewAccess.getReviewsByGame(game1.getName());
        assertEquals(2, l.size());
        assertEquals(updateReview.getComment(), updateComment);


        //getting all reviews
        l = reviewAccess.getAllReviews();
        assertEquals(3, l.size());
        assertTrue(l.contains(updateReview));
        assertTrue(l.contains(newReview2));

        //check reviews 1 by 1
        Review next = reviewAccess.getSequential();
        assertEquals(next, updateReview);
        next = reviewAccess.getSequential();
        assertEquals(next, newReview2);

        //delete reviews
        l = reviewAccess.getReviewsByGame(game1.getName());
        int prevSize = l.size();
        del = reviewAccess.deleteReview(updateReview);
        assertTrue(del);
        l = reviewAccess.getReviewsByGame(game1.getName());
        assertEquals(prevSize-1, l.size()); //number of reviews for game1 decreased by 1 after 1 review was deleted

        reviewAccess.clear();
        l = reviewAccess.getAllReviews();
        assertEquals(l.size(), 0);
        Review rev = reviewAccess.getSequential();
        assertNull(rev);
    }

    @Test
    public void testEdge() {
        AccessReviews reviewAccess = new AccessReviews();

        //when no reviews is given
        List<Review> l = reviewAccess.getAllReviews();
        assertEquals(l.size(), 0);
        Review r = reviewAccess.getSequential();
        assertNull(r);

        Review newReview = null;

        try {
            newReview = new Review("", "", "");
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }

        try {
            newReview2 = new Review(" ", " ", " ");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        boolean insert = reviewAccess.insertReview(newReview);
        assertFalse(insert);

        reviewAccess.clear();
        r = reviewAccess.getSequential();

        assertNull(r);
    }

    @AfterClass
    public static void AfterClass(){
        Services.closeDataAccess();
    }


}

package comp3350.grs.business;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessStub;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccessReviewTest {
    AccessUsers userAccess;

    @Before
    public void before(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(Main.dbName));

        userAccess = new AccessUsers();
    }

    @Test
    public void testTypical() {
        User user = userAccess.getSequential();
        String rev1 = "great game";
        String rev2 = "not worth it";

        Review newReview1 = null;

        try {
            newReview1 = new Review(rev1, "Dota2", "Guest");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        Review newReview2 = null;

        try {
            newReview2 = new Review(rev2, "GTA", "Guest");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        // test inserting
        AccessReviews reviewAccess = new AccessReviews();
        boolean insert1 = reviewAccess.insertReview(newReview1);
        boolean insert2 = reviewAccess.insertReview(newReview2);
        assertTrue(insert1);
        assertTrue(insert2);

        //numbers of review in Dota 2 should be 1
        assertEquals(reviewAccess.getReviewNumByGame("Dota2"), 1);

        List<Review> l = reviewAccess.getReviewsByGame("Dota2");
        assertEquals(l.size(), 1);
        assertEquals(l.get(0).getComment(), rev1);

        //inserting review for different games
        l = reviewAccess.getReviewsByGame("GTA");
        assertEquals(l.size(), 1);
        assertEquals(l.get(0).getComment(), rev2);

        //multiple reviews for same game
        try {
            newReview1 = new Review("good", "Dota2", "TEST");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        insert1 = reviewAccess.insertReview(newReview1);
        assertTrue(insert1);

        l = reviewAccess.getReviewsByGame("Dota2");

        assertEquals(l.size(), 2);
        assertEquals(l.get(1).getComment(), "good");

        //user-based testing
        l = reviewAccess.getReviewsByUser("TEST");
        assertEquals(l.size(), 1);
        assertEquals(l.get(0).getUserID(), "TEST");

        l = reviewAccess.getReviewsByUser("Guest");
        assertEquals(l.size(), 2);
        assertEquals(l.get(0).getUserID(), "Guest");
        assertEquals(l.get(0).getComment(), rev1);
        assertEquals(l.get(1).getComment(), rev2);

        //finding reviews by ID
        assertNotNull(newReview1);
        int id = newReview1.getReviewID();
        Review r = reviewAccess.getReviewById(id);
        assertTrue(r.equals(newReview1));


        //update reviews
        String updateComment = "good teammates";
        Review updateReview = null;
        try {
            updateReview = new Review(id, updateComment, "Dota2", "Guest");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        boolean update = reviewAccess.updateReview(updateReview);
        assertTrue(update);

        l = reviewAccess.getReviewsByGame("Dota2");
        assertEquals(l.size(), 2);
        assertEquals(l.get(0).getComment(), updateComment);


        //getting all reviews
        l = reviewAccess.getAllReviews();
        assertEquals(l.size(), 3);

        //check reviews 1 by 1
        Review next = reviewAccess.getSequential();
        assertEquals(next, updateReview);
        next = reviewAccess.getSequential();
        assertEquals(next, newReview2);

        //delete reviews
        boolean del = reviewAccess.deleteReview(updateReview);
        assertTrue(del);
        l = reviewAccess.getReviewsByGame("Dota2");
        assertEquals(l.size(), 1);

        reviewAccess.clear();
        l = reviewAccess.getAllReviews();
        assertEquals(l.size(), 0);

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
            newReview = new Review(" ", " ", " ");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        boolean insert = reviewAccess.insertReview(newReview);
        assertTrue(insert);

        reviewAccess.clear();
        r = reviewAccess.getSequential();

        assertNull(r);
    }


}

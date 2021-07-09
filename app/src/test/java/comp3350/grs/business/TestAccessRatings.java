package comp3350.grs.business;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.User;
import junit.framework.TestCase;

import org.junit.Test;

public class TestAccessRatings extends TestCase {
    @Test
    public void testTypical() {
        AccessUsers userAccess = new AccessUsers();
        User user = userAccess.getSequential();

        //create new "typical" ratings, for the same game
        Rating newGuestRating = new Rating(3.0, "Valheim", "Guest");
        Rating newUserRating = new Rating(5.0, "Valheim", user.getUserID());

        AccessRatings ratingAccess = new AccessRatings();
        boolean insert1 = ratingAccess.insertRating(newGuestRating);
        boolean insert2 = ratingAccess.insertRating(newUserRating);
        assertTrue(insert1);
        assertTrue(insert2);
        assertTrue((ratingAccess.getOverallRating("Valheim")) > 0);
        assertTrue(ratingAccess.getRatingsByUser(user.getUserID()).size() == 1); //the number of ratings by the user is one
        assertTrue(ratingAccess.getRating("Valheim", user.getUserID()).getRatingValue() == 5.0);

        Rating updated = new Rating(2.0, "Valheim", user.getUserID());
        ratingAccess.updateRating(updated);
        //the rating by user for Valheim should now be updated to 2.0 from 5.0
        assertTrue(ratingAccess.getRating("Valheim", user.getUserID()).getRatingValue() == 2.0);

        Rating sameRating = ratingAccess.getRating("Valheim", user.getUserID());
        assertTrue(sameRating != null);
        assertTrue(newUserRating.equals(sameRating));

        boolean delete1 = ratingAccess.deleteRating(newGuestRating);
        boolean delete2 = ratingAccess.deleteRating(newUserRating);
        assertTrue(delete1);
        assertTrue(delete2);
        Rating deleted = ratingAccess.getRating("Valheim", user.getUserID());
        assertNull(deleted); //rating of game "Valheim" by user is no longer in database
    }


    //to be implemented
    @Test
    public void testNull(){

    }

    @Test
    public void testEmpty(){

    }

    @Test
    public void testEdge(){

    }
}


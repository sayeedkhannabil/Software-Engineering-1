package comp3350.grs.business;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessStub;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessRatingsTest {
    AccessUsers userAccess;

    @Before
    public void before(){
        Services.closeDataAccess(); //if there was one open
        Services.createDataAccess(new DataAccessStub(Main.dbName));

        userAccess = new AccessUsers();
    }
    @Test
    public void testTypical() {
        User user = userAccess.getSequential();

        //create new "typical" ratings, for the same game
        Rating newGuestRating = null;
        try {
            newGuestRating = new Rating(3.0, "Valheim", "Guest");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        Rating newUserRating = null;
        try {
            newUserRating = new Rating(5.0, "Valheim", user.getUserID());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        AccessRatings ratingAccess = new AccessRatings();
        boolean insert1 = ratingAccess.insertRating(newGuestRating);
        boolean insert2 = ratingAccess.insertRating(newUserRating);
        assertTrue(insert1);
        assertTrue(insert2);
        assertTrue((ratingAccess.getOverallRating("Valheim")) > 0);
        assertTrue(ratingAccess.getRatingsByUser(user.getUserID()).size() == 1); //the number of ratings by the user is one
        assertTrue(ratingAccess.getRating("Valheim", user.getUserID()).getRatingValue() == 5.0);

        Rating updated = null;
        try {
            updated = new Rating(2.0, "Valheim", user.getUserID());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
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


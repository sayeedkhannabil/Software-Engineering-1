package comp3350.grs.tests.business;
import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;

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
        assert(ratingAccess.getRatingsByUser(user.getUserID()).size() == 1); //the number of ratings by the user is one
        assert(ratingAccess.getRating("Valheim", user.getUserID()).getRating() == 5.0);

        Rating updated = new Rating(2.0, "Valheim", user.getUserID());
        ratingAccess.updateRating(updated);
        //the rating by user for Valheim should now be updated to 2.0 from 5.0
        assert(ratingAccess.getRating("Valheim", user.getUserID()).getRating() == 2.0);

        Rating sameRating = ratingAccess.getRating("Valheim", user.getUserID());
        assert(sameRating != null);
        assert(newUserRating.equals(sameRating));

        boolean delete1 = ratingAccess.deleteRating(newGuestRating);
        boolean delete2 = ratingAccess.deleteRating(newUserRating);
        assertTrue(delete1);
        assertTrue(delete2);
        Rating deleted = ratingAccess.getRating("Valheim", user.getUserID());
        assert(deleted == null); //rating of game "Valheim" by user is no longer in database
    }
}

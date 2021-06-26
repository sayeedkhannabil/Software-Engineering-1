package comp3350.grs.tests.business;
import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.User;
import junit.framework.TestCase;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAccessRatings extends TestCase {
    @Test
    public void typicalInput() {
        //get a random user from the database
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

        boolean delete1 = ratingAccess.deleteRating(newGuestRating);
        boolean delete2 = ratingAccess.deleteRating(newUserRating);
        assertTrue(delete1);
        assertTrue(delete2);

        //will add more tests later
    }
}

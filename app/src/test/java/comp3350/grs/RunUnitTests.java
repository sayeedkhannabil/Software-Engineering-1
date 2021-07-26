package comp3350.grs;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.grs.business.AccessGamesTest;
import comp3350.grs.business.AccessRatingsTest;
import comp3350.grs.business.AccessRequestsTest;
import comp3350.grs.business.AccessReviewTest;
import comp3350.grs.business.AccessUsersTest;
import comp3350.grs.objects.GameTest;
import comp3350.grs.objects.GuestTest;
import comp3350.grs.objects.RatingTest;
import comp3350.grs.objects.RegisteredUserTest;
import comp3350.grs.objects.ReviewTest;
import comp3350.grs.persistence.DataAccessITest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessGamesTest.class, AccessUsersTest.class, AccessReviewTest.class, AccessRatingsTest.class,
        GameTest.class,GuestTest.class,RatingTest.class,
        RegisteredUserTest.class,ReviewTest.class,
        DataAccessITest.class, AccessRequestsTest.class
})
public class RunUnitTests {

}

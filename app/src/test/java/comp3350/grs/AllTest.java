package comp3350.grs;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessUsersTest;
import comp3350.grs.business.AccessGamesTest;
import comp3350.grs.objects.ReviewTest;
import comp3350.grs.objects.GameTest;
import comp3350.grs.objects.GuestTest;
import comp3350.grs.objects.RatingTest;
import comp3350.grs.objects.RegisteredUserTest;
import comp3350.grs.business.AccessRatingsTest;
import comp3350.grs.persistence.DataAccessITest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessGamesTest.class, AccessUsersTest.class,
        GameTest.class,GuestTest.class,RatingTest.class,
        RegisteredUserTest.class,ReviewTest.class,
        DataAccessITest.class
})
public class AllTest {

}

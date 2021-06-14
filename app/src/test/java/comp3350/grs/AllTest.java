package comp3350.grs;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.grs.objects.Feedback;
import comp3350.grs.persistence.DataAccessStubTest;
import comp3350.grs.objects.FeedbackTest;
import comp3350.grs.objects.ReviewTest;
import comp3350.grs.objects.GameTest;
import comp3350.grs.objects.GuestTest;
import comp3350.grs.objects.RatingTest;
import comp3350.grs.objects.RegisteredUserTest;



public class AllTest {
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        return suite;
    }

    private static void testObjects() {
        suite.addTestSuite(GameTest.class);
        suite.addTestSuite(GuestTest.class);
        suite.addTestSuite(RatingTest.class);
        suite.addTestSuite(ReviewTest.class);
        suite.addTestSuite(RegisteredUserTest.class);
        suite.addTestSuite(FeedbackTest.class);


    }

    private static void testBusiness() {
        suite.addTestSuite(DataAccessStubTest.class);
    }
}

package comp3350.grs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.grs.acceptance.DiscussionForumTest;
import comp3350.grs.acceptance.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DiscussionForumTest.class,
        GameInfoTest.class,
        RatingTest.class,
        RequestTest.class,
        ReviewTest.class,
        SearchTest.class,
        SortTest.class
})
public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Acceptance tests");
    }
}

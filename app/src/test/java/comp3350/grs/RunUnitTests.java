package comp3350.grs;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.grs.business.AccessGamesTest;
import comp3350.grs.business.AccessPostsTest;
import comp3350.grs.business.AccessRatingsTest;
import comp3350.grs.business.AccessReplysTest;
import comp3350.grs.business.AccessRequestsTest;
import comp3350.grs.business.AccessReviewsTest;
import comp3350.grs.business.AccessUsersTest;
import comp3350.grs.business.AccessVoteReplysTest;
import comp3350.grs.objects.DownvoteTest;
import comp3350.grs.objects.GameTest;
import comp3350.grs.objects.GuestTest;
import comp3350.grs.objects.PostTest;
import comp3350.grs.objects.RatingTest;
import comp3350.grs.objects.RegisteredUserTest;
import comp3350.grs.objects.ReplyTest;
import comp3350.grs.objects.RequestTest;
import comp3350.grs.objects.ReviewTest;
import comp3350.grs.objects.UpvoteTest;
import comp3350.grs.objects.VoteReplyTest;
import comp3350.grs.persistence.DataAccessITest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessGamesTest.class, AccessUsersTest.class, AccessReviewsTest.class
        , AccessRatingsTest.class,AccessRequestsTest.class,
        AccessVoteReplysTest.class, AccessPostsTest.class, AccessReplysTest.class,

        GameTest.class,GuestTest.class,RatingTest.class,
        RegisteredUserTest.class,ReviewTest.class,
        DownvoteTest.class, PostTest.class, ReplyTest.class,
        RequestTest.class, UpvoteTest.class, VoteReplyTest.class,

        DataAccessITest.class
})
public class RunUnitTests {

}

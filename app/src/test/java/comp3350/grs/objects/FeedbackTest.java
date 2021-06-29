package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;

public class FeedbackTest extends TestCase{
    private static Feedback f;

    @Test
    public static void testEmptyFeedback() {
        f = new Feedback();
        assert(f.getRating() == null);
        assert(f.getReview() == null);
    }

//    @Test
//    public static void testInvalidity() {todo
//        Rating r = new Rating(-4);
//        Review rev = new Review("rev");
//
//        f = new Feedback(r, rev);
//
//        assert (!f.validFeedback());
//
//    }
}

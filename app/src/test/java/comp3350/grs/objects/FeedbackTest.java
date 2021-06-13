package comp3350.grs.objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class FeedbackTest {
    @Test
    public void emptyFeedback() {
        Feedback f = new Feedback();
        assertNull(f.getRating());
        assertNull(f.getReview());
    }

    @Test
    public void invalidity() {
        Rating r = new Rating(-4);
        Review rev = new Review("rev");

        Feedback f = new Feedback(r, rev);

        assertFalse (f.validFeedback());

    }
}

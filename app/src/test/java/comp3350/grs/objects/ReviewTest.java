package comp3350.grs.objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReviewTest {
    @Test
    public void emptyReview() {
        Review r = new Review();
        assertNull(r.getComment());
    }

    @Test
    public void testReview() {
        Review r = new Review("This is a good game.");
        assert("This is a good game.".equals(r.getComment()));
    }
}

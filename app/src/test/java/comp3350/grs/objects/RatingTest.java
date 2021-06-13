package comp3350.grs.objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class RatingTest {
    @Test
    public void test() {
        Rating r = new Rating(3);
        assert(3 == r.getRating());
    }

    @Test
    public void wrongRating() {
        Rating r = new Rating(7);
        Rating n = new Rating(-1);
    }
}

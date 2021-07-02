package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

import static org.junit.Assert.*;

public class RatingTest extends TestCase {
    @Test
    public void test() {
        Rating r = null;
        try {
            r = new Rating(3);
            assert(3 == r.getRating());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
    }

    @Test
    public void wrongRating() {
        try {
            Rating r = new Rating(7);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
            assert true;
        }
        try {
            Rating n = new Rating(-1);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
            assert true;
        }
    }
}

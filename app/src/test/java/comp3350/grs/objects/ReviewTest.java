package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

import static org.junit.Assert.*;

public class ReviewTest extends TestCase {
    @Test
    public void emptyReview() {
        Review r = new Review();
        assertNull(r.getComment());
    }

    @Test
    public void testReview() {
        Review r = null;
        r = new Review("This is a good game.");
        assert("This is a good game.".equals(r.getComment()));
    }
}

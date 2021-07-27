package comp3350.grs.objects;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.grs.exceptions.IncorrectFormat;

public class PostTest {
    private Post post1, post2;

    @Before
    public void Before() {
        post1 = null;
        post2 = null;
    }

    @Test
    public void testTypical() {
        try {
            post1 = new Post("title", "content", "user");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        assertEquals(post1.getTitle(), "title");
        assertEquals(post1.getContent(), "content");
        assertEquals(post1.getUserID(), "user");

        try {
            post2 = new Post ("title", "content", "user");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        assertEquals(post1, post2);

        try {
            post2 = new Post(3, "title", "content", "user");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        assertEquals(post2.getID(), 3);
        assertEquals(post2.getTitle(), "title");
        assertEquals(post2.getContent(), "content");
        assertEquals(post2.getUserID(), "user");

    }

    @Test
    public void testNull() {
        post1 = new Post();
        assertFalse(post1.valid());

        try {
            post1 = new Post(null, "content", "user");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertFalse(post1.valid());
    }

    @Test
    public void testEdge() {
        try {
            post1 = new Post("title", " ", "user");
        } catch (IncorrectFormat incorrectFormat) {
            fail();
        }

        try {
            post1 = new Post("", " ", "user");
        } catch (IncorrectFormat incorrectFormat) {
            fail();
        }

        try {
            post1 = new Post("", " ", "");
        } catch (IncorrectFormat incorrectFormat) {
            fail();
        }
    }
}

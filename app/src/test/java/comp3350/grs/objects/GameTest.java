package comp3350.grs.objects;
import junit.framework.TestCase;

import org.junit.BeforeClass;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

import static org.junit.Assert.*;

public class GameTest extends TestCase {
    private ArrayList<String> genres = new ArrayList<>(Arrays.asList("Genre1", "Genre2", "Genre3"));
    private Game typicalGame = new Game("TypicalGame", "TypicalDeveloper",
            "TypicalDescription", 20.00, genres);;
    private Game typicalGameSimple = new Game("GameWithOnlyAName");;
    private Game nullGame = new Game();

    @Test
    public void testTypicalGame()
    {
        //typical test cases for the game with all four parameters (typicalGame)
        assertEquals("TypicalGame", typicalGame.getName());
        assertEquals("TypicalDeveloper", typicalGame.getDev());
        for(int i = 0; i < genres.size(); i++)
        {
            assertTrue(genres.get(i).equals(typicalGame.getGenres().get(i))); // all genres should match
        }
        assertEquals("TypicalDescription", typicalGame.getDescription());
        assertEquals(20.00, typicalGame.getPrice(), 0);
        assertTrue(typicalGame.validGame());
    }

    @Test
    public void testEdgeInput() {
        //test cases for which parameters for a Game object are still "valid" input, but not correct for our purposes
        ArrayList<String> genres1 = new ArrayList<>(); //empty
        Game edgeGame = new Game("", "", "", 0, genres1);
        Game edgeGame1 = new Game(" ", "dev", " ", 100.00, genres1);
        Game edgeGame2 = new Game("Valid Name", "Dev", "desc", -1.22, genres);

        //the object should not be instantiated with these inputs
        assertTrue(!edgeGame.validGame());
        assertTrue(!edgeGame1.validGame());
        assertTrue(!edgeGame2.validGame());
    }

    @Test
    public void testNull()
    {
        //test Games created with default constructor
        assertNull(nullGame.getDev());
        assertNull(nullGame.getDescription());

        //typical test cases for the game with one parameter (typicalGameSimple) -- other variables should return null
        assertEquals("GameWithOnlyAName", typicalGameSimple.getName());
        assertNull(typicalGameSimple.getDev());
        assertNull(typicalGameSimple.getDescription());
        assertEquals(-1, typicalGameSimple.getPrice(), 0);
    }

    @Test
    public void testEqualsSelf()
    {
        //games are equal if they have the same name (or both have a null field for a name), and are both Game objects
        assertEquals(true, nullGame.equals(nullGame));
        assertEquals(true, typicalGameSimple.equals(typicalGameSimple));
        assertEquals(true, typicalGame.equals(typicalGame));
    }

    @Test
    public void testEqualsOtherGame(){
        Game sameName = new Game("TypicalGame", "testDev",  "TestDesc", 0.0,genres);
        assertTrue(typicalGame.equals(sameName)); //because they have the same name

        assertEquals(false, nullGame.equals(typicalGameSimple));
        assertEquals(false, nullGame.equals(typicalGame));
        assertEquals(false, typicalGameSimple.equals(typicalGame));
    }
}

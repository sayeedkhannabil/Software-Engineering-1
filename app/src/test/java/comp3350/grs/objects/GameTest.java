package comp3350.grs.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

public class GameTest  {
    private static List<String> genres;
    private static Game typicalGame;
    private static Game typicalGameSimple;
    private static Game nullGame ;

    @BeforeClass
    public static void beforeClass(){
        genres = new ArrayList<>(Arrays.asList("Genre1", "Genre2", "Genre3"));
        try {
            typicalGame = new Game("TypicalGame", "TypicalDeveloper",
                    "TypicalDescription", 20.00, genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            typicalGameSimple = new Game("GameWithOnlyAName");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        nullGame = new Game();
    }

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
        assertTrue(typicalGame.valid());

        //test cases for game with only name parameter passed
        assertEquals("GameWithOnlyAName", typicalGameSimple.getName());
        assertNull(typicalGameSimple.getDescription());
        assertNull(typicalGameSimple.getDev());
        assertTrue(typicalGameSimple.getGenres().isEmpty());
        assertTrue(typicalGameSimple.getPrice() == 0);
        assertTrue(typicalGameSimple.valid());
    }

    @Test
    public void testEdgeInput() {
        //test cases for which parameters for a Game object are still "valid" input, but not correct for our purposes
        ArrayList<String> genres1 = new ArrayList<>(); //empty
        Game edgeGame = null;
        Game edgeGame1=null;
        Game edgeGame2=null;
        Game edgeGame3=null;


        try {
            edgeGame = new Game("", "", "", 0, genres1);
            fail("shoudln't create");
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }
        try {
            edgeGame1 = new Game(" ", "dev", " ", 100.00, genres1);
            fail("shoudln't create");
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);

        }
        try {
            edgeGame2 = new Game("Valid Name", "Dev", "desc", -1.22, genres);
            fail("shoudln't create");
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);

        }
        try {
            edgeGame3 = new Game("       ");
            fail("shoudln't create");
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);

        }


        try {
            edgeGame1 = new Game(" ", "dev", " ", 100.00, genres1);
            fail();
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }
        try {
            edgeGame2 = new Game("Valid Name", "Dev", "desc", -1.22, genres);
            fail();
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }
        try {
            edgeGame3 = new Game("       ");
            fail();
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }

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
        assertEquals(0.0, typicalGameSimple.getPrice(), 0);

        //test games passed null values
        Game null1 = null;
        try {
            null1 = new Game("",null,"description", 1.00, null);
            fail();
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }

        assertFalse(nullGame.valid());
        assertNull(nullGame.getName());
        assertNull(nullGame.getDev());
        assertNull(nullGame.getDescription());
        assertNull(nullGame.getGenres());
    }

    @Test
    public void testEqualsSelf()
    {
        //games are equal if they have the same name (or both have a null field for a name), and are both Game objects
        assertEquals(typicalGameSimple,typicalGameSimple);
        assertEquals(typicalGame,typicalGame);
    }

    @Test
    public void testEqualsOtherGame(){
        Game sameName = null;
        try {
            sameName = new Game("TypicalGame", "testDev",  "TestDesc", 0.0,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertEquals(sameName,typicalGame);
        assertNotEquals(nullGame, typicalGameSimple);
        assertNotEquals(nullGame, typicalGame);
        assertNotEquals(typicalGameSimple, typicalGame);
    }
}
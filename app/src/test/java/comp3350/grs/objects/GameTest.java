package comp3350.grs.objects;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameTest  {
    private static List<String> genres;
    private static Game typicalGame;
    private static Game typicalGameSimple;
    private static Game nullGame ;

    @BeforeClass
    public static void setClass(){
        genres = new ArrayList<>(Arrays.asList("Genre1", "Genre2", "Genre3"));
        typicalGame = new Game("TypicalGame", "TypicalDeveloper",
                "TypicalDescription", 20.00, genres);
        typicalGameSimple = new Game("GameWithOnlyAName");
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
        assertTrue(typicalGame.validGame());

        //test cases for game with only name parameter passed
        assertEquals("GameWithOnlyAName", typicalGameSimple.getName());
        assertNull(typicalGameSimple.getDescription());
        assertNull(typicalGameSimple.getDev());
        assertTrue(typicalGameSimple.getGenres().isEmpty());
        assertTrue(typicalGameSimple.getPrice() == 0);
        assertTrue(typicalGameSimple.validGame());
    }

    @Test
    public void testEdgeInput() {
        //test cases for which parameters for a Game object are still "valid" input, but not correct for our purposes
        ArrayList<String> genres1 = new ArrayList<>(); //empty
        Game edgeGame = new Game("", "", "", 0, genres1);
        Game edgeGame1 = new Game(" ", "dev", " ", 100.00, genres1);
        Game edgeGame2 = new Game("Valid Name", "Dev", "desc", -1.22, genres);
        Game edgeGame3 = new Game("       ");

        //the object should not be instantiated with these inputs
        assertTrue(!edgeGame.validGame());
        assertTrue(!edgeGame1.validGame());
        assertTrue(!edgeGame2.validGame());
        assertTrue(!edgeGame3.validGame());
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
        Game null1 = new Game("",null,"description", 1.00, null);
        assertFalse(null1.validGame()); //not a valid game
        assertNull(null1.getDev());
        assertNull(null1.getDescription());
        assertNull(null1.getGenres());

        assertFalse(nullGame.validGame());
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
        Game sameName = new Game("TypicalGame", "testDev",  "TestDesc", 0.0,genres);
        assertEquals(sameName,typicalGame);
        assertNotEquals(nullGame, typicalGameSimple);
        assertNotEquals(nullGame, typicalGame);
        assertNotEquals(typicalGameSimple, typicalGame);
    }
}
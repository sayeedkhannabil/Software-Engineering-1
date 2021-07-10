package comp3350.grs.business;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.objects.Game;

import static org.junit.Assert.*;

public class AccessGamesTest {

    private AccessGames gameAccess = new AccessGames();

    @Test
    public void testTypical()
    {

        List<String> genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");

        Game typicalGame = new Game("Game1", "Developer", "Description", 1.00, genres);
        gameAccess.insertGame(typicalGame);
        assertTrue(gameAccess.findGame("Game1") != null); //game is in db
        assertTrue(gameAccess.findGame("Game1").equals(typicalGame));

        //typicalGame = new Game("Game2", "Dev", "Desc", 2.00, genres);
        //gameAccess.updateGame(typicalGame); // commented out because error with the db

        Game typicalGameSimple = new Game("Game2");
        boolean inserted = gameAccess.insertGame(typicalGameSimple);
        assertTrue(inserted);
        assertTrue(gameAccess.findGame("Game2") != null);
        assertTrue(gameAccess.findGame("Game2").equals(typicalGameSimple));

        Game sequential = gameAccess.getSequential();
        assertTrue(sequential != null);

        boolean deleted = gameAccess.deleteGame(typicalGame);
        assertTrue(deleted);
        Game found = gameAccess.findGame("Game1");
        assertTrue(found == null);
    }

    @Test
    public void testInvalid()
    {
        Game noNameGame = new Game("    ");
        Game nullGame = null;

        boolean inserted = gameAccess.insertGame(noNameGame);
        boolean inserted2 = gameAccess.insertGame(nullGame);

        //should not have inserted an invalid game.
        assertFalse(inserted);
        assertFalse(inserted2);

        Game found = gameAccess.findGame("    ");
        assertNull(found);
        found = gameAccess.findGame(null);
        assertNull(found);
    }
}

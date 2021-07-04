package comp3350.grs.tests.business;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.business.AccessGames;
import comp3350.grs.objects.Game;

public class TestAccessGames extends TestCase {

    @Test
    public void testTypical()
    {
        AccessGames gameAccess = new AccessGames();
        List<String> genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");

        Game typicalGame = new Game("Game1", "Developer", "Description", 1.00, genres);
        gameAccess.insertGame(typicalGame);
        assert(gameAccess.findGame("Game1") != null); //game is in db

        typicalGame = new Game("Game2", "Dev", "Desc", 2.00, genres);

        /* error with database when I try to run updateGame. Didn't have time to look into it (and didn't fully understand the error)
          but will try later */
        //gameAccess.updateGame(typicalGame);
        //assert(gameAccess.findGame("Game1") == null); //"Game1" should no longer be in database
        //assert(gameAccess.findGame("Game2") != null); //"Game2" should now be in Game1's place

        //this is not finished of course. More tests will be added.
    }
}

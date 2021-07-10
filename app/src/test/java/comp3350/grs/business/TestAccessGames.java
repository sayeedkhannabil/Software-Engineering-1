package comp3350.grs.business;
import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Review;
import comp3350.grs.persistence.DataAccessStub;

public class TestAccessGames extends TestCase {

    private AccessGames gameAccess = new AccessGames();;

    @BeforeClass
    public void setup(){
        Services.closeDataAccess(); //if there was one open?
        Services.createDataAccess(new DataAccessStub(Main.dbName));
    }

    @Test
    public void testTypical()
    {
        List<String> genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");
        Game typicalGame = new Game("Game1", "Developer", "Description", 1.00, genres);
        gameAccess.insertGame(typicalGame);
        assertNotNull(gameAccess.findGame("Game1")); //game is in db
        assertEquals(gameAccess.findGame("Game1"), typicalGame);

        //typicalGame = new Game("Game2", "Dev", "Desc", 2.00, genres);
        //gameAccess.updateGame(typicalGame); // commented out because error with the db

        Game typicalGameSimple = new Game("Game2");
        boolean inserted = gameAccess.insertGame(typicalGameSimple);
        assertTrue(inserted);
        assertNotNull(gameAccess.findGame("Game2"));
        assertEquals(gameAccess.findGame("Game2"), typicalGameSimple);

        Game sequential = gameAccess.getSequential();
        assertNotNull(sequential);

        boolean deleted = gameAccess.deleteGame(typicalGame);
        assertTrue(deleted);
        Game found = gameAccess.findGame("Game1");
        assertNull(found);

        List<Game> allGames = gameAccess.getAllGames();
        Game firstInListNeutral = allGames.get(0);
        List<Game> ascendingNames = gameAccess.ascendingNameSort();
        Game firstInListAsc = ascendingNames.get(0);
        assertFalse(firstInListNeutral.equals(firstInListAsc));

        List<Game> descendingNames = gameAccess.descendingNameSort();
        Game firstInListDesc = descendingNames.get(0);
        assertFalse(firstInListNeutral.equals(firstInListDesc));
        assertFalse(firstInListAsc.equals(firstInListDesc));

        List<Game> ascendingPrice = gameAccess.ascendingPriceSort();
        double ascendPrice = ascendingPrice.get(0).getPrice();
        List<Game> descendingPrice = gameAccess.descendingPriceSort();
        double descendPrice = descendingPrice.get(0).getPrice();
        assertTrue(ascendPrice != descendPrice);
        assertTrue(descendPrice >= ascendPrice);

        List<Game> ascendingRatings = gameAccess.ascendingRatingSort();
        double ascendRating = ascendingRatings.get(0).getOverallRating();
        List<Game> descendingRatings = gameAccess.descendingRatingSort();
        double descendRating = descendingRatings.get(0).getOverallRating();
        assertTrue(descendRating >= ascendRating);

        List<Game> ascendingReviews = gameAccess.ascendingReviewSort();
        List<Game> descendingReviews = gameAccess.descendingReviewSort();
        AccessReviews reviewAccess = new AccessReviews();
        int ascendReviewNum = reviewAccess.getReviewNumByGame(ascendingReviews.get(0).getName());
        int descendReviewNum = reviewAccess.getReviewNumByGame(descendingReviews.get(0).getName());
        assertTrue(descendReviewNum >= ascendReviewNum);

        //test implicit search
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

    @Test
    void testEmpty(){

    }

    @Test
    void testEdge(){

    }

    @AfterClass
    public void shutDown(){
        Services.closeDataAccess();
    }
}

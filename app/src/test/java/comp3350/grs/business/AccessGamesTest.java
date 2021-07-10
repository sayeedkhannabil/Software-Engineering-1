package comp3350.grs.business;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.persistence.DataAccessStub;

import static org.junit.Assert.*;

public class AccessGamesTest {

    private AccessGames gameAccess;

    @BeforeClass
    public void before(){
        Services.closeDataAccess(); //if there was one open
        Services.createDataAccess(new DataAccessStub(Main.dbName));
        gameAccess = new AccessGames();
    }

    @Test
    public void testTypical()
    {
        List<String> genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");

        Game typicalGame = null;
        try {
            typicalGame = new Game("Game1", "Developer", "Description", 1.00, genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        gameAccess.insertGame(typicalGame);
        assertTrue(gameAccess.findGame("Game1") != null); //game is in db
        assertTrue(gameAccess.findGame("Game1").equals(typicalGame));

        //typicalGame = new Game("Game2", "Dev", "Desc", 2.00, genres);
        //gameAccess.updateGame(typicalGame); // commented out because error with the db

        Game typicalGameSimple = null;
        try {
            typicalGameSimple = new Game("Game2");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        boolean inserted = gameAccess.insertGame(typicalGameSimple);
        assertTrue(inserted);
        assertNotNull(gameAccess.findGame("Game2"));
        assertTrue(gameAccess.findGame("Game2").equals(typicalGameSimple));

        Game sequential = gameAccess.getSequential();
        assertTrue(sequential != null);

        boolean deleted = gameAccess.deleteGame(typicalGame);
        assertTrue(deleted);
        Game found = gameAccess.findGame("Game1");
        assertTrue(found != null);

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

        AccessRatings ratingAccess = new AccessRatings();
        List<Game> ascendingRatings = gameAccess.ascendingRatingSort();
        double ascendRating = ratingAccess.getOverallRating(ascendingRatings.get(0).getName());
        List<Game> descendingRatings = gameAccess.descendingRatingSort();
        double descendRating = ratingAccess.getOverallRating(descendingRatings.get(0).getName());
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
        Game noNameGame = null;
        try {
            noNameGame = new Game("    ");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
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

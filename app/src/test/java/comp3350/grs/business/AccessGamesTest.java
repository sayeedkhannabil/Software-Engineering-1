package comp3350.grs.business;
import org.junit.AfterClass;
import org.junit.Before;
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
    private AccessRatings ratingAccess;
    private AccessReviews reviewAccess;
    private List<String> genres;

    @Before
    public void before(){
        Services.closeDataAccess(); //if there was one open
        Services.createDataAccess(new DataAccessStub(Main.dbName));
        gameAccess = new AccessGames();
        ratingAccess = new AccessRatings();
        reviewAccess = new AccessReviews();

        genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");
    }

    @Test
    public void testTypical()
    {
        //typicalGame has all parameters (name, developer, description, price, genres)
        Game typicalGame = null;
        try {
            typicalGame = new Game("Game1", "Developer", "Description", 1.00, genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        gameAccess.insertGame(typicalGame);
        assertTrue(gameAccess.findGame("Game1") != null); //game is in db
        assertEquals(gameAccess.findGame("Game1"), typicalGame);
        int numGames = gameAccess.getAllGames().size();
        assertTrue(numGames >= 1);

        //typicalGameSimple has only the name parameter (no other info)
        Game typicalGameSimple = null;
        try {
            typicalGameSimple = new Game("Game2");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        boolean inserted = gameAccess.insertGame(typicalGameSimple);
        assertTrue(inserted);
        assertNotNull(gameAccess.findGame("Game2"));
        assertEquals(gameAccess.findGame("Game2"),typicalGameSimple);
        numGames = gameAccess.getAllGames().size();
        assertTrue(numGames >= 2);

        //update typicalGame
        try {
            typicalGameSimple = new Game("Game2", "Dev", "Desc", 2.00, genres);
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }

        boolean updated = gameAccess.updateGame(typicalGameSimple);
        assertTrue(updated);

        Game found = gameAccess.findGame("Game2");
        assertTrue(found != null);
        assertEquals(found,typicalGameSimple);

        //test that typicalGameSimple now has all parameters
        assertEquals("Dev", typicalGameSimple.getDev());
        assertEquals("Desc", typicalGameSimple.getDescription());
        assertEquals(2.00, typicalGameSimple.getPrice(), 0);
        assertNotNull(typicalGameSimple.getGenres());

        Game sequential = gameAccess.getSequential();
        assertTrue(sequential != null);

        boolean deleted = gameAccess.deleteGame(typicalGame);
        assertTrue(deleted);
        found = gameAccess.findGame("Game1");
        assertTrue(found == null);
        int numGamesAfterDel = gameAccess.getAllGames().size();
        assertTrue(numGames - 1 == numGamesAfterDel);

        //put typicalGame back
        gameAccess.insertGame(typicalGame);

        //List<Game> allGames = gameAccess.getAllGames();
        //Game firstInListNeutral = allGames.get(0);
        List<Game> ascendingNames = gameAccess.ascendingNameSort();
        Game firstInListAsc = ascendingNames.get(0);

        List<Game> descendingNames = gameAccess.descendingNameSort();
        Game firstInListDesc = descendingNames.get(0);
        assertNotEquals(firstInListAsc, firstInListDesc);

        List<Game> ascendingPrice = gameAccess.ascendingPriceSort();
        double ascendPrice = ascendingPrice.get(0).getPrice();
        List<Game> descendingPrice = gameAccess.descendingPriceSort();
        double descendPrice = descendingPrice.get(0).getPrice();
        assertTrue(descendPrice >= ascendPrice);

        List<Game> ascendingRatings = gameAccess.ascendingRatingSort();
        double ascendRating = ratingAccess.getRatingNumByGame(ascendingRatings.get(0).getName());
        System.out.println(ascendRating);
        List<Game> descendingRatings = gameAccess.descendingRatingSort();
        double descendRating = ratingAccess.getRatingNumByGame(descendingRatings.get(0).getName());
        System.out.println(descendRating);
        assertTrue(descendRating >= ascendRating);

        List<Game> ascendingReviews = gameAccess.ascendingReviewSort();
        List<Game> descendingReviews = gameAccess.descendingReviewSort();
        int ascendReviewNum = reviewAccess.getReviewNumByGame(ascendingReviews.get(0).getName());
        int descendReviewNum = reviewAccess.getReviewNumByGame(descendingReviews.get(0).getName());
        assertTrue(descendReviewNum >= ascendReviewNum);

        //test implicit search --- this will be improved once the stub implicit search is improved (soon)
        List<Game> implicit1 = gameAccess.getGamesByNameImplicit("Game");
        assertTrue(implicit1.size() >= 2);

        assertTrue(implicit1.contains(typicalGame));
        assertTrue(implicit1.contains(typicalGameSimple));

        List<Game> implicit2 = gameAccess.getGamesByNameImplicit("Ga*");
        assertTrue(implicit2.size() >= 2);
        assertTrue(implicit2.contains(typicalGame));
        assertTrue(implicit2.contains(typicalGameSimple));

        List<Game> implicit3 = gameAccess.getGamesByNameImplicit("non-game");
        assertFalse(implicit3.contains(typicalGame));
        assertFalse(implicit3.contains(typicalGameSimple));
    }

    @Test
    public void testInvalid()
    {
        Game nullGame = new Game(); //all parameters will be null, price will be -1

        Game noNameGame = null;
        try {
            noNameGame = new Game("    ");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        boolean inserted = gameAccess.insertGame(noNameGame);
        boolean inserted2 = gameAccess.insertGame(nullGame);

        //should not have inserted an invalid game
        assertFalse(inserted);
        assertFalse(inserted2);

        Game found = gameAccess.findGame(null);
        assertNull(found);

        List<Game> implicitGames = gameAccess.getGamesByNameImplicit("");
        assertTrue(implicitGames.isEmpty());

        //should not be able to insert two games with the same name (two equal games)
        Game sameName1 = null;
        Game sameName2 = null;

        try{
            sameName1 = new Game("sameName");
            sameName2 = new Game("sameName");
        }
        catch(IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }

        gameAccess.insertGame(sameName1);
        inserted = gameAccess.insertGame(sameName2);
        assertFalse(inserted);
    }

    @Test
    public void testEmpty(){
        gameAccess.clear();

        List<Game> allGames = gameAccess.getAllGames();
        assertTrue(allGames.isEmpty());
        assertEquals(0, allGames.size());
    }

    @AfterClass
    public static void shutDown(){
        Services.closeDataAccess();
    }
}

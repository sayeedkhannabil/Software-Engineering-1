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
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Review;
import comp3350.grs.persistence.DataAccessStub;

import static org.junit.Assert.*;

public class AccessGamesTest {

    private static AccessGames gameAccess;
    private static AccessRatings ratingAccess;
    private static AccessReviews reviewAccess;
    private List<String> genres;
    private Game typicalGame, typicalGameSimple, nullGame, noNameGame, found;
    private Rating rating1, rating2, rating3;
    private Review review1, review2, review3;
    private boolean insert, update, delete;
    private int numGames;

    @BeforeClass
    public static void beforeClass(){
        Services.closeDataAccess(); //if there was one open
        Services.createDataAccess(new DataAccessStub(Main.dbName));
        gameAccess = new AccessGames();
        ratingAccess = new AccessRatings();
        reviewAccess = new AccessReviews();
    }

    @Before
    public void before() {
        genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");

        typicalGame = null;
        typicalGameSimple = null;
        nullGame = null;
        noNameGame = null;
        found = null;
        rating1 = null;
        rating2 = null;
        rating3 = null;
        review1 = null;
        review2 = null;
        review3 = null;
        insert = false;
        update = false;
        delete = false;
        numGames = 0;
    }

    @Test
    public void testTypical()
    {
        //typicalGame has all parameters (name, developer, description, price, genres)
        try {
            typicalGame = new Game("Game1", "Developer", "Description", 1.00, genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        gameAccess.insertGame(typicalGame);
        assertTrue(gameAccess.findGame("Game1") != null); //game is in db
        assertEquals(gameAccess.findGame("Game1"), typicalGame);
        numGames = gameAccess.getAllGames().size();
        assertTrue(numGames >= 1);

        //typicalGameSimple has only the name parameter (no other info)
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

        update = gameAccess.updateGame(typicalGameSimple);
        assertTrue(update);

        found = gameAccess.findGame("Game2");
        assertTrue(found != null);
        assertEquals(found,typicalGameSimple);

        //test that typicalGameSimple now has all parameters
        assertEquals("Dev", typicalGameSimple.getDev());
        assertEquals("Desc", typicalGameSimple.getDescription());
        assertEquals(2.00, typicalGameSimple.getPrice(), 0);
        assertNotNull(typicalGameSimple.getGenres());

        delete = gameAccess.deleteGame(typicalGame);
        assertTrue(delete);
        found = gameAccess.findGame("Game1");
        assertTrue(found == null);
        int numGamesAfterDel = gameAccess.getAllGames().size();
        assertTrue(numGames - 1 == numGamesAfterDel);

        //put typicalGame back
        gameAccess.insertGame(typicalGame);

        assertTrue(gameAccess.getAllGames().size() >= 3);
        Game nextGame = gameAccess.getSequential();
        assertNotNull(nextGame);
        nextGame = gameAccess.getSequential();
        assertNotNull(nextGame);
        nextGame = gameAccess.getSequential();
        assertNotNull(nextGame);

        //add reviews and ratings to test sorting
        String gameName = gameAccess.getSequential().getName(); //get a game from the list

        try{
            rating1 = new Rating(3.0, gameName, "Registered1");
        }catch(IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        ratingAccess.insertRating(rating1);

        try{
            rating2 = new Rating(5.0, typicalGame.getName(), "Guest");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        ratingAccess.insertRating(rating2);

        try{
            rating3 = new Rating(4.0, typicalGame.getName(), "Registered1");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        ratingAccess.insertRating(rating3);

        //reviews
        try{
            review1 = new Review("Game is alright.", gameName, "Registered1");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        reviewAccess.insertReview(review1);

        try{
            review2 = new Review("Great game.", typicalGame.getName(), "Guest");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        reviewAccess.insertReview(review2);

        try{
            review3 = new Review("Fairly good.", typicalGame.getName(), "Registered1");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        reviewAccess.insertReview(review3);


        List<Game> ascendingNames = gameAccess.ascendingNameSort();
        Game firstInListAsc = ascendingNames.get(0);

        List<Game> descendingNames = gameAccess.descendingNameSort();
        Game firstInListDesc = descendingNames.get(0);
        assertNotEquals(firstInListAsc, firstInListDesc);
        assertNotEquals(firstInListAsc.getName(), firstInListDesc.getName());
        assertTrue(firstInListAsc.getName().charAt(0) <= firstInListDesc.getName().charAt(0));

        List<Game> ascendingPrice = gameAccess.ascendingPriceSort();
        double ascendPrice = ascendingPrice.get(0).getPrice();
        List<Game> descendingPrice = gameAccess.descendingPriceSort();
        double descendPrice = descendingPrice.get(0).getPrice();
        assertTrue(descendPrice >= ascendPrice);

        List<Game> ascendingRatings = gameAccess.ascendingRatingSort();
        double ascendRating = ratingAccess.getRatingNumByGame(ascendingRatings.get(0).getName());
        List<Game> descendingRatings = gameAccess.descendingRatingSort();
        double descendRating = ratingAccess.getRatingNumByGame(descendingRatings.get(0).getName());
        assertTrue(descendRating > ascendRating);

        List<Game> ascendingReviews = gameAccess.ascendingReviewSort();
        List<Game> descendingReviews = gameAccess.descendingReviewSort();
        int ascendReviewNum = reviewAccess.getReviewNumByGame(ascendingReviews.get(0).getName());
        int descendReviewNum = reviewAccess.getReviewNumByGame(descendingReviews.get(0).getName());
        assertTrue(descendReviewNum > ascendReviewNum);

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
        nullGame = new Game(); //all parameters will be null, price will be -1
        try {
            noNameGame = new Game("    ");
            fail("shouldn't create");
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }

        insert = gameAccess.insertGame(noNameGame);
        boolean insert2 = gameAccess.insertGame(nullGame);

        //should not have inserted an invalid game
        assertFalse(insert);
        assertFalse(insert2);

        found = gameAccess.findGame(null);
        assertNull(found);

        List<Game> implicitGames = gameAccess.getGamesByNameImplicit("");
        assertTrue(implicitGames.isEmpty());

        //should not be able to insert two games with the same name (two equal games)
        typicalGameSimple = null;
        Game sameName = null;

        try{
            typicalGameSimple = new Game("sameName");
            sameName = new Game("sameName");
        }
        catch(IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }

        gameAccess.insertGame(typicalGameSimple);
        insert = gameAccess.insertGame(sameName);
        assertFalse(insert);
    }

    @Test
    public void testEmpty(){
        gameAccess.clear();

        List<Game> allGames = gameAccess.getAllGames();
        assertTrue(allGames.isEmpty());
        assertEquals(0, allGames.size());
        Game sequential = gameAccess.getSequential();
        assertNull(sequential);
    }

    @AfterClass
    public static void afterClass(){
        Services.closeDataAccess();
    }
}

package comp3350.grs.business;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessStub;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccessRatingsTest {
    private static AccessUsers userAccess;
    private static AccessRatings ratingAccess;
    private String gameName, userID, guestID;
    private User user;
    private Rating newGuestRating, newRegisterRating, nullRating, updated, deleted;
    private boolean insert, update, delete;

    @BeforeClass
    public static void beforeClass(){
        Services.closeDataAccess(); //if there was one open
        Services.createDataAccess(new DataAccessStub(Main.dbName));

        userAccess = new AccessUsers();
        ratingAccess = new AccessRatings();
    }

    @Before
    public void before(){
        gameName = null;
        userID = "RegisteredUser";
        guestID = "Guest";
        newGuestRating = null;
        newRegisterRating = null;
        nullRating = null;
        updated = null;
        deleted = null;
        insert = false;
        update = false;
        delete = false;

        try{
            user = new RegisteredUser(userID);
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
    }

    @Test
    public void testTypical() {
        gameName = "Valheim";
        userAccess.insertUser(user);

        try {
            newGuestRating = new Rating(3.0, gameName, guestID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try {
            newRegisterRating = new Rating(5.0, gameName, userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        insert = ratingAccess.insertRating(newGuestRating);
        boolean insert2 = ratingAccess.insertRating(newRegisterRating);
        assertTrue(insert);
        assertTrue(insert2);
        assertTrue((ratingAccess.getOverallRating(gameName)) > 0);
        assertTrue(ratingAccess.getRatingNumByGame(gameName) > 0);
        List<Rating> gameRatings = ratingAccess.getRatingsByGame(gameName);
        assertTrue(gameRatings.size() >= 2);
        assertTrue(ratingAccess.getRatingsByUser(userID).size() == 1); //the number of ratings by the user is one
        assertEquals(1, ratingAccess.getRatingNumByUser(userID));
        assertTrue(ratingAccess.getRating(gameName, userID).getRatingValue() == 5.0);

        try {
            updated = new Rating(2.0, gameName, userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        update = ratingAccess.updateRating(updated);
        assertTrue(update);
        //the rating by user for Valheim should now be updated to 2.0 from 5.0
        assertTrue(ratingAccess.getRating(gameName, userID).getRatingValue() == 2.0);

        Rating sameRating = ratingAccess.getRating(gameName, userID);
        assertTrue(sameRating != null);
        assertEquals(newRegisterRating, sameRating);

        delete = ratingAccess.deleteRating(newGuestRating);
        boolean delete2 = ratingAccess.deleteRating(newRegisterRating);
        assertTrue(delete);
        assertTrue(delete2);
        deleted = ratingAccess.getRating(gameName, userID);
        assertNull(deleted); //rating of game "Valheim" by user is no longer in database
    }


    //to be implemented
    @Test
    public void testInvalid(){
        gameName = "Valheim";
        int initialNumRatings = ratingAccess.getRatingNumByGame(gameName);

        try{
            nullRating = new Rating(-1,gameName,userID);
        }
        catch(IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        insert = ratingAccess.insertRating(nullRating);
        assertFalse(insert);
        assertNull(nullRating);

        try{
            nullRating = new Rating(0, gameName,userID);
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        insert = ratingAccess.insertRating(nullRating);
        assertFalse(insert);
        assertNull(nullRating);
        assertEquals(0, ratingAccess.getRatingNumByUser(userID));
        assertEquals(initialNumRatings, ratingAccess.getRatingNumByGame(gameName)); //no added ratings

        //create and insert valid rating to then update with an invalid rating
        try{
            newRegisterRating = new Rating(2, gameName,userID);
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        insert = ratingAccess.insertRating(newRegisterRating);
        assertTrue(insert);

        try{
            updated = new Rating(6,gameName,userID);
        }
        catch(IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        update = ratingAccess.updateRating(updated);
        assertFalse(update);
    }

    @Test
    public void testEmpty(){
        gameName = "Non-Existent Game";

        try{
            newGuestRating = new Rating(2, gameName, guestID);
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        insert = ratingAccess.insertRating(newGuestRating);
        assertFalse(insert);

        ratingAccess.clear();
        assertTrue(ratingAccess.getAllRatings().isEmpty());
    }

    @Test
    public void testEdge(){

    }

    @AfterClass
    public static void shutDown(){
        Services.closeDataAccess();
    }
}


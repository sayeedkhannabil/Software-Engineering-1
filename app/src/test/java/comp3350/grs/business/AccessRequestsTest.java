package comp3350.grs.business;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.Duplicate;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Request;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessStub;
import static org.junit.Assert.*;

public class AccessRequestsTest {
    private static AccessUsers userAccess;
    private static AccessRequests requestsAccess;
    private User user;
    private String gameName, userID, guestID;
    private Request newRequest, newRequest2, inserted, updated, deleted;
    private boolean insert, update, delete, exists;

    @BeforeClass
    public static void beforeClass(){
        Services.createDataAccess(new DataAccessStub(Main.testDbName));
        userAccess = new AccessUsers();
        requestsAccess = new AccessRequests();
    }

    @AfterClass
    public static void afterClass(){
        Services.closeDataAccess();
    }

    @Before
    public void before(){
        user = null;
        gameName = null;
        userID = null;
        guestID = "Guest";
        newRequest = null;
        newRequest2 = null;
        inserted = null;
        updated = null;
        deleted = null;
        insert = false;
        update = false;
        delete = false;
        exists = false;
    }

    @Test
    public void testTypical(){
        gameName = "GameThatDoesNotExist";
        user = userAccess.getSequential(); //get a user that exists in the database
        userID = user.getUserID();

        //make new request
        try {
            newRequest = new Request(gameName, userID);
        }
        catch(IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }

        assertFalse(exists); // game should not exist in database

        try {
            insert = requestsAccess.insertRequest(newRequest);
        }
        catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }

        //should insert correctly
        assertTrue(insert);
        List<Request> allRequests = requestsAccess.getAllRequests();
        exists = allRequests.contains(newRequest);
        assertTrue(exists);
        assertTrue(allRequests.size() >= 1);

        //get requests by requested game name
        allRequests = requestsAccess.getRequestsByGame(gameName);
        exists = allRequests.contains(newRequest);
        assertTrue(exists);
        assertEquals(1, allRequests.size());

        //get requests by user who requested
        allRequests = requestsAccess.getRequestsByUser(userID);
        exists = allRequests.contains(newRequest);
        assertTrue(exists);
        assertTrue(allRequests.size() >= 1);

        //should return the Request inserted above
        inserted = requestsAccess.getRequest(gameName, userID);
        assertNotNull(inserted);
        assertEquals(inserted, newRequest);

        //delete the Request we inserted
        allRequests = requestsAccess.getAllRequests();
        int numRequestsBeforeDelete = allRequests.size();
        delete = requestsAccess.deleteRequest(newRequest);
        assertTrue(delete);
        allRequests = requestsAccess.getAllRequests();
        assertFalse(allRequests.contains(newRequest));
        assertEquals(numRequestsBeforeDelete - 1, allRequests.size());

        //add back in for further testing
        try{
            requestsAccess.insertRequest(newRequest);
        }
        catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }

        //insert a Request from another user for the same game
        user = userAccess.getSequential();
        userID = user.getUserID();
        try{
            newRequest2 = new Request(gameName, userID);
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(newRequest);
        assertEquals(gameName, newRequest2.getGameName());
        assertEquals(userID, newRequest2.getUserID());

        try{
            insert = requestsAccess.insertRequest(newRequest2);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertTrue(insert);
        allRequests = requestsAccess.getAllRequests();
        assertTrue(allRequests.size() >= 2);
        assertTrue(allRequests.contains(newRequest2));
        allRequests = requestsAccess.getRequestsByGame(gameName);
        assertEquals(2, allRequests.size());
        assertTrue(allRequests.contains(newRequest2));

        //insert a Request from the same user (as above) for another game
        gameName = "AnotherGameThatDoesNotExist";
        try{
            newRequest = new Request(gameName, userID);
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }

        try{
            insert = requestsAccess.insertRequest(newRequest);
        }
        catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }

        assertNotNull(newRequest);
        allRequests = requestsAccess.getAllRequests();
        assertTrue(allRequests.size() >= 3);
        assertTrue(allRequests.contains(newRequest));


        //get the list of game names by how many times the game was requested
        List<String> twoReqs = requestsAccess.getGamesByRequestNum(2);
        List<String> oneReq = requestsAccess.getGamesByRequestNum(1);
        assertFalse(twoReqs.isEmpty());
        assertFalse(oneReq.isEmpty());
        assertTrue(twoReqs.contains(newRequest2.getGameName()));
    }

    @Test
    public void testInvalid(){
        //the same user cannot request the same game multiple times.
        try{
            user = new RegisteredUser("NewUser1", "pass1");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        insert = userAccess.insertUser(user);
        assertTrue(insert);
        assertTrue(userAccess.getAllUsers().contains(user));
        userID = user.getUserID();
        gameName = "GameThatDoesNotExist2";
        try{
            newRequest = new Request(gameName, userID);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(newRequest);
        try {
            insert = requestsAccess.insertRequest(newRequest);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertTrue(insert);
        assertTrue(requestsAccess.getAllRequests().contains(newRequest));

        //now try to insert the same request twice (same user requesting same game)
        try{
            insert = requestsAccess.insertRequest(newRequest);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertFalse(insert);
        assertEquals(1, requestsAccess.getRequestsByGame(gameName).size()); //should only be one request made for this game

        //a user who is not registered, and who is not a Guest cannot request a game
        userID = "UserThatDoesNotExist";
        try{
            newRequest2 = new Request(gameName, userID);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(newRequest2);
        assertEquals("UserThatDoesNotExist", newRequest2.getUserID());

        try{
            insert = requestsAccess.insertRequest(newRequest2);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertFalse(insert);
        assertFalse(requestsAccess.getAllRequests().contains(newRequest2));
        //still should only be one request for the game
        assertEquals(1, requestsAccess.getRequestsByGame(gameName).size());

        //a game that already exists in the system cannot be requested
        gameName = "Valheim"; //a game in the database
        userID = user.getUserID();
        try{
            newRequest2 = new Request(gameName, userID);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        try{
            insert = requestsAccess.insertRequest(newRequest2);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertFalse(insert);
        assertEquals(0, requestsAccess.getRequestsByGame(gameName).size());
        assertFalse(requestsAccess.getAllRequests().contains(newRequest2));

        //guests can't request the same game multiple times
        gameName = "NotInSystem";
        userID = "Guest";
        try{
            newRequest = new Request(gameName,userID);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        try{
            insert = requestsAccess.insertRequest(newRequest);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertTrue(insert);
        assertTrue(requestsAccess.getAllRequests().contains(newRequest));
        assertEquals(1, requestsAccess.getRequestsByGame(gameName).size());

        //can't insert the same request again (even if from a different guest)
        try{
            newRequest2 = new Request(gameName, userID);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        try{
            insert = requestsAccess.insertRequest(newRequest2);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertFalse(insert);
        assertEquals(1, requestsAccess.getRequestsByGame(gameName).size());
    }

    @Test
    public void testEmpty(){
        userID = "Guest";
        gameName = "game";
        requestsAccess.clear();

        try{
            newRequest = new Request(gameName, userID);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }

        try{
            insert = requestsAccess.insertRequest(newRequest);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertTrue(insert);
        assertEquals(1, requestsAccess.getRequestsByGame(gameName).size());

        delete = requestsAccess.deleteRequest(newRequest);
        assertTrue(delete);
        assertTrue(requestsAccess.getRequestsByGame(gameName).isEmpty());
        assertNull(requestsAccess.getRequest(gameName,userID));
        assertTrue(requestsAccess.getRequestsByUser(userID).isEmpty());
        assertTrue(requestsAccess.getAllRequests().isEmpty());

        try{
            insert = requestsAccess.insertRequest(newRequest);
        }catch (Duplicate d){
            d.printStackTrace();
        }
        assertTrue(insert);
        assertEquals(1, requestsAccess.getAllRequests().size());

        requestsAccess.clear();
        assertTrue(requestsAccess.getAllRequests().isEmpty());
        assertTrue(requestsAccess.getRequestsByGame(gameName).isEmpty());
    }
}

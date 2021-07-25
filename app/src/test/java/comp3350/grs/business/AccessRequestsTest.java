package comp3350.grs.business;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.Duplicate;
import comp3350.grs.exceptions.IncorrectFormat;
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
        Services.closeDataAccess(); //if there was one open
        Services.createDataAccess(new DataAccessStub(Main.dbName));

        userAccess = new AccessUsers();
        requestsAccess = new AccessRequests();
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

        //check if game exists (shouldn't)
        try {
            exists = requestsAccess.checkGameExists(newRequest);
        }
        catch (Duplicate duplicate){
            duplicate.printStackTrace();
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

    }

    @Test
    public void testEmpty(){

    }
}

package comp3350.grs.objects;
import org.junit.Test;
import static org.junit.Assert.*;
import comp3350.grs.exceptions.IncorrectFormat;

public class RequestTest {

    @Test
    public void testTypical(){
        Request newRequest = null;
        try{
            newRequest = new Request("Game", "User");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(newRequest);
        assertEquals("Game", newRequest.getGameName());
        assertEquals("User", newRequest.getUserID());
        assertTrue(newRequest.valid());
        assertEquals("Game Name: "+newRequest.getGameName()+",UserID: "+newRequest.getUserID(), newRequest.toString());

        //request has same game, different user
        Request newRequest2 = null;
        try{
            newRequest2 = new Request("Game", "User2");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(newRequest2);
        assertEquals("User2", newRequest2.getUserID());
        assertFalse(newRequest.equals(newRequest2));
        assertFalse(newRequest2.equals(newRequest));

        //request has same user, different game
        try{
            newRequest2 = new Request("Game2", "User");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(newRequest2);
        assertEquals("Game2", newRequest2.getGameName());
        assertEquals("User", newRequest2.getUserID());
        assertEquals(newRequest.getUserID(), newRequest2.getUserID()); //same userID for both requests
        assertFalse(newRequest.equals(newRequest2));
        assertFalse(newRequest2.equals(newRequest));
    }

    @Test
    public void testEdge(){
        Request edgeRequest = null;
        try{
            edgeRequest = new Request("        game", "user");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(edgeRequest);
        assertTrue(edgeRequest.valid());
        assertEquals("        game", edgeRequest.getGameName());
        assertEquals("user", edgeRequest.getUserID());

        try{
            edgeRequest = new Request(" .    ", "Guest");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(edgeRequest);
        assertTrue(edgeRequest.valid());
        assertEquals(" .    ", edgeRequest.getGameName());
        assertEquals("Guest", edgeRequest.getUserID());
    }

    @Test
    public void testNull(){
        Request nullRequest = new Request();
        assertNotNull(nullRequest); //object itself not null
        assertFalse(nullRequest.valid());
        assertNull(nullRequest.getUserID());
        assertNull(nullRequest.getGameName());

        try{
            nullRequest = new Request(null, "User");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(nullRequest);
        assertFalse(nullRequest.valid());
        assertNull(nullRequest.getGameName());
        assertNull(nullRequest.getUserID());

        try{
            nullRequest = new Request("Game", null);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(nullRequest);
        assertFalse(nullRequest.valid());
        assertEquals("Game", nullRequest.getGameName());
        assertNull(nullRequest.getUserID());

        //gameName throws exception, so object is null
        Request invalidReq = null;
        try{
            invalidReq = new Request(" ", "User");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNull(invalidReq);
    }
}

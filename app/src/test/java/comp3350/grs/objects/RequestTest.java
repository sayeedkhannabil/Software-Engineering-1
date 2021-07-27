package comp3350.grs.objects;

import org.junit.Before;
import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

import static org.junit.Assert.*;

public class RequestTest {
    private Request request1,request2,request3;

    @Before
    public void Before(){
        request1=null;
        request2=null;
        request3=null;
    }

    @Test
    public void testTypical(){
        try {
            request1=new Request("game1","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertTrue(request1.valid());
        assertEquals("game1",request1.getGameName());
        assertEquals("user1",request1.getUserID());
        try {
            request2=new Request("game1","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertEquals(request1,request2);
        try {
            request2=new Request("game2","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertNotEquals(request1,request2);
        try {
            request2=new Request("game1","user2");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertNotEquals(request1,request2);

    }

    @Test
    public void testNull(){
        request1=new Request();
        assertFalse(request1.valid());
        assertNull(request1.getGameName());
        assertNull(request1.getUserID());
        request2=new Request();
        assertNotEquals(request1,request2);
        try {
            request1=new Request(null,"user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertFalse(request1.valid());
        try {
            request1=new Request("game1",null);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertFalse(request1.valid());
    }

    @Test
    public void testInvalid(){
        try {
            request1=new Request(" ",null);
            fail();
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }
        try {
            request1=new Request("",null);
            fail();
        } catch (IncorrectFormat incorrectFormat) {
            assertTrue(true);
        }
    }

    @Test
    public void testEdge(){
        try {
            request1=new Request("game1","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        assertNotEquals(null,request1);
        String object="";
        assertNotEquals(object,request1);
    }

}
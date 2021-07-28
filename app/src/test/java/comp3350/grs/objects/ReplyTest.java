package comp3350.grs.objects;

import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReplyTest {

    @Test
    public void testTypicalReply(){
        Reply testReply=null;
        System.out.println("\nStarting testReply");
        try {
            testReply = new Reply(1000,"reply1","user1",100);
            assertTrue(testReply.getPostID() == 100);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            testReply = new Reply("reply2","user2",200);
            assertTrue(testReply.getPostID() == 200);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try{
            testReply=new Reply("reply2","user2",100);
            assertEquals(-1,testReply.getID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
    }

    @Test
    public void testEqualsReply(){
        Reply reply1=null;
        Reply reply2=null;
        try{
            reply1=new Reply(1000,"reply2","user2",100);
            reply2=new Reply(1001,"reply3","user2",100);
            assertFalse(reply1.equals(reply2));

        }catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try{
            reply1=new Reply(1000,"reply2","user2",100);
            reply2=new Reply(1000,"reply2","user2",100);
            assertTrue(reply1.equals(reply2));
        }catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
    }

    @Test
    public void testNullReply(){
        Reply nullReply=null;

        nullReply=new Reply();
        assertEquals(-1,nullReply.getPostID());
    }

}

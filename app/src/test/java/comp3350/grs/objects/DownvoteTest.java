package comp3350.grs.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DownvoteTest {
    private VoteI voteI1,voteI2;

    @Before
    public void Before(){
        voteI1=null;
        voteI2=null;
    }

    @Test
    public void testTypical(){
        voteI1=new Downvote("user1");
        assertTrue(voteI1.valid());
        assertEquals("user1",voteI1.getUserID());
        assertEquals(Vote.DOWN_VALUE,voteI1.getValue());
        voteI2=Vote.createVote("user1",Vote.DOWN_VALUE);
        assertEquals(voteI1,voteI2);
        voteI2=new Downvote("user2");
        assertNotEquals(voteI1,voteI2);
        voteI2=new Upvote("user1");
        assertNotEquals(voteI1,voteI2);
    }

    @Test
    public void testEdge(){
        voteI1=new Downvote();
        assertFalse(voteI1.valid());
        assertNull(voteI1.getUserID());
        assertEquals(0,voteI1.getValue());
        voteI2=new Downvote();
        assertNotEquals(voteI1,voteI2);
        voteI1=Vote.createVote("user1",-100);
        assertNull(voteI1);
    }
}
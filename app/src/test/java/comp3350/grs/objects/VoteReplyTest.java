package comp3350.grs.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VoteReplyTest {
    private VoteI voteI1,voteI2;
    private VoteReply voteReply1,voteReply2;

    @Before
    public void Before(){
        voteI1=null;
        voteI2=null;
        voteReply1=null;
        voteReply2=null;
    }

    @Test
    public void testTypical(){
        voteI1=new Upvote("user1");
        voteReply1=new VoteReply(voteI1,0);
        assertTrue(voteReply1.valid());
        voteI2=voteReply1.getVoteI();
        assertEquals(voteI1,voteI2);
        assertEquals(0,voteReply1.getReplyID());
        voteI1=new Downvote("user1");
        voteReply2=new VoteReply(voteI1,0);
        assertEquals(voteReply1,voteReply2);
        voteI1=new Downvote("user2");
        voteReply2=new VoteReply(voteI1,0);
        assertNotEquals(voteReply1,voteReply2);
        voteI1=new Upvote("user1");
        voteReply2=new VoteReply(voteI1,1);
        assertNotEquals(voteReply1,voteReply2);
    }

    @Test
    public void testEdge() {
        voteReply1=new VoteReply();
        assertFalse(voteReply1.valid());
        assertNull(voteReply1.getVoteI());
        assertEquals(-1,voteReply1.getReplyID());
        voteI1=new Upvote("user1");
        voteReply1=new VoteReply(voteI1,-2);
        assertFalse(voteReply1.valid());
        voteReply2=new VoteReply(voteI1,-2);
        assertNotEquals(voteReply1,voteReply2);
        assertNotEquals(null,voteReply2);
    }

}
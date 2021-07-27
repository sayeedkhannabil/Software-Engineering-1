package comp3350.grs.business;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Downvote;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Upvote;
import comp3350.grs.objects.User;
import comp3350.grs.objects.Vote;
import comp3350.grs.objects.VoteReply;
import comp3350.grs.persistence.DataAccessStub;

import static org.junit.Assert.*;

public class AccessVoteReplysTest {
    private static AccessVoteReplys accessVoteReplys;
    private static AccessUsers accessUsers;
    private static AccessReplys accessReplys;
    private static AccessPosts accessPosts;
    private VoteReply voteReply,voteReply1,voteReply2,voteReply3;
    private User user,user1,user2,user3;
    private boolean success;
    private List<VoteReply> voteReplyList;
    private Reply reply1,reply2;
    private Post post1;

    @BeforeClass
    public static void beforeClass(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(Main.testDbName));
        accessVoteReplys=new AccessVoteReplys();
        accessUsers=new AccessUsers();
        accessReplys=new AccessReplys();
        accessPosts =new AccessPosts();
    }

    @AfterClass
    public static void afterClass(){
        Services.closeDataAccess();
    }

    @Before
    public void before(){
        accessVoteReplys.clear();
        voteReply=null;
        voteReply1=null;
        voteReply2=null;
        voteReply3=null;
        user=null;
        user1=null;
        user2=null;
        user3=null;
        voteReplyList=null;
        reply1=null;
        reply2=null;
        post1=null;
        //initiate some data, used for testing
        try {
            user1=new RegisteredUser("user1","pass");
            user2=new RegisteredUser("user2","pass");
            user3=new RegisteredUser("user3","pass");

        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        accessUsers.insertUser(user2);
        accessUsers.insertUser(user3);
        try {
            post1=new Post(0,"post1","content","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessPosts.insertPost(post1);
        try {
            reply1=new Reply(0,"content","user1",0);
            reply2=new Reply(1,"content","user1",0);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessReplys.insertReply(reply1);
        accessReplys.insertReply(reply2);
    }


    @Test
    public void testTypical(){
        voteReply1=new VoteReply(new Upvote("user1"),0);
        success=accessVoteReplys.insertVoteReply(voteReply1);
        assertTrue(success);
        success=accessVoteReplys.insertVoteReply(voteReply1);
        assertFalse(success);
        voteReply2= accessVoteReplys.getVoteReply("user1",0);
        assertEquals(voteReply1,voteReply2);
        assertEquals(Vote.UP_VALUE,voteReply2);
        voteReply1=new VoteReply(new Downvote("user1"),0);
        success=accessVoteReplys.updateVoteReply(voteReply1);
        assertTrue(success);
        voteReply2= accessVoteReplys.getVoteReply("user1",0);
        assertEquals(voteReply1,voteReply2);
        assertEquals(Vote.DOWN_VALUE,voteReply2);
        voteReply2=new VoteReply(new Downvote("user1"),0);
        voteReply3=new VoteReply(new Downvote("user1"),1);
        success=accessVoteReplys.insertVoteReply(voteReply2);
        assertTrue(success);
        success=accessVoteReplys.insertVoteReply(voteReply3);
        assertTrue(success);
        voteReplyList=accessVoteReplys.getVoteReplysByReply(0);
        assertEquals(2,voteReplyList.size());
        voteReplyList=accessVoteReplys.getVoteReplysByReply(1);
        assertEquals(1,voteReplyList.size());
        accessVoteReplys.deleteVoteReply(voteReply1);
        voteReply= accessVoteReplys.getVoteReply("user1",0);
        assertNull(voteReply);
        voteReplyList=accessVoteReplys.getVoteReplysByReply(0);
        assertEquals(1,voteReplyList.size());
    }

    @Test
    public void testNull(){
        success= accessVoteReplys.insertVoteReply(null);
        assertFalse(success);
        success= accessVoteReplys.updateVoteReply(null);
        assertFalse(success);
        success= accessVoteReplys.deleteVoteReply(null);
        assertFalse(success);
    }

    @Test
    public void testEdge(){
        voteReply1=new VoteReply(new Upvote("user1"),0);
        voteReply2=new VoteReply(new Downvote("user1"),0);
        success=accessVoteReplys.insertVoteReply(voteReply1);
        assertTrue(success);
        success=accessVoteReplys.insertVoteReply(voteReply2);
        assertFalse(success);
        voteReply2=new VoteReply(new Downvote("user2"),0);
        voteReply= accessVoteReplys.getVoteReply("user2",0);
        assertNull(voteReply);
        success=accessVoteReplys.updateVoteReply(voteReply2);
        assertFalse(success);
        success=accessVoteReplys.deleteVoteReply(voteReply2);
        assertFalse(success);
        success=accessVoteReplys.deleteVoteReply(voteReply1);
        assertTrue(success);
        voteReplyList=accessVoteReplys.getVoteReplysByReply(0);
        assertEquals(0,voteReplyList.size());
    }


}
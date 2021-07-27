package comp3350.grs.business;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessStub;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccessReplysTest {
    private static AccessUsers userAccess;
    private static AccessReplys replyAccess;
    private boolean insert1, insert2, update, del;
    private Post newPost1, newPost2;
    private int postID;
    List<Reply> replyList;
    private Reply newReply1, newReply2;
    private int replyID1, replyID2;
    private String userID, guestID;

    @BeforeClass
    public static void beforeClass(){
        Services.createDataAccess(new DataAccessStub(Main.testDbName));
        replyAccess = new AccessReplys();
        userAccess = new AccessUsers();
    }

    @AfterClass
    public static void afterClass(){
        Services.closeDataAccess();
    }

    @Before
    public void before(){
        newReply1 = null;
        newReply2 = null;
        replyList = null;
        newPost1 = null;
        newPost2 = null;
        postID = -1;
        insert1 = false;
        insert2 = false;
        update = false;
        del = false;
        replyID1 = 0;
        replyID2 = 0;
        userID = null;
        guestID = null;
    }

    @Test
    public void testTypical() {
        userID = "RegisteredUser";
        guestID = "Guest";

        String rep1 = "great game";
        String rep2 = "need more features";

        try {
            newPost1 = new Post("Dota2", "Good patch", userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try {
            newReply1 = new Reply(rep1, userID, newPost1.getID());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try {
            newReply2 = new Reply(rep2, guestID, newPost1.getID());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        //test insert
        replyAccess = new AccessReplys();
        insert1 = replyAccess.insertReply(newReply1);
        assertTrue(insert1);
        insert2 = replyAccess.insertReply(newReply2);
        assertTrue(insert1);

        //get all replies
        replyList = replyAccess.getAllReplys();
        assertEquals(replyList.size(), 2);

        //get all reply by user
        replyList = replyAccess.getReplyByUser(userID);
        assertEquals(replyList.size(), 1);
        assertTrue(replyList.contains(newReply1));

        replyList = replyAccess.getReplyByUser(guestID);
        assertEquals(replyList.size(), 1);
        assertTrue(replyList.contains(newReply2));

        //getting reply by post ID
        postID = newPost1.getID();
        replyList = replyAccess.getReplyByPost(postID);
        assertEquals(replyList.size(), 2);
        assertTrue(replyList.contains(newReply1));
        assertTrue(replyList.contains(newReply2));

        //get reply by ID
        replyID1 = newReply1.getID();
        replyID2 = newReply2.getID();
        Reply r = replyAccess.getReplyById(replyID1);
        assertEquals(newReply1, r);
        r = replyAccess.getReplyById(replyID2);
        assertEquals(newReply2, r);

        //edit reply
        try {
            newReply1 = new Reply(replyID1, "Velorant is on sale", guestID, postID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        update = replyAccess.updateReply(newReply1);
        assertTrue(update);
        replyList = replyAccess.getAllReplys();
        assertEquals(replyList.size(), 2);
        assertTrue(replyList.contains(newReply1));

        //delete reply
        del = replyAccess.deleteReply(newReply1);
        assertTrue(del);
        replyList = replyAccess.getAllReplys();
        assertEquals(replyList.size(), 1);

        //clean
        replyAccess.clear();
        replyList = replyAccess.getAllReplys();
        assertEquals(replyList.size(), 0);
    }

}

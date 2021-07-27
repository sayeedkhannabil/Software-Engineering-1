package comp3350.grs.business;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessStub;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class AccessPostsTest {
    private static AccessUsers accessUsers;
    private static AccessPosts accessPosts;
    private User user;
    private List<Post> pList;
    private boolean insert1, insert2, update, del;
    private Post newPost1, newPost2;
    private int postID1, postID2;
    private String userID, guestID;

    @BeforeClass
    public static void beforeClass(){
        Services.createDataAccess(new DataAccessStub(Main.testDbName));
        accessPosts = new AccessPosts();
        accessUsers = new AccessUsers();
    }

    @AfterClass
    public static void afterClass(){
        Services.closeDataAccess();
    }

    @Before
    public void before(){
        newPost1 = null;
        newPost2 = null;
        user = null;
        pList = null;
        insert1 = false;
        insert2 = false;
        update = false;
        del = false;
        postID1 = 0;
        postID2 = 0;
        userID = null;
        guestID = null;
    }

    @Test
    public void testTypical() {
        userID = "RegisteredUser";
        guestID = "Guest";

        String title1 = "Velorant?";
        String content1 = "Is Velorant good?";

        String title2 = "Dota2";
        String content2 = "New patch is bad.";
        postID2 = 3;

        try {
            newPost1 = new Post(title1, content1, userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try {
            newPost2 = new Post(postID2, title2, content2, guestID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        //test Inserting
        accessPosts = new AccessPosts();
        insert1 = accessPosts.insertPost(newPost1);
        insert2 = accessPosts.insertPost(newPost2);

        assertTrue(insert1);
        assertTrue(insert2);

        //number of post should be 1
        pList = accessPosts.getPostsByUser(userID);
        assertEquals(pList.size(), 1);
        pList = accessPosts.getPostsByUser(guestID);
        assertEquals(pList.size(), 1);

        //multiple posts for the same user
        title1 = "GTA5";
        content1 = "is it worth to buy gta5?";
        try {
            newPost1 = new Post(title1, content1, userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        //number of post should be 2
        pList = accessPosts.getPostsByUser(userID);
        assertEquals(pList.size(), 2);

        //test update
        postID1 = newPost1.getID();
        postID2 = newPost2.getID();

        title1 = "Velorant";
        content1 = "Sale on Velorant";
        try {
            newPost1 = new Post(postID1, title1, content1, userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        update = accessPosts.updatePost(newPost1);
        assertTrue(update);

        title2 = "Horizon";
        content2 = "Sale on Horizon";
        try {
            newPost2 = new Post(postID2, title2, content2, userID);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        update = accessPosts.updatePost(newPost2);
        assertTrue(update);

        pList = accessPosts.getPostsByUser(userID);
        assertEquals(pList.size(), 3);

        //getting all post
        pList = accessPosts.getAllPosts();
        assertEquals(pList.size(), 3);
        assertTrue(pList.contains(newPost1));
        assertTrue(pList.contains(newPost2));

        //get post By ID
        postID1 = newPost1.getID();
        assertEquals(newPost1, accessPosts.getPostById(postID1));

        //testing delete
        del = accessPosts.deletePost(newPost1);
        assertTrue(del);
        assertEquals(pList.size(), 2);

        del = accessPosts.deletePost(newPost2);
        assertTrue(del);
        assertEquals(pList.size(), 1);

        //testing clear
        accessPosts.clear();
        pList = accessPosts.getAllPosts();
        assertEquals(pList.size(), 0);

    }

    @Test
    public void testEdge() {

    }

}

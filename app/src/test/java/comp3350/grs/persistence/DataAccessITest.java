package comp3350.grs.persistence;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Downvote;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Request;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.Upvote;
import comp3350.grs.objects.User;
import comp3350.grs.objects.Vote;
import comp3350.grs.objects.VoteReply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class DataAccessITest{
    private static DataAccessI dataAccessI;
    private User user,user1,user2,user3;
    private Game game,game1,game2,game3;
    private Review review, review1,review2,review3;
    private Rating rating, rating1,rating2,rating3;
    private Request request, request1,request2,request3;
    private Reply reply, reply1,reply2,reply3;
    private Post post, post1, post2 ,post3;
    private VoteReply voteReply,voteReply1,voteReply2,voteReply3;
    private List<User> userList;
    private List<Game> gameList;
    private List<Review> reviewList;
    private List<Rating> ratingList;
    private List<Request> requestList;
    private List<Reply> replyList;
    private List<Post> postList;
    private List<VoteReply> voteReplyList;
    private String userID,userID2,password,password2,gameName,developer,
            description,reviewContent;
    private double price;
    private boolean success;
    private List<String> genreList;


    @BeforeClass()
    public static void beforeClass(){
        dataAccessI=new DataAccessStub(Main.testDbName);
        dataAccessI.open(Main.getDBPathName(Main.testDbName));
    }

    @Before
    public void before(){
        dataAccessI.clearAllData();
        user=null;
        user1=null;
        user2=null;
        user3=null;
        game=null;
        game1=null;
        game2=null;
        game3=null;
        userList=null;
        gameList=null;
        userID=null;
        userID2=null;
        password=null;
        password2=null;
        gameName=null;
        developer=null;
        description=null;
        price=0.0;
        review1=null;
        review2=null;
        review3=null;
        rating1=null;
        rating2=null;
        rating3=null;
        request1=null;
        request2=null;
        request3=null;
        reply=null;
        reply1=null;
        reply2=null;
        reply3=null;
        post=null;
        post1=null;
        post2=null;
        post3=null;
        voteReply1=null;
        voteReply2=null;
        voteReply3=null;
        voteReply=null;
        success=false;
        reviewList=null;
        ratingList=null;
        genreList=null;
        requestList=null;
        replyList=null;
        postList=null;
        voteReplyList=null;
    }

    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }

    // this code will run the tests on the given DAO
    public static void dataAccessTest(DataAccessI dataAccess) {
        DataAccessITest dataAccessTest = new DataAccessITest();
        dataAccessTest.dataAccessI = dataAccess;
        dataAccessTest.before();
        dataAccessTest.testTypical();
        dataAccessTest.before();
        dataAccessTest.testNull();
        dataAccessTest.before();
        dataAccessTest.testEdge();
        dataAccessTest.afterClass();
    }

    @Test
    public void testTypical(){
        try {
            user1=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertUser(user1);
        assertTrue (success);
        userList=dataAccessI.getAllUsers();
        assertEquals(1,userList.size());
        user2=userList.get(0);
        assertEquals(user1,user2);
        user2=dataAccessI.getUserByID("Guest");
        assertEquals("Guest",user2.getUserID());
        userID="myUserID";
        password="myPassword";
        try {
            user1=new RegisteredUser(userID,password);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertUser(user1);
        assertTrue (success);
        userList=dataAccessI.getAllUsers();
        assertEquals(2,userList.size());
        userList= dataAccessI.getUsersByIDImplicit("myUserID");
        assertEquals(1,userList.size());
        user2=dataAccessI.getUserByID(userID);
        assertEquals(userID,user2.getUserID());
        RegisteredUser registeredUser;
        registeredUser=(RegisteredUser)user2;
        assertEquals(password,registeredUser.getPassword());
        password2="anotherPassword";
        try {
            user1=new RegisteredUser(userID,password2);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.updateUser(user1);
        userList=dataAccessI.getAllUsers();
        assertEquals(2,userList.size());
        user2= dataAccessI.getUserByID(userID);
        assertEquals(userID,user2.getUserID());
        registeredUser=(RegisteredUser)user2;
        assertEquals(password2,registeredUser.getPassword());
        dataAccessI.deleteUser(user1);
        userList=dataAccessI.getAllUsers();
        assertEquals(1,userList.size());
        user2= dataAccessI.getUserByID(userID);
        assertNull(user2);
        dataAccessI.clearAllData();
        try {
            user1=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertUser(user1);
        user=dataAccessI.getUserByID("Guest");
        assertNotNull(user);
        dataAccessI.clearUsers();
        user=dataAccessI.getUserByID("Guest");
        assertNull(user);

        try {
            game3=new Game("otherGame1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertGame(game3);
        gameList=dataAccessI.getAllGames();
        assertEquals(1,gameList.size());
        gameName="myGame";
        developer="myDeveloper";
        description="myDescription";
        price=9.99;
        genreList=new ArrayList<>();
        genreList.clear();
        genreList.add("genre1");
        genreList.add("genre2");
        genreList.add("genre3");
        try {
            game1=new Game(gameName,developer,description,price,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertGame(game1);
        assertTrue (success); ;
        gameList=dataAccessI.getAllGames();
        assertEquals(2,gameList.size());
        game2=dataAccessI.getGameByName(gameName);
        assertEquals(gameName,game2.getName());
        assertEquals(developer,game2.getDev());
        assertEquals(description,game2.getDescription());
        assertEquals(price,game2.getPrice(),0.01);
        genreList=game2.getGenres();
        assertEquals(3,genreList.size());
        assertTrue (genreList.contains("genre1"));
        assertTrue (genreList.contains("genre2"));
        assertTrue (genreList.contains("genre3"));

        try {
            game3=new Game("otherGame2");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertGame(game3);
        gameList=dataAccessI.getAllGames();
        assertEquals(3,gameList.size());

        developer="anotherDeveloper";
        description="anotherDescription";
        price=99.99;
        genreList.clear();
        genreList.add("genre1");
        genreList.add("genre4");

        try {
            game1=new Game(gameName,developer,description,price,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.updateGame(game1);
        gameList=dataAccessI.getAllGames();
        assertEquals(3,gameList.size());
        game2=dataAccessI.getGameByName(gameName);
        assertEquals(gameName,game2.getName());
        assertEquals(developer,game2.getDev());
        assertEquals(description,game2.getDescription());
        assertEquals(price,game2.getPrice(),0.01);
        genreList=game2.getGenres();
        assertEquals(2,genreList.size());
        assertTrue (genreList.contains("genre1"));
        assertTrue (genreList.contains("genre4"));
        dataAccessI.deleteGame(game1);
        gameList=dataAccessI.getAllGames();
        assertEquals(2,gameList.size());
        game2=dataAccessI.getGameByName(gameName);
        assertNull(game2);
        try {
            game1=new Game("gameName");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertGame(game1);
        game= dataAccessI.getGameByName("gameName");
        assertNotNull(game);
        dataAccessI.clearGames();
        game= dataAccessI.getGameByName("gameName");
        assertNull(game);

        dataAccessI.clearAllData();
        userList=dataAccessI.getAllUsers();
        assertEquals(0,userList.size());
        gameList=dataAccessI.getAllGames();
        assertEquals(0,gameList.size());


        try {
            user=new Guest();
            user1=new RegisteredUser("user1","password");
            user2=new RegisteredUser("user2","password");
            user3=new RegisteredUser("user3","password");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        genreList.clear();
        try {
            game1=new Game("game1","dev1","desc1",1.0,genreList);
            game2=new Game("game2","dev2","desc2",2.0,genreList);
            game3=new Game("game3","dev3","desc3",3.0,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        dataAccessI.insertUser(user);
        dataAccessI.insertUser(user1);
        dataAccessI.insertUser(user2);
        dataAccessI.insertUser(user3);
        dataAccessI.insertGame(game1);
        dataAccessI.insertGame(game2);
        dataAccessI.insertGame(game3);

        userList=dataAccessI.getUsersByIDImplicit("user%");
        assertEquals(3,userList.size());
        assertTrue (userList.contains(user1));
        assertTrue (userList.contains(user2));
        assertTrue (userList.contains(user3));
        userList=dataAccessI.getUsersByIDImplicit("user");
        assertEquals(0,userList.size());

        gameList=dataAccessI.getGamesByNameImplicit("game%");
        assertEquals(3,gameList.size());
        assertTrue (gameList.contains(game1));
        assertTrue (gameList.contains(game2));
        assertTrue (gameList.contains(game3));
        gameList=dataAccessI.getGamesByNameImplicit("ga%");
        assertEquals(3,gameList.size());
        gameList=dataAccessI.getGamesByNameImplicit("game");
        assertEquals(0,gameList.size());

        try {
            review1=new Review("content1","game1","Guest");
            review2=new Review(5,"content2","game3","Guest");
            review3=new Review("content3","game3","user2");
            rating1=new Rating(4.5,"game1","user2");
            rating2=new Rating(4.6,"game1","user3");
            rating3=new Rating(4.0,"game2","user2");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        success= dataAccessI.insertReview(review1);
        assertTrue(success) ;
        success= dataAccessI.insertReview(review2);
        assertTrue(success) ;
        success= dataAccessI.insertReview(review3);
        assertTrue(success) ;
        success= dataAccessI.insertRating(rating1);
        assertTrue(success) ;
        success= dataAccessI.insertRating(rating2);
        assertTrue(success) ;
        success= dataAccessI.insertRating(rating3);
        assertTrue(success) ;

        reviewList=dataAccessI.getAllReviews();
        assertEquals(3,reviewList.size());
        reviewList=dataAccessI.getReviewsByGame("game3");
        assertEquals(2,reviewList.size());
        reviewList=dataAccessI.getReviewsByGame("game1");
        assertEquals(1,reviewList.size());
        reviewList=dataAccessI.getReviewsByGame("game2");
        assertEquals(0,reviewList.size());
        reviewList=dataAccessI.getReviewsByUser("Guest");
        assertEquals(2,reviewList.size());
        reviewList=dataAccessI.getReviewsByUser("user2");
        assertEquals(1,reviewList.size());
        assertEquals("content3",reviewList.get(0).getComment());
        reviewList=dataAccessI.getReviewsByUser("user1");
        assertEquals(0,reviewList.size());

        review=dataAccessI.getReviewByID(5);
        assertEquals("content2",review2.getComment());
        assertEquals("Guest",review2.getUserID());
        assertEquals("game3",review2.getGameName());

        try {
            review=new Review(5,"newContent","game2","user2" );
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.updateReview(review);
        assertTrue(success);
        review= dataAccessI.getReviewByID(5);
        assertEquals("newContent",review.getComment());
        assertEquals("user2",review.getUserID());
        assertEquals("game2",review.getGameName());
        success=dataAccessI.deleteReview(review);
        review= dataAccessI.getReviewByID(5);
        assertNull(review);

        ratingList=dataAccessI.getAllRatings();
        assertEquals(3,ratingList.size());
        ratingList=dataAccessI.getRatingsByGame("game1");
        assertEquals(2,ratingList.size());
        ratingList=dataAccessI.getRatingsByGame("game2");
        assertEquals(1,ratingList.size());
        ratingList=dataAccessI.getRatingsByGame("game3");
        assertEquals(0,ratingList.size());
        ratingList=dataAccessI.getRatingsByUser("user2");
        assertEquals(2,ratingList.size());
        ratingList=dataAccessI.getRatingsByUser("user3");
        assertEquals(1,ratingList.size());
        assertEquals(rating2,ratingList.get(0));
        ratingList=dataAccessI.getRatingsByUser("user1");
        assertEquals(0,ratingList.size());

        rating=dataAccessI.getRating("game1","user3");
        assertEquals("user3",rating.getUserID());
        assertEquals("game1",rating.getGameName());
        assertEquals(rating.getRatingValue(),4.6,0.01);
        assertEquals("game1",rating.getGameName());
        assertEquals("user3",rating.getUserID());
        try {
            rating=new Rating(3.0,"game1","user3");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.updateRating(rating);
        assertTrue(success);
        rating=dataAccessI.getRating("game1","user3");
        assertEquals("user3",rating.getUserID());
        assertEquals("game1",rating.getGameName());
        assertEquals(rating.getRatingValue(),3.0,0.01);
        success=dataAccessI.deleteRating(rating);
        rating= dataAccessI.getRating("game1","user3");
        assertNull(rating);


        try {
            review1=new Review(13,"comment","game1","Guest");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertReview(review1);
        review= dataAccessI.getReviewByID(13);
        assertNotNull(review);
        dataAccessI.clearReviews();
        review= dataAccessI.getReviewByID(13);
        assertNull(review);

        try {
            rating1=new Rating(3.0,"game1","Guest");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertRating(rating1);
        rating= dataAccessI.getRating("game1","Guest");
        assertNotNull(rating);
        dataAccessI.clearRatings();
        rating= dataAccessI.getRating("game1","Guest");
        assertNull(rating);

        try {
            request1=new Request("game1","user1");
            request2=new Request("game2","user2");
            request3=new Request("game3","user3");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertRequest(request1);
        assertTrue(success);
        requestList=dataAccessI.getAllRequests();
        assertEquals(1,requestList.size());
        success=dataAccessI.insertRequest(request2);
        assertTrue(success);
        success=dataAccessI.insertRequest(request3);
        assertTrue(success);
        requestList=dataAccessI.getAllRequests();
        assertEquals(3,requestList.size());
        try {
            request1=new Request("game1","user2");
            request2=new Request("game1","user3");
            request3=new Request("game2","user3");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertRequest(request1);
        assertTrue(success);
        success=dataAccessI.insertRequest(request2);
        assertTrue(success);
        success=dataAccessI.insertRequest(request3);
        assertTrue(success);
        requestList=dataAccessI.getRequestsByGame("game1");
        assertEquals(3,requestList.size());
        requestList=dataAccessI.getRequestsByGame("game2");
        assertEquals(2,requestList.size());
        requestList=dataAccessI.getRequestsByUser("user2");
        assertEquals(2,requestList.size());
        requestList=dataAccessI.getRequestsByUser("user3");
        assertEquals(3,requestList.size());
        request1= dataAccessI.getRequest("game1","user2");
        assertNotNull(request1);
        assertEquals("game1",request1.getGameName());
        assertEquals("user2",request1.getUserID());
        List<String>  gameNameList;
        gameNameList=dataAccessI.getGamesOrderByRequestNum(1);
        assertEquals("game1",gameNameList.get(0));
        gameNameList=dataAccessI.getGamesOrderByRequestNum(2);
        assertEquals(2,gameNameList.size());
        success=dataAccessI.deleteRequest(request1);
        assertTrue(success);
        request= dataAccessI.getRequest("game1","user2");
        assertNull(request);


        //test post
        try{
            post1=new Post(100,"post1 title","post1 content","user1");
            success=dataAccessI.insertPost(post1);
            assertTrue(success);
            post2=new Post(101,"post2 title","post2 content","user2");
            success=dataAccessI.insertPost(post2);
            assertTrue(success);
            success=post1.equals(post2);
            assertFalse(success);
            post3=new Post("post3","content3","user3");
            success=dataAccessI.insertPost(post3);
            assertTrue(success);
            postList=dataAccessI.getPostsByUser("user3");
            assertEquals(1,postList.size());
            post1=new Post(100,"newPost","newContent","user2");
            success=dataAccessI.updatePost(post1);
            assertTrue(success);
            post= dataAccessI.getPostByID(100);
            assertEquals("newPost",post.getTitle());
            assertEquals("newContent",post.getContent());
            assertEquals("user2",post.getUserID());
            postList=dataAccessI.getAllPosts();
            assertEquals(3,postList.size());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        //test reply
        try{
            reply1=new Reply(1000,"reply1","user1",100);
            success=dataAccessI.insertReply(reply1);
            assertTrue(success);
            reply2=new Reply(1001,"reply2","user2",101);
            success=dataAccessI.insertReply(reply2);
            assertTrue(success);
            success=reply1.equals(reply2);
            assertFalse(success);
            reply3=new Reply("reply3","user3",100);
            success=dataAccessI.insertReply(reply3);
            assertTrue(success);
            replyList= dataAccessI.getReplysByUser("user3");
            assertEquals(1,replyList.size());
            reply=replyList.get(0);
            assertNotEquals(-1,reply.getID());
            reply1=new Reply(1000,"newReply","user2",101);
            dataAccessI.updateReply(reply1);
            reply= dataAccessI.getReplyByID(1000);
            assertEquals("newReply",reply.getContent());
            assertEquals("user2",reply.getUserID());
            assertEquals(101,reply.getPostID());
            replyList=dataAccessI.getAllReplys();
            assertEquals(3,replyList.size());
            replyList= dataAccessI.getReplysByPost(101);
            assertEquals(2,replyList.size());
            replyList= dataAccessI.getReplysByUser("user2");
            assertEquals(2,replyList.size());
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        //test vote reply
        voteReply1=new VoteReply(new Upvote("user1"),1000);
        success=dataAccessI.insertVoteReply(voteReply1);
        assertTrue(success);
        success=dataAccessI.insertVoteReply(voteReply1);
        assertFalse(success);
        voteReply2= dataAccessI.getVoteReply("user1",1000);
        assertEquals(voteReply1,voteReply2);
        assertEquals(Vote.UP_VALUE,voteReply2.getVoteI().getValue());
        voteReply1=new VoteReply(new Downvote("user1"),1000);
        success=dataAccessI.updateVoteReply(voteReply1);
        assertTrue(success);
        voteReply2= dataAccessI.getVoteReply("user1",1000);
        assertEquals(voteReply1,voteReply2);
        assertEquals(Vote.DOWN_VALUE,voteReply2.getVoteI().getValue());
        voteReply2=new VoteReply(new Downvote("user2"),1000);
        voteReply3=new VoteReply(new Downvote("user1"),1001);
        success=dataAccessI.insertVoteReply(voteReply2);
        assertTrue(success);
        success=dataAccessI.insertVoteReply(voteReply3);
        assertTrue(success);
        voteReplyList=dataAccessI.getVoteReplysByReply(1000);
        assertEquals(2,voteReplyList.size());
        voteReplyList=dataAccessI.getVoteReplysByReply(1001);
        assertEquals(1,voteReplyList.size());
        dataAccessI.deleteVoteReply(voteReply1);
        voteReply= dataAccessI.getVoteReply("user1",1000);
        assertNull(voteReply);
        voteReplyList=dataAccessI.getVoteReplysByReply(1000);
        assertEquals(1,voteReplyList.size());
    }

    @Test
    public void testNull(){

        try {
            user1=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertUser(user1);
        userList=dataAccessI.getUsersByIDImplicit(null);
        assertEquals(0,userList.size());
        user2=dataAccessI.getUserByID(null);
        assertNull (user2);
        success= dataAccessI.insertUser(null);
        assertFalse(success);
        success= dataAccessI.updateUser(null);
        assertFalse(success);
        success= dataAccessI.deleteUser(null);
        assertFalse(success);

        genreList=new ArrayList<>();
        genreList.add("genre");
        try {
            game1=new Game("gameName","dev","desc",1.0,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertGame(game1);
        gameList=dataAccessI.getGamesByNameImplicit(null);
        assertEquals(0,gameList.size());
        game2= dataAccessI.getGameByName(null);
        assertNull(game2);
        success=dataAccessI.insertGame(null);
        assertFalse(success);
        success=dataAccessI.updateGame(null);
        assertFalse(success);
        success=dataAccessI.deleteGame(null);
        assertFalse(success);

        try {
            user1=new RegisteredUser("userID","123");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertUser(user1);
        try {
            review1=new Review("comment","gameName","userID");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertReview(review1);
        success=dataAccessI.insertReview(null);
        assertFalse(success);
        success=dataAccessI.updateReview(null);
        assertFalse(success);
        success=dataAccessI.deleteReview(null);
        assertFalse(success);
        reviewList=dataAccessI.getReviewsByGame(null);
        assertEquals(0,reviewList.size());
        reviewList=dataAccessI.getReviewsByUser(null);
        assertEquals(0,reviewList.size());

        try {
            rating1=new Rating(4.0,"gameName","userID");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertRating(rating1);
        success=dataAccessI.insertRating(null);
        assertFalse(success);
        success=dataAccessI.updateRating(null);
        assertFalse(success);
        success=dataAccessI.deleteRating(null);
        assertFalse(success);
        ratingList=dataAccessI.getRatingsByGame(null);
        assertEquals(0,ratingList.size());
        ratingList=dataAccessI.getRatingsByUser(null);
        assertEquals(0,ratingList.size());
        rating2= dataAccessI.getRating(null,null);
        assertNull(rating2);

        dataAccessI.clearAllData();
        try {
            user1=new RegisteredUser("myUserID","myPass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertUser(user1);
        userList= dataAccessI.getUsersByIDImplicit("myUserID");
        assertEquals(1,userList.size());

        success=dataAccessI.insertRequest(null);
        assertFalse(success);
        success=dataAccessI.deleteRequest(null);
        assertFalse(success);

        success= dataAccessI.insertVoteReply(null);
        assertFalse(success);
        success= dataAccessI.updateVoteReply(null);
        assertFalse(success);
        success= dataAccessI.deleteVoteReply(null);
        assertFalse(success);
        
        success= dataAccessI.insertReply(null);
        assertFalse(success);
        success= dataAccessI.updateReply(null);
        assertFalse(success);
        success= dataAccessI.deleteReply(null);
        assertFalse(success);
        
        success= dataAccessI.insertPost(null);
        assertFalse(success);
        success= dataAccessI.updatePost(null);
        assertFalse(success);
        success= dataAccessI.deletePost(null);
        assertFalse(success);
    }

    @Test
    public void testEdge(){

        try {
            user1=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.insertUser(user1);
        assertTrue(success);
        success= dataAccessI.updateUser(user1);
        assertTrue(success);
        userList=dataAccessI.getUsersByIDImplicit("");
        assertEquals(0,userList.size());
        user2= dataAccessI.getUserByID("");
        assertNull(user2);

        genreList=new ArrayList<>();
        try {
            game1=new Game("game1","","",1.0,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.insertGame(game1);
        assertTrue(success);
        game2= dataAccessI.getGameByName("game1");
        assertEquals(game1,game2);
        gameList=dataAccessI.getGamesByNameImplicit("g%");
        assertEquals(1,gameList.size());
        game2=gameList.get(0);
        assertEquals(game1,game2);
        try {
            game1=new Game("game1","dev","desc",2.0,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.updateGame(game1);
        assertTrue(success);
        game2= dataAccessI.getGameByName("game1");
        assertEquals(2.0,game2.getPrice(),0.01);
        assertEquals("dev", game2.getDev());
        assertEquals("desc", game2.getDescription());
        success=dataAccessI.deleteGame(game1);
        assertTrue(success);
        game2= dataAccessI.getGameByName("game1");
        assertNull(game2);

        try {
            game1=new Game("1","","",1.0,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertGame(game1);
        assertTrue(success);
        game2= dataAccessI.getGameByName("1");
        assertEquals(game1,game2);
        gameList=dataAccessI.getGamesByNameImplicit("1");
        assertEquals(1,gameList.size());
        game2=gameList.get(0);
        assertEquals(game1,game2);
        try {
            game1=new Game("1","","",3.0,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.updateGame(game1);
        assertTrue(success);
        game2= dataAccessI.getGameByName("1");
        assertEquals(game2.getPrice(),3.0,0.01);
        success=dataAccessI.deleteGame(game1);
        assertTrue(success);
        game2= dataAccessI.getGameByName("1");
        assertNull(game2);

        try {
            game1=new Game("gameName");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertGame(game1);
        try {
            user1=new RegisteredUser("userID","password");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.insertUser(user1);
        assertTrue(success);
        try {
            review1=new Review(0,"comment","gameName","userID");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertReview(review1);
        assertTrue(success);
        reviewList=dataAccessI.getReviewsByGame("gameName");
        assertEquals(1,reviewList.size());
        review2=reviewList.get(0);
        assertEquals(review1,review2);
        reviewList=dataAccessI.getReviewsByUser("userID");
        assertEquals(1,reviewList.size());
        review2=reviewList.get(0);
        assertEquals(review1,review2);
        review2= dataAccessI.getReviewByID(0);
        assertEquals(review1,review2);
        try {
            review1=new Review(0,"newComment","gameName","userID");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.updateReview(review1);
        assertTrue(success);
        review2= dataAccessI.getReviewByID(0);
        assertEquals("newComment",review2.getComment());
        success= dataAccessI.deleteReview(review1);
        assertTrue(success);
        review2= dataAccessI.getReviewByID(0);
        assertNull(review2);

        try {
            rating1=new Rating(0.5,"gameName","userID");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertRating(rating1);
        assertTrue(success);
        ratingList=dataAccessI.getRatingsByUser("userID");
        assertEquals(1,ratingList.size());
        rating2=ratingList.get(0);
        assertEquals(rating1,rating2);
        ratingList=dataAccessI.getRatingsByGame("gameName");
        assertEquals(1,ratingList.size());
        rating2=ratingList.get(0);
        assertEquals(rating1,rating2);
        rating2= dataAccessI.getRating("gameName","userID");
        assertEquals(rating1,rating2);
        try {
            rating1=new Rating(1.0,"gameName","userID");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= dataAccessI.updateRating(rating1);
        assertTrue(success);
        rating2= dataAccessI.getRating("gameName","userID");
        assertEquals(rating1,rating2);
        success=dataAccessI.deleteRating(rating1);
        assertTrue(success);
        rating2= dataAccessI.getRating("gameName","userID");
        assertNull(rating2);

        //request
        dataAccessI.clearAllData();
        requestList=dataAccessI.getAllRequests();
        assertEquals(0,requestList.size());
        try {
            game1=new Game("game1");
            game2=new Game("game2");
            game3=new Game("game3");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            user=new Guest();
            user1=new RegisteredUser("user1","pass");
            user2=new RegisteredUser("user2","pass");
            user3=new RegisteredUser("user3","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertUser(user);
        dataAccessI.insertUser(user1);
        dataAccessI.insertUser(user2);
        dataAccessI.insertUser(user3);
        dataAccessI.insertGame(game1);
        dataAccessI.insertGame(game2);
        dataAccessI.insertGame(game3);

        try {
            request1=new Request("game1","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success=dataAccessI.insertRequest(request1);
        assertTrue(success);
        success=dataAccessI.insertRequest(request1);
        assertFalse(success);

        try {
            post1=new Post(10,"title","content","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertPost(post1);
        try {
            reply1=new Reply(10,"content","user1",10);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertReply(reply1);

        voteReply1=new VoteReply(new Upvote("user1"),10);
        assertNotNull(voteReply1);
        assertTrue(voteReply1.valid());
        voteReply2=new VoteReply(new Downvote("user1"),10);
        success=dataAccessI.insertVoteReply(voteReply1);
        assertTrue(success);
        success=dataAccessI.insertVoteReply(voteReply2);
        assertFalse(success);
        voteReply2=new VoteReply(new Downvote("user2"),10);
        voteReply= dataAccessI.getVoteReply("user2",10);
        assertNull(voteReply);
        success=dataAccessI.updateVoteReply(voteReply2);
        assertFalse(success);
        success=dataAccessI.deleteVoteReply(voteReply2);
        assertFalse(success);
        success=dataAccessI.deleteVoteReply(voteReply1);
        assertTrue(success);
        voteReplyList=dataAccessI.getVoteReplysByReply(10);
        assertEquals(0,voteReplyList.size());

        dataAccessI.deleteReply(reply1);
        dataAccessI.deletePost(post1);
        user= dataAccessI.getUserByID("Guest");
        assertNotNull(user);
        user= dataAccessI.getUserByID("user1");
        assertNotNull(user);
        dataAccessI.deleteDatabase();
        dataAccessI.open(Main.getDBPathName(Main.testDbName));
        user= dataAccessI.getUserByID("Guest");
        assertNotNull(user);
        user= dataAccessI.getUserByID("user1");
        assertNull(user);
    }
}

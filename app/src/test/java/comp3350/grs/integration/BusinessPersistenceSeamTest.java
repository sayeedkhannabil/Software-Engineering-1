package comp3350.grs.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.business.AccessGames;
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessRequests;
import comp3350.grs.business.AccessReviews;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessVoteReplys;
import comp3350.grs.exceptions.Duplicate;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Downvote;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Request;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.Upvote;
import comp3350.grs.objects.User;
import comp3350.grs.objects.VoteReply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BusinessPersistenceSeamTest {
    private String className;
    private boolean inserted, updated, deleted;
    private AccessGames accessGames;
    private AccessPosts accessPosts;
    private AccessRatings accessRatings;
    private AccessReplys accessReplys;
    private AccessRequests accessRequests;
    private AccessReviews accessReviews;
    private AccessUsers accessUsers;
    private AccessVoteReplys accessVoteReplys;
    Game game, game2;
    User user1, user2;

    @Before
    public void before(){
        System.out.println("\nStarting Integration test");
        Services.closeDataAccess();
        Services.createDataAccess(Main.testDbName);
        accessGames = new AccessGames();
        accessPosts = null;
        accessRatings = new AccessRatings();
        accessReplys = null;
        accessRequests = null;
        accessReviews = new AccessReviews();
        accessUsers = new AccessUsers();
        accessVoteReplys = null;
        inserted = false;
        updated = false;
        deleted = false;

        try{
            game = new Game("newGame", "dev", "desc", 5.00, new ArrayList<>());
            game2 = new Game("newGame2", "otherDev", "desc", 100, new ArrayList<>());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessGames.insertGame(game);
        assertTrue(inserted);
        inserted = accessGames.insertGame(game2);
        assertTrue(inserted);

        try{
            user1 = new RegisteredUser("newUser", "pass");
            user2 = new RegisteredUser("newUser2", "pass2");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessUsers.insertUser(user1);
        assertTrue(inserted);
        inserted = accessUsers.insertUser(user2);
        assertTrue(inserted);
    }

    @After
    public void after(){
        accessRatings.clear();
        accessReviews.clear();
        accessGames.deleteGame(game);
        accessGames.deleteGame(game2);
        accessUsers.deleteUser(user1);
        accessUsers.deleteUser(user2);
        Services.closeDataAccess();
        System.out.println("Finished Integration test of "+className+" to persistence");
    }

    @Test
    public void testAccessGames(){
        className = "AccessGames";
        accessRatings = new AccessRatings();
        accessReviews = new AccessReviews();

        List<Game> allGames;
        Rating rating = null;
        Review review = null;
        String gameName;
        int listSize;

        allGames = accessGames.getAllGames();
        assertNotNull(allGames);
        listSize = allGames.size();
        assertTrue(listSize > 0);

        //update
        try {
            game = new Game("newGame", "different", "different", 20.00, new ArrayList<>());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        updated = accessGames.updateGame(game);
        assertTrue(updated);
        game = accessGames.getGameByName("newGame");
        assertEquals("newGame", game.getName());
        assertEquals("different", game.getDev());
        assertEquals("different", game.getDescription());
        assertEquals(20.00, game.getPrice(), 0);
        assertNotNull(game.getGenres());
        listSize = accessGames.getAllGames().size();

        //add ratings and review to test sort methods (which communicate with persistence)
        try{
            rating = new Rating(5.0, game.getName(), user1.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessRatings.insertRating(rating);
        assertTrue(inserted);
        try{
            rating = new Rating(3.0, game2.getName(), user2.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessRatings.insertRating(rating);
        assertTrue(inserted);
        try{
            rating = new Rating(4.0, game2.getName(), user1.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessRatings.insertRating(rating);
        assertTrue(inserted);

        try{
            review = new Review("comment", game.getName(), user2.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReviews.insertReview(review);
        assertTrue(inserted);

        //ascending + descending rating sort
        allGames = accessGames.descendingRatingSort();
        assertEquals(game2, allGames.get(0));

        allGames = accessGames.ascendingRatingSort();
        assertNotEquals(game2, allGames.get(0));

        //ascending + descending review sort
        allGames = accessGames.descendingReviewSort();
        assertEquals(game, allGames.get(0));

        allGames = accessGames.ascendingReviewSort();
        assertNotEquals(game, allGames.get(0));

        //game by name implicit
        allGames = accessGames.getGamesByNameImplicit("new");
        assertTrue(allGames.size() >=2 ); //all games begin with game

        accessRatings.clear();
        accessReviews.clear();

        //delete
        listSize = accessGames.getAllGames().size();
        deleted = accessGames.deleteGame(game);
        assertTrue(deleted);
        allGames = accessGames.getAllGames();
        assertFalse(allGames.contains(game));
        assertEquals(listSize-1, allGames.size());
        assertNull(accessGames.getGameByName("newGame"));
        deleted = accessGames.deleteGame(game2);
        assertTrue(deleted);

        try {
            game=new Game("my game");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game);
        game=accessGames.getGameByName("my game");
        assertNotNull(game);
        accessGames.clear();
        game=accessGames.getGameByName("my game");
        assertNull(game);
    }

    @Test
    public void testAccessPosts(){
        className = "AccessPosts";
        accessPosts = new AccessPosts();
        List<Post> allPosts;
        int postID = 1;
        Post post = null;

        //insert
        try{
            post = new Post(postID, "NewPost", "content", user1.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(post);
        assertTrue(post.valid());
        inserted = accessPosts.insertPost(post);
        assertTrue(inserted);
        allPosts = accessPosts.getAllPosts();
        assertEquals(1, allPosts.size());
        assertTrue(allPosts.contains(post));
        allPosts = accessPosts.getPostsByUser(user1.getUserID());
        assertEquals(1, allPosts.size());

        //can't insert same post multiple times
        inserted = accessPosts.insertPost(post);
        assertFalse(inserted);

        //update
        try{
            post = new Post(postID, "NewTitle", "newContent", user1.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(post);
        assertTrue(post.valid());
        updated = accessPosts.updatePost(post);
        assertTrue(updated);
        allPosts = accessPosts.getAllPosts();
        assertEquals(1, allPosts.size());
        post = accessPosts.getPostById(postID);
        assertTrue(allPosts.contains(post));

        //delete
        deleted = accessPosts.deletePost(post);
        assertTrue(deleted);
        post = accessPosts.getPostById(postID);
        assertNull(post);
        allPosts = accessPosts.getPostsByUser(user1.getUserID());
        assertTrue(allPosts.isEmpty());
        allPosts = accessPosts.getAllPosts();
        assertTrue(allPosts.isEmpty());

        accessPosts.clear();
        assertTrue(accessPosts.getAllPosts().isEmpty());
    }

    @Test
    public void testAccessRatings(){
        className = "AccessRatings";
        List<Rating> allRatings;
        Rating rating = null;

        //insert
        try{
            rating = new Rating(5.0, game.getName(),user2.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(rating);
        assertTrue(rating.valid());
        inserted = accessRatings.insertRating(rating);
        assertTrue(inserted);
        allRatings = accessRatings.getAllRatings();
        assertTrue(allRatings.size() >= 1);
        assertTrue(allRatings.contains(rating));
        allRatings = accessRatings.getRatingsByUser(user2.getUserID());
        assertEquals(1, allRatings.size());
        allRatings = accessRatings.getRatingsByGame(game.getName());
        assertEquals(1, allRatings.size());
        Rating rating2 = accessRatings.getRating(game.getName(), user2.getUserID());
        assertEquals(rating, rating2);
        int ratingNumByGame = accessRatings.getRatingNumByGame(game.getName());
        assertTrue(ratingNumByGame >= 1);
        int ratingNumByUser = accessRatings.getRatingNumByUser(user2.getUserID());
        assertEquals(1, ratingNumByUser);

        //can't insert same rating multiple times
        inserted = accessRatings.insertRating(rating);
        assertFalse(inserted);

        //update
        try{
            rating2 = new Rating(2.0, game.getName(), user2.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(rating2);
        assertTrue(rating2.valid());
        updated = accessRatings.updateRating(rating2);
        assertTrue(updated);
        allRatings = accessRatings.getAllRatings();
        assertTrue(allRatings.size() >= 1);
        rating = accessRatings.getRating(game.getName(), user2.getUserID());
        assertTrue(allRatings.contains(rating));
        assertEquals(2.0, rating.getRatingValue(), 0);


        //delete
        deleted = accessRatings.deleteRating(rating);
        assertTrue(deleted);
        rating2 = accessRatings.getRating(game.getName(), user2.getUserID());
        assertNull(rating2);
        allRatings = accessRatings.getRatingsByGame(game.getName());
        assertTrue(allRatings.isEmpty());
        allRatings = accessRatings.getAllRatings();
        assertTrue(allRatings.isEmpty());
        accessRatings.clear();
    }

    @Test
    public void testAccessReplys(){
        className = "AccessReplys";
        accessReplys = new AccessReplys();
        accessPosts = new AccessPosts();
        List<Reply> allReplies;
        Post postToReply = null;
        Reply reply = null;

        //must insert post to reply to
        try{
            postToReply = new Post(8, "title", "content", user2.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessPosts.insertPost(postToReply);

        //insert
        try{
            reply = new Reply(5,"comment", user1.getUserID(), 8);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReplys.insertReply(reply);
        assertTrue(inserted);

        try{
            reply = new Reply("comment", user2.getUserID(), 8);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReplys.insertReply(reply);
        assertTrue(inserted);

        //cannot insert reply to nonexistent post (ie. a post with this postID not in the post table)
        try{
            reply = new Reply(3, "newComment", user2.getUserID(), 4);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReplys.insertReply(reply);
        assertFalse(inserted);

        allReplies = accessReplys.getReplyByUser(user2.getUserID());
        assertEquals(1, allReplies.size());
        assertFalse(allReplies.contains(accessReplys.getReplyById(3)));

        allReplies = accessReplys.getReplyByPost(8);
        assertEquals(2, allReplies.size());
        assertTrue(allReplies.contains(accessReplys.getReplyById(5)));

        //update
        try{
            reply = new Reply(5,"otherComment", user1.getUserID(), 8);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        updated = accessReplys.updateReply(reply);
        assertTrue(updated);
        allReplies = accessReplys.getAllReplys();
        assertTrue(allReplies.contains(reply));
        int indexReply = allReplies.indexOf(reply);
        reply = allReplies.get(indexReply);
        assertEquals(5, reply.getID());
        assertEquals("otherComment", reply.getContent());
        assertEquals(user1.getUserID(), reply.getUserID());
        assertEquals(8, reply.getPostID());

        //delete
        int listSizePreDelete = accessReplys.getAllReplys().size();
        deleted = accessReplys.deleteReply(reply);
        assertTrue(deleted);
        assertFalse(allReplies.contains(accessReplys.getReplyById(5)));
        allReplies = accessReplys.getReplyByPost(8);
        assertEquals(listSizePreDelete - 1, allReplies.size());

        accessReplys.clear();
        assertTrue(accessReplys.getAllReplys().isEmpty());
        deleted = accessPosts.deletePost(postToReply);
        assertTrue(deleted);
    }

    @Test
    public void testAccessRequests(){
        className = "AccessRequests";
        AccessRequests accessRequests = new AccessRequests();
        List<Request> allRequests;
        Request request = null;
        Request request2;

        //insert
        try{
            request = new Request("newGameNotInDB", user1.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        try {
            inserted = accessRequests.insertRequest(request);
        }catch (Duplicate duplicate){
            duplicate.printStackTrace();
        }
        assertTrue(inserted);
        allRequests = accessRequests.getAllRequests();
        assertTrue(allRequests.size() >= 1);
        assertTrue(allRequests.contains(request));

        request2 = accessRequests.getRequest("newGameNotInDB", user1.getUserID());
        assertEquals(request, request2);

        allRequests = accessRequests.getRequestsByGame("newGameNotInDB");
        assertEquals(1, allRequests.size());
        assertTrue(allRequests.contains(request));

        allRequests = accessRequests.getRequestsByUser(user1.getUserID());
        assertTrue(allRequests.size() >= 1);
        assertTrue(allRequests.contains(request));

        //get the list of requested games, sorted by request number, up to 5 games
        List<String> gamesByRequestNum = accessRequests.getGamesByRequestNum(5);
        assertTrue(gamesByRequestNum.contains(request.getGameName()));

        //delete
        int numReqsBeforeDel = accessRequests.getAllRequests().size();
        deleted = accessRequests.deleteRequest(request);
        assertTrue(deleted);
        allRequests = accessRequests.getAllRequests();
        assertFalse(allRequests.contains(request));
        assertEquals(numReqsBeforeDel-1, allRequests.size());

    }

    @Test
    public void testAccessReviews(){
        className = "AccessReviews";
        Review review = null;
        Review review2 = null;
        List<Review> allReviews;

        //insert
        try{
            review = new Review(1, "comment", game.getName(), user1.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReviews.insertReview(review);
        assertTrue(inserted);
        allReviews = accessReviews.getAllReviews();
        assertTrue(allReviews.size() >= 1 );
        assertTrue(allReviews.contains(review));

        try{
            review2 = new Review(2, "comment2", game.getName(), user2.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReviews.insertReview(review2);
        assertTrue(inserted);
        allReviews = accessReviews.getAllReviews();
        assertTrue(allReviews.size() >= 2);
        assertTrue(allReviews.contains(review2));

        int numReviewsGame1 = accessReviews.getReviewNumByGame(game.getName());
        assertTrue(numReviewsGame1 >= 2);

        allReviews = accessReviews.getReviewsByGame(game.getName());
        assertTrue(allReviews.size() >= 2);
        assertTrue(allReviews.contains(review));
        assertTrue(allReviews.contains(review2));

        allReviews = accessReviews.getReviewsByUser(user1.getUserID());
        assertTrue(allReviews.size() >= 1);
        assertTrue(allReviews.contains(review));

        allReviews = accessReviews.getReviewsByUser(user2.getUserID());
        assertTrue(allReviews.size() >= 1);
        assertTrue(allReviews.contains(review2));

        //udpate
        try{
            review2 = new Review(2, "differentComment", game.getName(), user1.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        updated = accessReviews.updateReview(review2);
        assertTrue(updated);
        review2 = accessReviews.getReviewById(2);
        assertEquals("differentComment", review2.getComment());
        assertEquals(game.getName(), review2.getGameName());
        assertEquals(user1.getUserID(), review2.getUserID());

        //delete
        int numReviewsPreDelete = accessReviews.getAllReviews().size();
        deleted = accessReviews.deleteReview(review);
        assertTrue(deleted);
        allReviews = accessReviews.getAllReviews();
        assertFalse(allReviews.contains(review));
        assertEquals(numReviewsPreDelete-1, allReviews.size());

        accessReviews.clear();
        assertTrue(accessReviews.getAllReviews().isEmpty());
    }

    @Test
    public void testAccessUsers(){
        className = "AccessUsers";
        List<User> allUsers;
        User newUser1 = null;
        User duplicate = null;

        try{
            newUser1 = new RegisteredUser("newUserReg", "newPass");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessUsers.insertUser(newUser1);
        assertTrue(inserted);

        //duplicate user
        try{
            duplicate = new RegisteredUser("newUserReg", "anotherPass");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessUsers.insertUser(duplicate);
        assertFalse(inserted);

        allUsers = accessUsers.getAllUsers();
        assertTrue(allUsers.size() >= 1);
        assertTrue(allUsers.contains(newUser1));

        accessUsers.setActiveUser(newUser1);
        newUser1 = accessUsers.getActiveUser();
        assertEquals("newUserReg", newUser1.getUserID());

        allUsers = accessUsers.getUsersByIDImplicit("new");
        assertTrue(allUsers.size() >= 1);
        assertTrue(allUsers.contains(newUser1));

        //update
        try{
            newUser1 = new RegisteredUser("newUserReg", "changedPassword");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        updated = accessUsers.updateUser(newUser1);
        assertTrue(updated);
        duplicate = accessUsers.getUserByID("newUserReg");
        assertEquals(newUser1, duplicate);
        assertTrue(accessUsers.getAllUsers().contains(newUser1));

        //delete
        int numUsersPreDelete = accessUsers.getAllUsers().size();
        deleted = accessUsers.deleteUser(newUser1);
        assertTrue(deleted);
        assertFalse(accessUsers.getAllUsers().contains(newUser1));
        assertEquals(numUsersPreDelete-1, accessUsers.getAllUsers().size());
    }

    @Test
    public void testAccessVoteReplys(){
        className = "AccessVoteReplys";
        accessVoteReplys = new AccessVoteReplys();
        accessPosts = new AccessPosts();
        accessReplys = new AccessReplys();
        List<VoteReply> allVoteReps;
        VoteReply voteReply;
        VoteReply test;
        Post post = null;
        Reply reply = null;

        //must have a post, and a reply to that post to vote on
        try{
            post = new Post(2,"title", "content", user2.getUserID());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessPosts.insertPost(post);
        assertTrue(inserted);
        try{
            reply = new Reply(5, "content", user1.getUserID(), 2);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReplys.insertReply(reply);
        assertTrue(inserted);

        //insert
        voteReply = new VoteReply(new Upvote(user2.getUserID()), reply.getID());
        inserted = accessVoteReplys.insertVoteReply(voteReply);
        assertTrue(inserted);
        test = accessVoteReplys.getVoteReply(user2.getUserID(), reply.getID());
        assertEquals(voteReply, test);
        allVoteReps = accessVoteReplys.getVoteReplysByReply(reply.getID());
        assertTrue(allVoteReps.size() >= 1);
        assertTrue(allVoteReps.contains(voteReply));

        //update
        voteReply = new VoteReply(new Downvote(user2.getUserID()), reply.getID());
        updated = accessVoteReplys.updateVoteReply(voteReply);
        assertTrue(updated);
        test = accessVoteReplys.getVoteReply(user2.getUserID(), reply.getID());
        assertEquals(test, voteReply);
        assertEquals(-1, test.getVoteI().getValue()); //it is now a downvote
        allVoteReps = accessVoteReplys.getVoteReplysByReply(reply.getID());
        assertTrue(allVoteReps.size() >= 1);
        assertTrue(allVoteReps.contains(test));

        //delete
        deleted = accessVoteReplys.deleteVoteReply(voteReply);
        assertTrue(deleted);
        voteReply = accessVoteReplys.getVoteReply(user2.getUserID(), reply.getID());
        assertNull(voteReply);
        allVoteReps = accessVoteReplys.getVoteReplysByReply(reply.getID());

        //undo changes
        deleted = accessReplys.deleteReply(reply);
        assertTrue(deleted);
        deleted = accessPosts.deletePost(post);
        assertTrue(deleted);
        accessVoteReplys.clear();
        accessReplys.clear();
        accessPosts.clear();
    }
}

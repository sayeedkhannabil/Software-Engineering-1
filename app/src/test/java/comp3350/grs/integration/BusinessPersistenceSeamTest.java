package comp3350.grs.integration;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.business.AccessGames;
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessReviews;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Review;

public class BusinessPersistenceSeamTest {
    private String className;
    private boolean inserted, updated, deleted;

    @Before
    public void before(){
        System.out.println("\nStarting Integration test");
        Services.closeDataAccess();
        Services.createDataAccess(Main.testDbName);
        inserted = false;
        updated = false;
        deleted = false;
    }

    @After
    public void after(){
        Services.closeDataAccess();
        System.out.println("Finished Integration test of "+className+" to persistence");
    }

    @Test
    public void testAccessGames(){
        className = "AccessGames";
        AccessGames accessGames = new AccessGames();
        AccessRatings accessRatings = new AccessRatings();
        AccessReviews accessReviews = new AccessReviews();
        List<Game> allGames;
        Game game;
        Game game2 = null;
        Rating rating = null;
        Review review = null;
        String gameName;
        int listSize;

        allGames = accessGames.getAllGames();
        assertNotNull(allGames);
        listSize = allGames.size();
        assertTrue(listSize > 0);
        game = accessGames.getSequential();
        gameName = game.getName();
        assertEquals(game, accessGames.findGame(gameName));
        assertTrue(allGames.contains(game));

        //insert
        inserted = accessGames.insertGame(game);
        assertFalse(inserted); //because the game already exists

        try{
            game = new Game("newGame", "dev", "desc", 5.00, new ArrayList<>());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessGames.insertGame(game);
        assertTrue(inserted);

        try{
            game2 = new Game("otherGame", "otherDev", "desc", 100, new ArrayList<>());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessGames.insertGame(game2);
        assertTrue(inserted);

        //update
        try {
            game = new Game("newGame", "different", "different", 20.00, new ArrayList<>());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        updated = accessGames.updateGame(game);
        assertTrue(updated);
        game = accessGames.findGame("newGame");
        assertEquals("newGame", game.getName());
        assertEquals("different", game.getDev());
        assertEquals("different", game.getDescription());
        assertEquals(20.00, game.getPrice(), 0);
        assertNotNull(game.getGenres());
        listSize = accessGames.getAllGames().size();

        //delete
        game = accessGames.findGame("newGame");
        assertNotNull(game);
        deleted = accessGames.deleteGame(game);
        assertTrue(deleted);
        allGames = accessGames.getAllGames();
        assertFalse(allGames.contains(game));
        assertEquals(listSize-1, allGames.size());
        assertNull(accessGames.findGame("newGame"));
        game = accessGames.findGame("otherGame");
        deleted = accessGames.deleteGame(game);
        assertTrue(deleted);

        //add ratings and review to test sort methods (which communicate with persistence)
        try{
            rating = new Rating(5.0, "game1", "user1");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessRatings.insertRating(rating);
        assertTrue(inserted);
        try{
            rating = new Rating(3.0, "game2", "user2");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessRatings.insertRating(rating);
        assertTrue(inserted);
        try{
            rating = new Rating(4.0, "game2", "user1");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessRatings.insertRating(rating);
        assertTrue(inserted);

        try{
            review = new Review("comment", "game3", "user2");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReviews.insertReview(review);
        assertTrue(inserted);

        //ascending + descending rating sort
        allGames = accessGames.descendingRatingSort();
        game = accessGames.findGame("game2");
        assertEquals(game, allGames.get(0));

        allGames = accessGames.ascendingRatingSort();
        game = accessGames.findGame("game3");
        assertEquals(game, allGames.get(0));

        //ascending + descending review sort
        allGames = accessGames.descendingReviewSort();
        assertEquals(accessGames.findGame("game3"), allGames.get(0));

        allGames = accessGames.ascendingReviewSort();
        assertNotEquals(accessGames.findGame("game3"), allGames.get(0));

        //game by name implicit
        allGames = accessGames.getGamesByNameImplicit("game");
        assertEquals(accessGames.getAllGames().size(), allGames.size()); //all games begin with game

        accessRatings.clear();
        accessReviews.clear();
    }

    @Test
    public void testAccessPosts(){
        className = "AccessPosts";
        AccessPosts accessPosts = new AccessPosts();
        List<Post> allPosts;
        int postID = 1;
        Post post = null;

        //insert
        try{
            post = new Post(postID, "NewPost", "content", "user1");
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
        allPosts = accessPosts.getPostsByUser("user1");
        assertEquals(1, allPosts.size());

        //can't insert same post multiple times
        inserted = accessPosts.insertPost(post);
        assertFalse(inserted);

        //update
        try{
            post = new Post(postID, "NewTitle", "newContent", "user1");
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
        allPosts = accessPosts.getPostsByUser("user1");
        assertTrue(allPosts.isEmpty());
        allPosts = accessPosts.getAllPosts();
        assertTrue(allPosts.isEmpty());

        accessPosts.clear();
        assertTrue(accessPosts.getAllPosts().isEmpty());
    }

    @Test
    public void testAccessRatings(){
        className = "AccessRatings";
        AccessRatings accessRatings = new AccessRatings();
        List<Rating> allRatings;
        Rating rating = null;

        //insert
        try{
            rating = new Rating(5.0, "game1","user2");
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
        allRatings = accessRatings.getRatingsByUser("user2");
        assertEquals(1, allRatings.size());
        allRatings = accessRatings.getRatingsByGame("game1");
        assertEquals(1, allRatings.size());
        Rating rating2 = accessRatings.getRating("game1", "user2");
        assertEquals(rating, rating2);
        int ratingNumByGame = accessRatings.getRatingNumByGame("game1");
        assertTrue(ratingNumByGame >= 1);
        int ratingNumByUser = accessRatings.getRatingNumByUser("user2");
        assertEquals(1, ratingNumByUser);

        //can't insert same rating multiple times
        inserted = accessRatings.insertRating(rating);
        assertFalse(inserted);

        //update
        try{
            rating2 = new Rating(2.0, "game1", "user2");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        assertNotNull(rating2);
        assertTrue(rating2.valid());
        updated = accessRatings.updateRating(rating2);
        assertTrue(updated);
        allRatings = accessRatings.getAllRatings();
        assertTrue(allRatings.size() >= 1);
        rating = accessRatings.getRating("game1", "user2");
        assertTrue(allRatings.contains(rating));
        assertEquals(2.0, rating.getRatingValue(), 0);


        //delete
        deleted = accessRatings.deleteRating(rating);
        assertTrue(deleted);
        rating2 = accessRatings.getRating("game1", "user2");
        assertNull(rating2);
        allRatings = accessRatings.getRatingsByGame("game1");
        assertTrue(allRatings.isEmpty());
        allRatings = accessRatings.getAllRatings();
        assertTrue(allRatings.isEmpty());
    }

    @Test
    public void testAccessReplys(){
        className = "AccessReplys";
        AccessReplys accessReplys = new AccessReplys();
        List<Reply> allReplies;
        Reply reply = null;

        //insert
        try{
            reply = new Reply(5,"comment", "user1", 6);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReplys.insertReply(reply);
        assertTrue(inserted);
        try{
            reply = new Reply("comment", "user2", 4);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReplys.insertReply(reply);
        assertTrue(inserted);
        try{
            reply = new Reply(5, "newComment", "user2", 4);
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        inserted = accessReplys.insertReply(reply);
        assertFalse(inserted);

        accessReplys.clear();
        assertTrue(accessReplys.getAllReplys().isEmpty());
    }

    @Test
    public void testAccessRequests(){
        className = "AccessRequests";

        //representative test cases

    }

    @Test
    public void testAccessReviews(){
        className = "AccessReviews";

        //representative test cases

    }

    @Test
    public void testAccessUsers(){
        className = "AccessUsers";
        //representative test cases

    }

    @Test
    public void testAccessVoteReplys(){
        className = "AccessVoteReplys";
        //representative test cases

    }
}

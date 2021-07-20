package comp3350.grs.persistence;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;

import static org.junit.Assert.*;


public class DataAccessITest{
    private static DataAccessI dataAccessI;
    private User user1,user2,user3;
    private Game game1,game2,game3;
    private Review review1,review2,review3;
    private Rating rating1,rating2,rating3;
    private List<User> userList;
    private List<Game> gameList;
    private List<Review> reviewList;
    private List<Rating> ratingList;
    private String userID,userID2,password,password2,gameName,developer,
            description,reviewContent;
    private int reviewID;
    private double price,rating;
    private boolean success;
    private List<String> genreList;
    private static String dbName="TestDB";
    private static String dbPath="database/TestDB";


    @Before
    public void before(){
//        dataAccessI=new DataAccessObject("TestDB");
        dataAccessI=new DataAccessStub(dbName);
        dataAccessI.open(dbPath);
        dataAccessI.deleteDatabase();
        dataAccessI.open(dbPath);
        dataAccessI.clearAllData();
        user1=null;
        user2=null;
        user3=null;
        game1=null;
        game2=null;
        game3=null;
        userList=new ArrayList<User>();
        gameList=new ArrayList<Game>();
        userID=null;
        userID2=null;
        password=null;
        password2=null;
        gameName=null;
        developer=null;
        description=null;
        price=0.0;
        reviewID=0;
        rating=0.0;
        review1=null;
        review2=null;
        review3=null;
        rating1=null;
        rating2=null;
        rating3=null;
        success=false;
        reviewList=new ArrayList<Review>();
        ratingList=new ArrayList<Rating>();
        genreList=new ArrayList<String>();
    }

    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
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

        dataAccessI.clearAllData();
        userList=dataAccessI.getAllUsers();
        assertEquals(0,userList.size());
        gameList=dataAccessI.getAllGames();
        assertEquals(0,gameList.size());

        genreList.clear();
        try {
            user1=new Guest();
            user2=new RegisteredUser("user2","password");
            user3=new RegisteredUser("user3","password");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try {
            game1=new Game("game1","dev1","desc1",1.0,genreList);
            game2=new Game("game2","dev2","desc2",2.0,genreList);
            game3=new Game("game3","dev3","desc3",3.0,genreList);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        dataAccessI.insertUser(user1);
        dataAccessI.insertUser(user2);
        dataAccessI.insertUser(user3);
        dataAccessI.insertGame(game1);
        dataAccessI.insertGame(game2);
        dataAccessI.insertGame(game3);

        userList=dataAccessI.getUsersByIDImplicit("user%");
        assertEquals(2,userList.size());
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

        Review review;
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

        Rating rating;
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

        User user;
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

        Game game;
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

        dataAccessI.insertUser(user1);
        dataAccessI.insertGame(game1);
        try {
            review1=new Review(13,"comment","gameName","Guest");
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
            rating1=new Rating(3.0,"gameName","Guest");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertRating(rating1);
        rating= dataAccessI.getRating("gameName","Guest");
        assertNotNull(rating);
        dataAccessI.clearRatings();
        rating= dataAccessI.getRating("gameName","Guest");
        assertNull(rating);
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

        dataAccessI.close();
        dataAccessI= Services.createDataAccess(new DataAccessStub());
        dataAccessI.clearAllData();
        try {
            user1=new RegisteredUser("myUserID","myPass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertUser(user1);
        userList= dataAccessI.getUsersByIDImplicit("myUserID");
        assertEquals(1,userList.size());

    }

    @Test
    public void testEdge(){

        try {
            user1=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        dataAccessI.insertUser(user1);
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

    }
}
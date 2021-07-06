package comp3350.grs.persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class DataAccessITest {
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


    @Before
    public void initiateDB(){

        dataAccessI=new DataAccessObject("TestDB");
//       dataAccessI=new DataAccessStub("TestDB");

        dataAccessI.open("database/TestDB");
        dataAccessI.clearDatabase();
        dataAccessI.open("database/TestDB");
        dataAccessI.clearTable();
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
    public static void closeDatabase(){
        dataAccessI.close();
    }

    @Test
    public void testTypical(){
        try {
            user1=new Guest();
            success=dataAccessI.insertUser(user1);
            assert (success);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==1);
            user2=userList.get(0);
            assert (user2.equals(user1));
            user2=dataAccessI.getUserByID("Guest");
            assert (user2.getUserID().equals("Guest"));

            userID="myUserID";
            password="myPassword";
            user1=new RegisteredUser(userID,password);
            success=dataAccessI.insertUser(user1);
            assert (success);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==2);
            user2=dataAccessI.getUserByID(userID);
            assert (user2.getUserID().equals(userID));
            user2=(RegisteredUser)user2;
            assert (((RegisteredUser) user2).getPassword().equals(password));

            password2="anotherPassword";
            user1=new RegisteredUser(userID,password2);
            dataAccessI.updateUser(user1);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==2);
            user2=(RegisteredUser) dataAccessI.getUserByID(userID);
            assert (user2.getUserID().equals(userID));
            assert (((RegisteredUser) user2).getPassword().equals(password2));
            assertFalse(((RegisteredUser) user2).getPassword().equals(password));


            dataAccessI.deleteUser(user1);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==1);
            user2= dataAccessI.getUserByID(userID);
            assertNull(user2);

            game3=new Game("otherGame1");
            dataAccessI.insertGame(game3);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==1);


            gameName="myGame";
            developer="myDeveloper";
            description="myDescription";
            price=9.99;
            genreList.clear();
            genreList.add("genre1");
            genreList.add("genre2");
            genreList.add("genre3");
            game1=new Game(gameName,developer,description,price,genreList);
            success=dataAccessI.insertGame(game1);
            assert (success) ;
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==2);
            game2=dataAccessI.getGameByName(gameName);
            assert (game2.getName().equals(gameName));
            assert (game2.getDev().equals(developer));
            assert (game2.getDescription().equals(description));
            assertEquals(price,game2.getPrice(),0.01);
            genreList=game2.getGenres();
            assert (genreList.size()==3);
            assert (genreList.contains("genre1"));
            assert (genreList.contains("genre2"));
            assert (genreList.contains("genre3"));

            game3=new Game("otherGame2");
            dataAccessI.insertGame(game3);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==3);

            developer="anotherDeveloper";
            description="anotherDescription";
            price=99.99;
            genreList.clear();
            genreList.add("genre1");
            genreList.add("genre4");

            game1=new Game(gameName,developer,description,price,genreList);
            dataAccessI.updateGame(game1);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==3);
            game2=dataAccessI.getGameByName(gameName);
            assert (game2.getName().equals(gameName));
            assert (game2.getDev().equals(developer));
            assert (game2.getDescription().equals(description));
            assertEquals(price,game2.getPrice(),0.01);
            genreList=game2.getGenres();
            assert (genreList.size()==2);
            assert (genreList.contains("genre1"));
            assert (genreList.contains("genre4"));

            dataAccessI.deleteGame(game1);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==2);
            game2=dataAccessI.getGameByName(gameName);
            assertNull(game2);

            dataAccessI.clearTable();
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==0);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==0);

            genreList.clear();
            user1=new Guest();
            user2=new RegisteredUser("user2","password");
            user3=new RegisteredUser("user3","password");
            game1=new Game("game1","dev1","desc1",1.0,genreList);
            game2=new Game("game2","dev2","desc2",2.0,genreList);
            game3=new Game("game3","dev3","desc3",3.0,genreList);

            dataAccessI.insertUser(user1);
            dataAccessI.insertUser(user2);
            dataAccessI.insertUser(user3);
            dataAccessI.insertGame(game1);
            dataAccessI.insertGame(game2);
            dataAccessI.insertGame(game3);

            userList=dataAccessI.getUsersByIDImplicit("user*");
            assert (userList.size()==2);
            assert (userList.contains(user2));
            assert (userList.contains(user3));
            userList=dataAccessI.getUsersByIDImplicit("user");
            assert (userList.size()==0);

            gameList=dataAccessI.getGamesByNameImplicit("game?");
            assert (gameList.size()==3);
            assert (gameList.contains(game1));
            assert (gameList.contains(game2));
            assert (gameList.contains(game3));
            gameList=dataAccessI.getGamesByNameImplicit("ga*");
            assert (gameList.size()==3);
            gameList=dataAccessI.getGamesByNameImplicit("game");
            assert (gameList.size()==0);

            review1=new Review("content1","game1","Guest");
            review2=new Review(5,"content2","game3","Guest");
            review3=new Review("content3","game3","user2");
            rating1=new Rating(4.5,"game1","user2");
            rating2=new Rating(4.6,"game1","user3");
            rating3=new Rating(4.0,"game2","user2");

            success= dataAccessI.insertReview(review1);
            assert success;
            success= dataAccessI.insertReview(review2);
            assert success;
            success= dataAccessI.insertReview(review3);
            assert success;
            success= dataAccessI.insertRating(rating1);
            assert success;
            success= dataAccessI.insertRating(rating2);
            assert success;
            success= dataAccessI.insertRating(rating3);
            assert success;

            reviewList=dataAccessI.getAllReviews();
            assert (reviewList.size()==3);
            reviewList=dataAccessI.getReviewsByGame("game3");
            assert (reviewList.size()==2);
            reviewList=dataAccessI.getReviewsByGame("game1");
            assert (reviewList.size()==1);
            reviewList=dataAccessI.getReviewsByGame("game2");
            assert (reviewList.size()==0);
            reviewList=dataAccessI.getReviewsByUser("Guest");
            assert (reviewList.size()==2);
            reviewList=dataAccessI.getReviewsByUser("user2");
            assert (reviewList.size()==1);
            assert (reviewList.get(0).equals(review3));
            reviewList=dataAccessI.getReviewsByUser("user1");
            assert (reviewList.size()==0);

            Review review;
            review=dataAccessI.getReviewByID(5);
            assert (review1.getComment().equals("content2"));
            assert (review1.getUserID().equals("Guest"));
            assert (review1.getGameName().equals("game3"));
            review=new Review(5,"newContent","game2","user2" );
            success= dataAccessI.updateReview(review);
            assert success;
            review= dataAccessI.getReviewByID(5);
            assert (review1.getComment().equals("newContent"));
            assert (review1.getUserID().equals("user2"));
            assert (review1.getGameName().equals("game2"));
            success=dataAccessI.deleteReview(review);
            review= dataAccessI.getReviewByID(5);
            assertNull(review);

            ratingList=dataAccessI.getAllRatings();
            assert (ratingList.size()==3);
            ratingList=dataAccessI.getRatingsByGame("game1");
            assert (ratingList.size()==2);
            ratingList=dataAccessI.getRatingsByGame("game2");
            assert (ratingList.size()==1);
            ratingList=dataAccessI.getRatingsByGame("game3");
            assert (ratingList.size()==0);
            ratingList=dataAccessI.getRatingsByUser("user2");
            assert (ratingList.size()==2);
            ratingList=dataAccessI.getRatingsByUser("user3");
            assert (ratingList.size()==1);
            assert (ratingList.get(0).equals(rating2));
            ratingList=dataAccessI.getRatingsByUser("user1");
            assert (ratingList.size()==0);

            Rating rating;
            rating=dataAccessI.getRating("game1","user3");
            assert (rating.getUserID().equals("user3"));
            assert (rating.getGameName().equals("game1"));
            assertEquals(rating.getRatingValue(),4.6,0.01);
            assert (rating.getGameName().equals("game1"));
            assert (rating.getUserID().equals("user3"));
            rating=new Rating(3.0,"game1","user3");
            success= dataAccessI.updateRating(rating);
            assert success;
            rating=dataAccessI.getRating("game1","user3");
            assert (rating.getUserID().equals("user3"));
            assert (rating.getGameName().equals("game1"));
            assertEquals(rating.getRatingValue(),3.0,0.01);
            success=dataAccessI.deleteRating(rating);
            rating= dataAccessI.getRating("game1","user3");
            assertNull(rating);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testNull(){
        try {
            user1=new Guest();
            dataAccessI.insertUser(user1);
            userList=dataAccessI.getUsersByIDImplicit(null);
            assert (userList.size()==0);
            user2=dataAccessI.getUserByID(null);
            assert (user1==null);
            success= dataAccessI.insertUser(null);
            assertFalse(success);
            success= dataAccessI.updateUser(null);
            assertFalse(success);
            success= dataAccessI.deleteUser(null);
            assertFalse(success);

            genreList=new ArrayList<>();
            genreList.add("genre");
            game1=new Game("gameName","dev","desc",1.0,genreList);
            dataAccessI.insertGame(game1);
            gameList=dataAccessI.getGamesByNameImplicit(null);
            assert (gameList.size()==0);
            game2= dataAccessI.getGameByName(null);
            assertNull(game2);
            success=dataAccessI.insertGame(null);
            assertFalse(success);
            success=dataAccessI.updateGame(null);
            assertFalse(success);
            success=dataAccessI.deleteGame(null);
            assertFalse(success);

            user1=new RegisteredUser("userID","123");
            dataAccessI.insertUser(user1);
            review1=new Review("comment","gameName","userID");
            dataAccessI.insertReview(review1);
            success=dataAccessI.insertReview(null);
            assertFalse(success);
            success=dataAccessI.updateReview(null);
            assertFalse(success);
            success=dataAccessI.deleteReview(null);
            assertFalse(success);
            reviewList=dataAccessI.getReviewsByGame(null);
            assert (reviewList.size()==0);
            reviewList=dataAccessI.getReviewsByUser(null);
            assert (reviewList.size()==0);

            rating1=new Rating(4.0,"gameName","userID");
            dataAccessI.insertRating(rating1);
            success=dataAccessI.insertRating(null);
            assertFalse(success);
            success=dataAccessI.updateRating(null);
            assertFalse(success);
            success=dataAccessI.deleteRating(null);
            assertFalse(success);
            ratingList=dataAccessI.getRatingsByGame(null);
            assert (ratingList.size()==0);
            ratingList=dataAccessI.getRatingsByUser(null);
            assert (ratingList.size()==0);
            rating2= dataAccessI.getRating(null,null);
            assertNull(rating2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testEdge(){
        try {
            user1=new Guest();
            dataAccessI.insertUser(user1);
            userList=dataAccessI.getUsersByIDImplicit("");
            assert (userList.size()==0);
            user2= dataAccessI.getUserByID("");
            assertNull(user2);

            genreList=new ArrayList<>();
            game1=new Game("","","",1.0,genreList);
            success= dataAccessI.insertGame(game1);
            assert success;
            game2= dataAccessI.getGameByName("");
            assertEquals(game1,game2);
            gameList=dataAccessI.getGamesByNameImplicit("");
            assert gameList.size()==1;
            game2=gameList.get(0);
            assertEquals(game1,game2);
            game1=new Game("","","",2.0,genreList);
            success= dataAccessI.updateGame(game1);
            assert success;
            game2= dataAccessI.getGameByName("");
            assertEquals(game2.getPrice(),2.0,0.01);
            success=dataAccessI.deleteGame(game1);
            assert success;
            game2= dataAccessI.getGameByName("");
            assertNull(game2);

            game1=new Game("1","","",1.0,genreList);
            dataAccessI.insertGame(game1);
            assert success;
            game2= dataAccessI.getGameByName("1");
            assertEquals(game1,game2);
            gameList=dataAccessI.getGamesByNameImplicit("1");
            assert gameList.size()==1;
            game2=gameList.get(0);
            assertEquals(game1,game2);
            game1=new Game("1","","",3.0,genreList);
            success= dataAccessI.updateGame(game1);
            assert success;
            game2= dataAccessI.getGameByName("1");
            assertEquals(game2.getPrice(),3.0,0.01);
            success=dataAccessI.deleteGame(game1);
            assert success;
            game2= dataAccessI.getGameByName("1");
            assertNull(game2);

            game1=new Game("gameName");
            dataAccessI.insertGame(game1);
            user1=new RegisteredUser("userID","password");
            success= dataAccessI.insertUser(user1);
            assert success;
            review1=new Review(-1,"comment","gameName","userID");
            success=dataAccessI.insertReview(review1);
            assert success;
            reviewList=dataAccessI.getReviewsByGame("gameName");
            assert reviewList.size()==1;
            review2=reviewList.get(0);
            assert (review1.equals(review2));
            reviewList=dataAccessI.getReviewsByUser("userID");
            assert reviewList.size()==1;
            review2=reviewList.get(0);
            assert (review1.equals(review2));
            review2= dataAccessI.getReviewByID(-1);
            assert (review1.equals(review2));
            review1=new Review(-1,"newComment","gameName","userID");
            review2= dataAccessI.getReviewByID(-1);
            assert (review2.getComment().equals("newComment"));
            success= dataAccessI.deleteReview(review1);
            assert success;
            review2= dataAccessI.getReviewByID(-1);
            assertNull(review2);

            rating1=new Rating(0.0,"gameName","userID");
            success=dataAccessI.insertRating(rating1);
            assert success;
            ratingList=dataAccessI.getRatingsByUser("userID");
            assert ratingList.size()==1;
            rating2=ratingList.get(0);
            assert (rating1.equals(rating2));
            ratingList=dataAccessI.getRatingsByGame("gameName");
            assert ratingList.size()==1;
            rating2=ratingList.get(0);
            assert (rating1.equals(rating2));
            rating2= dataAccessI.getRating("gameName","userID");
            assert (rating1.equals(rating2));
            rating1=new Rating(1.0,"gameName","userID");
            success= dataAccessI.updateRating(rating1);
            assert success;
            rating2= dataAccessI.getRating("gameName","userID");
            assert (rating1.equals(rating2));
            success=dataAccessI.deleteRating(rating1);
            assert success;
            rating2= dataAccessI.getRating("gameName","userID");
            assertNull(rating2);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
    }
}
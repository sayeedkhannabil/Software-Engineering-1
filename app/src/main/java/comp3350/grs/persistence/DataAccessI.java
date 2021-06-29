package comp3350.grs.persistence;


import java.sql.SQLException;
import java.util.List;

import comp3350.grs.objects.Game;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;

public interface DataAccessI
{
	void open(String dbPath);

	void close();

	void clearDatabase();//delete all the tables

	void clearTable();//clear the content of table without deleting the table
	// itself

	List<User> getAllUsers();

	List<User> getUsersByIDImplicit(String userIDImp);

	User getUserByID(String userID);

	boolean insertUser(User user);

	boolean updateUser(User user);

	boolean deleteUser(User user);

	List<Game> getAllGames();

	Game getGameByName(String gameName);

	List<Game> getGamesByNameImplicit(String gameNameImp);

	boolean insertGame(Game game);

	boolean updateGame(Game game);

	boolean deleteGame(Game game);

	boolean insertReview(Review review);

	boolean updateReview(Review review);

	boolean deleteReview(Review review);

	List<Review> getAllReviews();

	List<Review> getReviewsByGame(String gameName);

	List<Review> getReviewsByUser(String userID);

	Review getReviewByID(int reviewID);

	boolean insertRating(Rating theRating);

	boolean updateRating(Rating theRating);

	boolean deleteRating(Rating theRating);

	List<Rating> getAllRatings();

	List<Rating> getRatingsByGame(String gameName);

	List<Rating> getRatingsByUser(String userID);

	Rating getRating(String gameName,String userID);


}

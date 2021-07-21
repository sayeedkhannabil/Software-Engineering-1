package comp3350.grs.persistence;


import java.util.List;

import comp3350.grs.objects.Game;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Request;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;

public interface DataAccessI
{
	void open(String dbPath);

	void close();

	void deleteDatabase();//delete the database itself

	void clearAllData();//clear data of the database,without delete the database

	void clearUsers();

	void clearGames();

	void clearReviews();

	void clearRatings();

	void clearRequests();

	void clearReply();

	boolean insertUser(User user);

	boolean updateUser(User user);

	boolean deleteUser(User user);

	List<User> getAllUsers();

	List<User> getUsersByIDImplicit(String userIDImp);//implicit search

	User getUserByID(String userID);

	boolean insertGame(Game game);

	boolean updateGame(Game game);

	boolean deleteGame(Game game);

	List<Game> getAllGames();

	Game getGameByName(String gameName);

	List<Game> getGamesByNameImplicit(String gameNameImp);//implicit search

	boolean insertReview(Review review);

	boolean updateReview(Review review);

	boolean deleteReview(Review review);

	List<Review> getAllReviews();

	List<Review> getReviewsByGame(String gameName);

	List<Review> getReviewsByUser(String userID);

	Review getReviewByID(int reviewID);

	boolean insertRating(Rating rating);

	boolean updateRating(Rating rating);

	boolean deleteRating(Rating rating);

	List<Rating> getAllRatings();

	List<Rating> getRatingsByGame(String gameName);

	List<Rating> getRatingsByUser(String userID);

	Rating getRating(String gameName,String userID);

	boolean insertRequest(Request request);

	boolean deleteRequest(Request request);

	List<Request> getAllRequests();

	List<Request> getRequestsByGame(String gameName);

	List<Request> getRequestsByUser(String userID);

	Request getRequest(String gameName,String userID);

	boolean insertReply(Reply reply);

	boolean updateReply(Reply reply);

	boolean deleteReply(Reply reply);

	List<Reply> getAllReply();

	List<Reply> getReplyByGame(String gameName);

	List<Reply> getReplyByUser(String userId);

	Reply getReply(String gameName, String userId);



}

package comp3350.grs.persistence;


import java.util.List;

import comp3350.grs.objects.Game;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Request;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;
import comp3350.grs.objects.VoteReply;

public interface DataAccessI {
	String getDbName();

	void open(String dbPath);

	void close();

	void deleteDatabase();//delete the database itself

	void clearAllData();//clear data of the database,without delete the database

	void clearUsers();

	void clearGames();

	void clearReviews();

	void clearRatings();

	void clearRequests();

	void clearReplys();

	void clearVoteReplys();

	void clearPosts();

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

	Rating getRating(String gameName, String userID);

	boolean insertRequest(Request request);

	boolean deleteRequest(Request request);

	List<Request> getAllRequests();

	List<Request> getRequestsByGame(String gameName);

	List<Request> getRequestsByUser(String userID);

	Request getRequest(String gameName, String userID);
	//get a list of game name by the number of user who requested it, order
	// by the number of user, maxium size of the list is limit
	List<String> getGamesOrderByRequestNum(int limit);

	boolean insertVoteReply(VoteReply voteReply);

	boolean updateVoteReply(VoteReply voteReply);

	boolean deleteVoteReply(VoteReply voteReply);
	//get a list of voteReplys by its replyID
	List<VoteReply> getVoteReplysByReply(int replyID);

	VoteReply getVoteReply(String userID, int replyID);

	boolean insertReply(Reply reply);

	boolean updateReply(Reply reply);

	boolean deleteReply(Reply reply);

	List<Reply> getAllReplys();
	//get a list of replys by its userID
	List<Reply> getReplysByUser(String userID);
	//get a list of replys by its psotID
	List<Reply> getReplysByPost(int postID);
	//get a reply by its ID
	Reply getReplyByID(int replyID);

	boolean insertPost(Post post);

	boolean updatePost(Post post);

	boolean deletePost(Post post);

	List<Post> getAllPosts();
	//get a list of post by its userID
	List<Post> getPostsByUser(String userId);

	Post getPostByID(int postID);
}
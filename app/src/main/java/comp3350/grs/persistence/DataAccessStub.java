package comp3350.grs.persistence;

// the stub database which stores users and games

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Review;


public class DataAccessStub extends DataAccess implements DataAccessI {
	//did not add these to DataAccess, added here instead
	private List<Review> reviews;
	private List<Rating> ratings;

	public DataAccessStub(String dbName) {
		super(dbName);
		reviews = new ArrayList<>();
		ratings = new ArrayList<>();
	}

	public DataAccessStub() {
		super();
		reviews = new ArrayList<>();
		ratings = new ArrayList<>();
	}

	@Override
	public void open(String dbPath) {
		super.open(dbPath);
		dbType = "stub";
	}

	public void close() {
		System.out.println("Closed " +dbType +" database " +dbName);
	}

	public void clearDatabase(){
		users=null;
		games=null;
	}

	public void clearTable(){
		users.clear();
		games.clear();
	}

	public boolean insertUser(User newUser) {
		return users.add(newUser);
	}

	public boolean updateUser(User currentUser) {
		int index;
		boolean updated = false;

		index = users.indexOf(currentUser);
		if (index >= 0) {
			users.set(index, currentUser);
			updated = true;
		}

		return updated;
	}

	public boolean deleteUser(User user) {
		int index;
		boolean deleted = false;

		index = users.indexOf(user);
		if (index >= 0) {
			users.remove(index);
			deleted = true;
		}

		return deleted;
	}

	//get all the users in database
	public List<User> getAllUsers() {
		List<User> usersCopy = new ArrayList<>();
		User toCopy = null;
		User copy = null;

		for(int i = 0; i < users.size(); i++){
			toCopy = users.get(i);
			if(toCopy instanceof Guest) {
				try {
					copy = new Guest();
				} catch (IncorrectFormat incorrectFormat) {
					System.out.println(incorrectFormat.getMessage());
				}
			}
			else if(toCopy instanceof RegisteredUser) {
				if(((RegisteredUser) toCopy).getPassword() != null) {
					try{
						copy = new RegisteredUser(toCopy.getUserID(),((RegisteredUser) toCopy).getPassword());
					}
					catch (IncorrectFormat incorrectFormat) {
						System.out.println(incorrectFormat.getMessage());
					}
				}
				else
				{
					try{
						copy = new RegisteredUser(toCopy.getUserID());
					}
					catch (IncorrectFormat incorrectFormat) {
						System.out.println(incorrectFormat.getMessage());
					}
				}
			}
			usersCopy.add(copy);
		}

		//we want to return a copy so the original can't be modified through the list returned
		return usersCopy;
	}

	public List<User> getUsersByIDImplicit(String userNameImplicit) {
		List<User> res = new ArrayList<>();
		List<User> searchList = this.getAllUsers();
		int searchLength = userNameImplicit.length();
		char currChar;
		String userName, implicit = null;
		User curr;

		if (searchLength > 0) {
			for (int i = 0; i < searchList.size(); i++) {
				curr = searchList.get(i);
				userName = curr.getUserID();
				for(int j = 0; j < searchLength; j++){
					currChar = userNameImplicit.charAt(j);
					if(currChar != '%' && currChar != '*' && currChar != '?'){
						implicit += currChar;
					}
					if(userName.contains(implicit)){
						res.add(curr);
					}
				}
			}
		} else {
			System.out.println("Please input valid user ID.");
		}
		if (res.size() == 0 && searchLength > 0) {
			System.out.println("Not found.");
		}

		return res;
	}

	//get a specific user
	public User getUserByID(String userID){
		User toReturn = null;
		boolean found = false;

		if(userID != null){
			for(int i = 0; i < users.size() && !found; i++){
				if(users.get(i).getUserID().equals(userID)){
					toReturn = users.get(i);
					found = true;
				}
			}
		}
		return toReturn;
	}

	public boolean insertGame(Game toAdd) {
		return games.add(toAdd);
	}

	public boolean updateGame(Game toUpdate) {
		boolean updated = false;
		int index = games.indexOf(toUpdate);
		if(index >= 0) {
			games.set(index, toUpdate);
			updated = true;
		}

		return updated;
	}

	public boolean deleteGame(Game toDel) {
		boolean deleted = false;

		int index = games.indexOf(toDel);
		if(index >= 0) {
			games.remove(index);
			deleted = true;
		}

		return deleted;
	}

	public ArrayList<Game> getAllGames() {
		ArrayList<Game> gamesCopy = new ArrayList<>();
		Game toCopy = null;
		Game copy = null;

		for(int i = 0; i < games.size(); i++){
			toCopy = games.get(i);
			if(toCopy.getDev() != null) {
				copy = new Game(toCopy.getName(), toCopy.getDev(), toCopy.getDescription(), toCopy.getPrice(), toCopy.getGenres());
			}
			else {
				copy = new Game(toCopy.getName());
			}
			gamesCopy.add(copy);
		}

		//we want to return a copy so the original can't be modified through the list returned
		return gamesCopy;
	}


	public Game getGameByName(String gameName) {
		Game toReturn = null;
		boolean found = false;

		if(gameName != null){
			for(int i = 0; i < games.size() && !found; i++){
				if(games.get(i).getName().equals(gameName)){
					toReturn = games.get(i);
					found = true;
				}
			}
		}
		return toReturn;
	}

	public List<Game> getGamesByNameImplicit(String gameNameImplicit) {
		List<Game> res = new ArrayList<>();
		List<Game> searchList = this.getAllGames();
		int searchLength = gameNameImplicit.length();
		char currChar;
		String gameName, implicit = null;
		Game curr;

		if (searchLength > 0) {
			for (int i = 0; i < searchList.size(); i++) {
				curr = searchList.get(i);
				gameName = curr.getName();
				for(int j = 0; j < searchLength; j++){
					currChar = gameNameImplicit.charAt(j);
					if(currChar != '%' && currChar != '*' && currChar != '?'){
						implicit += currChar;
					}
					if(gameName.contains(implicit)){
						res.add(curr);
					}
				}
			}
		} else {
			System.out.println("Please input valid game name.");
		}
		if (res.size() == 0 && searchLength > 0) {
			System.out.println("Not found.");
		}

		return res;
	}

	public boolean insertReview(Review review){
		return reviews.add(review);
	}

	public boolean updateReview(Review review){
		boolean updated = false;

		int index = reviews.indexOf(review);
		if(index >= 0) {
			reviews.set(index, review);
			updated = true;
		}
		return updated;
	}

	public boolean deleteReview(Review review) {
		boolean deleted = false;

		int index = reviews.indexOf(review);
		if(index >= 0) {
			reviews.remove(index);
			deleted = true;
		}

		return deleted;
	}

	public List<Review> getAllReviews(){
		List<Review> reviewsCopy = new ArrayList<>();
		Review toCopy = null;
		Review copy = null;

		for(int i = 0; i < reviews.size(); i++){
			toCopy = reviews.get(i);
			if(toCopy.validReview()) {
				if(toCopy.getReviewID() != -1){
					try{
						copy = new Review(toCopy.getReviewID(),toCopy.getComment(),toCopy.getGameName(),toCopy.getUserID());
					}
					catch (IncorrectFormat incorrectFormat){
						System.out.println(incorrectFormat.getMessage());
					}
				}
				else {
					try{
						copy = new Review(toCopy.getComment(),toCopy.getGameName(),toCopy.getUserID());
					}
					catch (IncorrectFormat incorrectFormat){
						System.out.println(incorrectFormat.getMessage());
					}
				}
			}
			reviewsCopy.add(copy);
		}

		//we want to return a copy so the original can't be modified through the list returned
		return reviewsCopy;
	}

	public List<Review> getReviewsByGame(String gameName){
		List<Review> reviewsCopy = this.getAllReviews();
		List<Review> gameReviews = new ArrayList<>();

		for(int i = 0; i < reviewsCopy.size(); i++){
			if(reviewsCopy.get(i).getGameName().equals(gameName)){
				gameReviews.add(reviewsCopy.get(i));
			}
		}
		return gameReviews;
	}

	public List<Review> getReviewsByUser(String userID) {
		List<Review> reviewsCopy = this.getAllReviews();
		List<Review> userReviews = new ArrayList<>();

		for(int i = 0; i < reviewsCopy.size(); i++){
			if(reviewsCopy.get(i).getUserID().equals(userID)){
				userReviews.add(reviewsCopy.get(i));
			}
		}
		return userReviews;
	}

	public Review getReviewByID(int reviewID){
		Review match = null;
		boolean found = false;

		for(int i = 0; i < reviews.size() && !found; i++){
			if(reviews.get(i).getReviewID() == reviewID){
				match = reviews.get(i);
				found = true;
			}
		}

		return match;
	}

	public boolean insertRating(Rating rating){
		return ratings.add(rating);
	}

	public boolean updateRating(Rating rating){
		boolean updated = false;

		int index = ratings.indexOf(rating);
		if(index >= 0) {
			ratings.set(index, rating);
			updated = true;
		}
		return updated;
	}

	public boolean deleteRating(Rating rating){
		boolean deleted = false;

		int index = ratings.indexOf(rating);
		if(index >= 0) {
			ratings.remove(index);
			deleted = true;
		}

		return deleted;
	}

	public List<Rating> getAllRatings(){
		List<Rating> ratingsCopy = new ArrayList<>();
		Rating toCopy = null;
		Rating copy = null;

		for(int i = 0; i < ratings.size(); i++){
			toCopy = ratings.get(i);
			if(toCopy.validRating()){
				copy = new Rating(toCopy.getRatingValue(),toCopy.getGameName(),toCopy.getUserID());
			}
			ratingsCopy.add(copy);
		}

		return ratingsCopy;
	}

	public List<Rating> getRatingsByGame(String gameName){
		List<Rating> ratingsCopy = this.getAllRatings();
		List<Rating> gameRatings = new ArrayList<>();

		for(int i = 0; i < ratingsCopy.size(); i++){
			if(ratingsCopy.get(i).getGameName().equals(gameName)){
				gameRatings.add(ratingsCopy.get(i));
			}
		}
		return gameRatings;
	}

	public List<Rating> getRatingsByUser(String userID){
		List<Rating> ratingsCopy = this.getAllRatings();
		List<Rating> userRatings = new ArrayList<>();

		for(int i = 0; i < ratingsCopy.size(); i++){
			if(ratingsCopy.get(i).getUserID().equals(userID)){
				userRatings.add(ratingsCopy.get(i));
			}
		}
		return userRatings;
	}

	public Rating getRating(String gameName, String userID){
		Rating toReturn = null;
		Rating curr = null;
		boolean found = false;

		for(int i = 0; i < ratings.size() && !found; i++){
			curr = ratings.get(i);
			if(curr.getUserID().equals(userID) && curr.getGameName().equals(gameName)){
				toReturn = curr;
				found = true;
			}
		}

		return toReturn;
	}
}

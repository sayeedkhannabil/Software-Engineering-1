package comp3350.grs.persistence;

// the stub database which stores users and games

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Request;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Review;

public class DataAccessStub extends DataAccess implements DataAccessI {


	public DataAccessStub(String dbName) {
		super(dbName);
	}

	public DataAccessStub() {
		super();
	}

	@Override
	public void open(String dbPath) {
		super.open(dbPath);
		dbType = "stub";
	}

	public void close() {
		System.out.println("Closed " +dbType +" database " +dbName);
	}

	public void deleteDatabase(){
		users=null;
		games=null;
		ratings = null;
		reviews = null;
	}

	public void clearAllData(){
		users.clear();
		games.clear();
		ratings.clear();
		reviews.clear();
		requests.clear();
	}

	@Override
	public void clearUsers() {
		users.clear();
	}

	@Override
	public void clearGames() {
		games.clear();
	}

	@Override
	public void clearReviews() {
		reviews.clear();
	}

	@Override
	public void clearRequests(){
		requests.clear();
	}

	@Override
	public void clearRatings() {
		ratings.clear();
	}

	public boolean insertUser(User newUser) {
		boolean inserted = false;
		if(newUser != null&&newUser.valid()) {
			if(!users.contains(newUser)){
				inserted = users.add(newUser);
			}
		}
		return inserted;
	}

	public boolean updateUser(User currentUser) {
		int index;
		boolean updated = false;

		if(currentUser != null && currentUser.valid()) {
			index = users.indexOf(currentUser);
			if (index >= 0) {
				users.set(index, currentUser);
				updated = true;
			}
		}

		return updated;
	}

	public boolean deleteUser(User user) {
		int index;
		boolean deleted = false;

		if(user != null) {
			index = users.indexOf(user);
			if (index >= 0) {
				users.remove(index);
				deleted = true;
			}
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
					incorrectFormat.printStackTrace();
				}
			}
			else if(toCopy instanceof RegisteredUser) {
				try{
					RegisteredUser registeredUser=(RegisteredUser) toCopy;
					copy = new RegisteredUser(toCopy.getUserID(),registeredUser.getPassword());
				}
				catch (IncorrectFormat incorrectFormat) {
					incorrectFormat.printStackTrace();
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
		int searchLength;
		char currChar;
		boolean containsWC = false; //contains wild card
		String userName, implicit = "";
		User curr;

		if(userNameImplicit != null) {
			searchLength = userNameImplicit.length();
			if (searchLength > 0) {
				//get the username searched for
				for (int j = 0; j < searchLength; j++) {
					currChar = userNameImplicit.charAt(j);
					if (currChar != '%' && currChar != '*' && currChar != '?') {
						implicit += currChar;
					}
					else{
						containsWC = true;
					}
				}

				for (int i = 0; i < searchList.size(); i++) {
					curr = searchList.get(i);
					userName = curr.getUserID();

					if (implicit.trim().length() > 0) {
						//if it contains a wildcard, add if userName contains implicit
						if(containsWC){
							if(userName.contains(implicit)) {
								res.add(curr);
							}
						}
						else{ //if no wildcard, add ONLY if userName is an exact match to implicit
							if(userName.equals(implicit)){
								res.add(curr);
							}
						}
					}
				}
			} else {
				System.out.println("Please input valid user ID.");
			}
			if (res.size() == 0 && searchLength > 0) {
				System.out.println("Not found.");
			}
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
		boolean inserted = false;
		if(toAdd != null) {
			if (toAdd.valid()) {
				if(!games.contains(toAdd)){
					inserted = games.add(toAdd);
				}
			}
		}
		return inserted;
	}

	public boolean updateGame(Game toUpdate) {
		boolean updated = false;
		int index;

		if(toUpdate != null && toUpdate.valid()) {
			index = games.indexOf(toUpdate);
			if (index >= 0) {
				games.set(index, toUpdate);
				updated = true;
			}
		}

		return updated;
	}

	public boolean deleteGame(Game toDel) {
		boolean deleted = false;
		int index;

		if(toDel != null) {
			index = games.indexOf(toDel);
			if (index >= 0) {
				games.remove(index);
				deleted = true;
			}
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
				try {
					copy = new Game(toCopy.getName(), toCopy.getDev(), toCopy.getDescription(), toCopy.getPrice(), toCopy.getGenres());
				} catch (IncorrectFormat incorrectFormat) {
					incorrectFormat.printStackTrace();
				}
			}
			else {
				try {
					copy = new Game(toCopy.getName());
				} catch (IncorrectFormat incorrectFormat) {
					incorrectFormat.printStackTrace();
				}
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
		int searchLength;
		char currChar;
		boolean containsWC = false; //contains wild card
		String gameName, implicit = "";
		Game curr;

		if(gameNameImplicit != null) {
			searchLength = gameNameImplicit.length();
			if (searchLength > 0) {
				//get the game name searched for
				for (int j = 0; j < searchLength; j++) {
					currChar = gameNameImplicit.charAt(j);
					if (currChar != '%' && currChar != '*' && currChar != '?') {
						implicit += currChar;
					}
					else{
						containsWC = true;
					}
				}

				for (int i = 0; i < searchList.size(); i++) {
					curr = searchList.get(i);
					gameName = curr.getName();

					if (implicit.trim().length() > 0) {
						//if it contains a wildcard, add if userName contains implicit
						if(containsWC){
							if(gameName.contains(implicit)) {
								res.add(curr);
							}
						}
						else{ //if no wildcard, add ONLY if userName is an exact match to implicit
							if(gameName.equals(implicit)){
								res.add(curr);
							}
						}
					}
				}
			} else {
				System.out.println("Please input valid game name.");
			}
			if (res.size() == 0 && searchLength > 0) {
				System.out.println("Not found.");
			}
		}

		return res;
	}

	public boolean insertReview(Review review){
		boolean inserted = false;
		Game reviewGame;
		User reviewUser;
		if(review != null && review.valid()) {
			reviewGame = getGameByName(review.getGameName());
			reviewUser = getUserByID(review.getUserID());
			if(games.contains(reviewGame) && (users.contains(reviewUser) || review.getUserID().equals("Guest"))) {
				inserted = reviews.add(review);
			}
		}
		return inserted;
	}

	public boolean updateReview(Review review){
		boolean updated = false;
		int index;
		Game reviewGame;
		User reviewUser;

		if(review != null && review.valid()) {
			reviewGame = getGameByName(review.getGameName());
			reviewUser = getUserByID(review.getUserID());
			if(games.contains(reviewGame) && (users.contains(reviewUser) || review.getUserID().equals("Guest"))) {
				index = reviews.indexOf(review);
				if (index >= 0) {
					reviews.set(index, review);
					updated = true;
				}
			}
		}
		return updated;
	}

	public boolean deleteReview(Review review) {
		boolean deleted = false;
		int index;

		if(review != null) {
			index = reviews.indexOf(review);
			if (index >= 0) {
				reviews.remove(index);
				deleted = true;
			}
		}

		return deleted;
	}

	public List<Review> getAllReviews(){
		List<Review> reviewsCopy = new ArrayList<>();
		Review toCopy = null;
		Review copy = null;

		for(int i = 0; i < reviews.size(); i++){
			toCopy = reviews.get(i);
			if(toCopy.valid()) {
				if(toCopy.getReviewID() != -1){
					try{
						copy = new Review(toCopy.getReviewID(),toCopy.getComment(),toCopy.getGameName(),toCopy.getUserID());
					}
					catch (IncorrectFormat incorrectFormat){
						incorrectFormat.printStackTrace();
					}
				}
				else {
					try{
						copy = new Review(toCopy.getComment(),toCopy.getGameName(),toCopy.getUserID());
					}
					catch (IncorrectFormat incorrectFormat){
						incorrectFormat.printStackTrace();
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
		boolean inserted = false;
		Game ratingGame;
		User ratingUser;
		if(rating != null && rating.valid()) {
			ratingGame = getGameByName(rating.getGameName());
			ratingUser = getUserByID(rating.getUserID());
			if(games.contains(ratingGame) && (users.contains(ratingUser) || rating.getUserID().equals("Guest"))) {
				inserted = ratings.add(rating);
			}
		}
		return inserted;
	}

	public boolean updateRating(Rating rating){
		boolean updated = false;
		int index;
		Game ratingGame;
		User ratingUser;

		if(rating != null && rating.valid()) {
			index = ratings.indexOf(rating);
			ratingGame = getGameByName(rating.getGameName());
			ratingUser = getUserByID(rating.getUserID());
			if (index >= 0) {
				if(games.contains(ratingGame) && (users.contains(ratingUser) || rating.getUserID().equals("Guest"))) {
					ratings.set(index, rating);
					updated = true;
				}
			}
		}
		return updated;
	}

	public boolean deleteRating(Rating rating){
		boolean deleted = false;
		int index;

		if(rating != null) {
			index = ratings.indexOf(rating);
			if (index >= 0) {
				ratings.remove(index);
				deleted = true;
			}
		}

		return deleted;
	}

	public List<Rating> getAllRatings(){
		List<Rating> ratingsCopy = new ArrayList<>();
		Rating toCopy = null;
		Rating copy = null;

		for(int i = 0; i < ratings.size(); i++){
			toCopy = ratings.get(i);
			if(toCopy.valid()){
				try {
					copy = new Rating(toCopy.getRatingValue(),toCopy.getGameName(),toCopy.getUserID());
				} catch (IncorrectFormat incorrectFormat) {
					incorrectFormat.printStackTrace();
				}
			}
			ratingsCopy.add(copy);
		}

		return ratingsCopy;
	}

	public List<Rating> getRatingsByGame(String gameName){
		List<Rating> ratingsCopy = this.getAllRatings();
		List<Rating> gameRatings = new ArrayList<>();

		if(gameName != null) {
			for (int i = 0; i < ratingsCopy.size(); i++) {
				if (ratingsCopy.get(i).getGameName().equals(gameName)) {
					gameRatings.add(ratingsCopy.get(i));
				}
			}
		}
		return gameRatings;
	}

	public List<Rating> getRatingsByUser(String userID){
		List<Rating> ratingsCopy = this.getAllRatings();
		List<Rating> userRatings = new ArrayList<>();

		if(userID != null) {
			for (int i = 0; i < ratingsCopy.size(); i++) {
				if (ratingsCopy.get(i).getUserID().equals(userID)) {
					userRatings.add(ratingsCopy.get(i));
				}
			}
		}
		return userRatings;
	}

	public Rating getRating(String gameName, String userID){
		Rating toReturn = null;
		Rating curr = null;
		boolean found = false;

		if(gameName != null && userID != null) {
			for (int i = 0; i < ratings.size() && !found; i++) {
				curr = ratings.get(i);
				if (curr.getUserID().equals(userID) && curr.getGameName().equals(gameName)) {
					toReturn = curr;
					found = true;
				}
			}
		}

		return toReturn;
	}

	public List<Request> getAllRequests(){
		List<Request> requestsCopy = new ArrayList<>();
		Request toCopy = null;
		Request copy = null;

		for(int i = 0; i < requests.size(); i++){
			toCopy = requests.get(i);
			if(toCopy.valid()) {
				try{
					copy = new Request(toCopy.getGameName(),toCopy.getUserID());
				}
				catch (IncorrectFormat incorrectFormat){
					incorrectFormat.printStackTrace();
				}
			}
			requestsCopy.add(copy);
		}

		//we want to return a copy so the original can't be modified through the list returned
		return requestsCopy;
	}

	public List<Request> getRequestsByUser(String userID){
		List<Request> requestsCopy = this.getAllRequests();
		List<Request> userRequests = new ArrayList<>();

		if(userID != null) {
			for (int i = 0; i < requestsCopy.size(); i++) {
				if (requestsCopy.get(i).getUserID().equals(userID)) {
					userRequests.add(requestsCopy.get(i));
				}
			}
		}
		return userRequests;
	}

	public List<Request> getRequestsByGame(String gameName){
		List<Request> requestsCopy = this.getAllRequests();
		List<Request> gameRequests = new ArrayList<>();

		if(gameName != null) {
			for (int i = 0; i < requestsCopy.size(); i++) {
				if (requestsCopy.get(i).getGameName().equals(gameName)) {
					gameRequests.add(requestsCopy.get(i));
				}
			}
		}
		return gameRequests;
	}

	public Request getRequest(String gameName, String userID){
		Request toReturn = null;
		Request curr = null;
		boolean found = false;

		if(gameName != null && userID != null) {
			for (int i = 0; i < requests.size() && !found; i++) {
				curr = requests.get(i);
				if (curr.getUserID().equals(userID) && curr.getGameName().equals(gameName)) {
					toReturn = curr;
					found = true;
				}
			}
		}

		return toReturn;
	}

	public boolean insertRequest(Request toInsert){
		boolean inserted = false;
		User requestUser;
		if(toInsert != null && toInsert.valid()) {
			requestUser = getUserByID(toInsert.getUserID());
			if (!requests.contains(toInsert) && (users.contains(requestUser) || toInsert.getUserID().equals("Guest"))) {
				inserted = requests.add(toInsert);
			}
		}
		return inserted;
	}

	public boolean deleteRequest(Request toDelete){
		boolean deleted = false;
		int index;

		if(toDelete != null) {
			index = requests.indexOf(toDelete);
			if (index >= 0) {
				requests.remove(index);
				deleted = true;
			}
		}

		return deleted;
	}

	public List<String> getGamesOrderByRequestNum(int limit){
		return null;
	}
}

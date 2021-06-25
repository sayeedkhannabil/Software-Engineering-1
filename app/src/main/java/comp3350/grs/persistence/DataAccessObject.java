/**
 * This code is not used in the first iteration. It is provided as
 * an example of usage of HSQLDB (for iteration 2).
 */

package comp3350.grs.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;


public class DataAccessObject extends DataAccess implements DataAccessI
{
	private Statement statement1, statement2, statement3;
	private PreparedStatement preparedStatement;
	private Connection connection;
	private ResultSet resultSet1,resultSet2,resultSet3;

	private String cmdString;
	private int updateCount;
	private String result;
	private static String EOF = "  ";


	public DataAccessObject(String dbName)
	{
		super(dbName);
	}

	public DataAccessObject(){
		super();
	}

	public void open(String dbPath)
	{
		super.open(dbPath);
		String url;
		try
		{
			// Setup for HSQL
			dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
			connection = DriverManager.getConnection(url, "SA", "");
			statement1 = connection.createStatement();
			statement2 = connection.createStatement();
			statement3 = connection.createStatement();


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}




		try {
			if (!checkTableExist("users")){
				cmdString="CREATE TABLE USERS(USERID VARCHAR(20) " +
						"NOT NULL PRIMARY KEY,PASSWORD VARCHAR(20))";
				statement1.executeUpdate(cmdString);
				for (int i = 0; i < users.size(); i++) {
					insertUser(users.get(i));
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}



		try {
			if (!checkTableExist("games")){
				cmdString="CREATE TABLE GAMES(GAMENAME VARCHAR(20) " +
						"NOT NULL " +
						"PRIMARY KEY,DEVELOPER VARCHAR(20),DESCRIPTION " +
						"VARCHAR(20),PRICE DOUBLE)";
				statement1.executeUpdate(cmdString);
				for (int i = 0; i < games.size(); i++) {
					insertGame(games.get(i));
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		if (!checkTableExist("ratings")){
			cmdString="create TABLE ratings (ratingID integer,rating double, GAMENAME VARCHAR(20), USERID VARCHAR(20),primary key(ratingID),foreign key(GAMENAME) references GAMES(GAMENAME),foreign key(USERID) references users(USERID))";
			try {
				statement1.executeUpdate(cmdString);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		if (!checkTableExist("reviews")){
			cmdString="CREATE TABLE reviews(reviewID integer,reviewContent VARCHAR(140),GAMENAME VARCHAR(20), USERID VARCHAR(20),primary key(reviewID),foreign key(GAMENAME) references GAMES(GAMENAME),foreign key(USERID) references users(USERID))";
			try {
				statement1.executeUpdate(cmdString);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		System.out.println("Opened " +dbType +" database " +dbPath);
	}

	private boolean checkTableExist(String tableName){
		boolean tableExist=false;
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			resultSet1= databaseMetaData.getTables(null, null, tableName, null);
			if (resultSet1.next()){
				tableExist=true;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return tableExist;
	}

	private void deleteTable(String tableName){
		cmdString="drop table " + tableName;
		try {
			statement1.executeUpdate(cmdString);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void clearDatabase(){
		deleteTable("users");
		deleteTable("games");
	}

	private void clearTable(String tableName){
		cmdString="delete from "+ tableName;
		try {
			statement1.executeUpdate(cmdString);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void clearTable(){
		clearTable("users");
		clearTable("games");
	}


	public void close()
	{
		try
		{	// commit all changes to the database
			cmdString = "shutdown compact";
			resultSet1 = statement1.executeQuery(cmdString);
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Closed " +dbType +" database " +dbName);
	}

	public List<User> getAllUsers(){
		List<User> userList=new ArrayList<User>();
		String userID,password;
		try {
			preparedStatement=connection.prepareStatement("select * from users");
			resultSet1=preparedStatement.executeQuery();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		try {

			while (resultSet1.next()){
				userID=resultSet1.getString(1);
				password=resultSet1.getString(2);
				if (password==null){
					userList.add(new Guest());
				}else {
					userList.add(new RegisteredUser(userID,password));
				}

			}
		} catch (SQLException | IncorrectFormat throwables) {
			throwables.printStackTrace();
		}

		return userList;
	}

	public User getUserByID(String userID){
		User userResult=null;
		String password=null;

		try {
			preparedStatement=connection.prepareStatement("select * from " +
					"users where USERID = ?");
			preparedStatement.setString(1,userID);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		try {
			resultSet1=preparedStatement.executeQuery();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		try {
			if (resultSet1.next()){
				password=resultSet1.getString(2);
				if (password==null){
					userResult=new Guest();
				}
				else{
					userResult=new RegisteredUser(userID,password);
				}
			}
		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return userResult;
	}

	public boolean insertUser(User user) {
		String values;
		result = null;
		boolean insertSuccess=false;

		try {
			preparedStatement=connection.prepareStatement("insert into users " +
					"values(?,?)");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		try {
			preparedStatement.setString(1,user.getUserID());
			if (user instanceof Guest){
				preparedStatement.setNull(2, Types.VARCHAR);
			}
			else {
				user=(RegisteredUser)user;
				preparedStatement.setString(2,((RegisteredUser) user).getPassword());
			}
			updateCount = preparedStatement.executeUpdate();
			if (updateCount!=1){
				insertSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		return insertSuccess;

	}

	public boolean updateUser(User user){
		String userID;
		String password;
		boolean updateSuccess=false;

		userID=user.getUserID();

		try {
			preparedStatement= connection.prepareStatement("update users set " +
					"password=? where userid=?");
			preparedStatement.setString(2,userID);
			if(user instanceof Guest){
				preparedStatement.setNull(1,Types.VARCHAR);
			}
			else{
				user=(RegisteredUser)user;
				password=((RegisteredUser) user).getPassword();
				preparedStatement.setString(1,password);
			}
			updateCount= preparedStatement.executeUpdate();
			if (updateCount==1){
				updateSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return updateSuccess;
	}

	public boolean deleteUser(User user){
		String userID;
		boolean deleteSuccess=false;

		userID=user.getUserID();
		try {
			preparedStatement= connection.prepareStatement("delete from users " +
					"where userid=?");
			preparedStatement.setString(1,userID);
			updateCount= preparedStatement.executeUpdate();
			if (updateCount==1){
				deleteSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		return deleteSuccess;
	}

	public List<Game> getAllGames(){
		List<Game> gameList=new ArrayList<Game>();
		String gameName,developer,description;
		double price;

		try {
			preparedStatement=connection.prepareStatement("select * from games");
			resultSet1=preparedStatement.executeQuery();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		try {

			while (resultSet1.next()){
				gameName=resultSet1.getString(1);
				developer=resultSet1.getString(2);
				description=resultSet1.getString(3);
				price=resultSet1.getDouble(4);
				gameList.add(new Game(gameName,developer,description,price));

			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return gameList;
	}

	public Game getGameByName(String gameName){
		Game gameResult=null;
		String developer=null,description=null;
		double price=0.0;

		try {
			preparedStatement= connection.prepareStatement("select * from games " +
					"where gameName=?");
			preparedStatement.setString(1,gameName);
			resultSet1=preparedStatement.executeQuery();
			if (resultSet1.next()){
				gameName=resultSet1.getString(1);
				developer=resultSet1.getString(2);
				description=resultSet1.getString(3);
				price=resultSet1.getDouble(4);
				gameResult=new Game(gameName,developer,description,price);
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return gameResult;
	}

	public boolean insertGame(Game game){

		try {
			preparedStatement=connection.prepareStatement("Insert into Games " +
					"values(?,?,?,?)");
			preparedStatement.setString(1,game.getName());
			preparedStatement.setString(2,game.getDev());
			preparedStatement.setString(3,game.getDescription());
			preparedStatement.setDouble(4, game.getPrice());
			updateCount=preparedStatement.executeUpdate();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		if (updateCount!=1){
			return false;
		}
		else {
			return true;
		}
	}

	public boolean updateGame(Game game){
		String gameName=null,developer=null,description=null;
		double price=0.0;
		boolean updateSuccess=false;

		gameName=game.getName();
		developer=game.getDev();
		description=game.getDescription();
		price=game.getPrice();

		try {
			preparedStatement= connection.prepareStatement("update games set " +
					"developer=?,description=?,price=? where gameName=?");
			preparedStatement.setString(1,developer);
			preparedStatement.setString(2,description);
			preparedStatement.setDouble(3,price);
			preparedStatement.setString(4,gameName);

			updateCount= preparedStatement.executeUpdate();
			if (updateCount==1){
				updateSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return updateSuccess;
	}

	public boolean deleteGame(Game game){
		String gameName=null;
		boolean deleteSuccess=false;

		gameName=game.getName();
		try {
			preparedStatement= connection.prepareStatement("delete from games" +
					" " +
					"where gameName=?");
			preparedStatement.setString(1,gameName);
			updateCount= preparedStatement.executeUpdate();
			if (updateCount==1){
				deleteSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return deleteSuccess;
	}

	public List<Review> getAllReviews(){
		List<Review> reviewList=new ArrayList<>();

		int reviewID;
		String gameName=null,userID=null,reviewContent=null;

		try {
			preparedStatement= connection.prepareStatement("select * from reviews");
			resultSet1= preparedStatement.executeQuery();
			while(resultSet1.next()){
				reviewID=resultSet1.getInt(1);
				reviewContent=resultSet1.getString(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				reviewList.add(new Review(reviewID,reviewContent,gameName,
						userID));
			}


		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return reviewList;
	}


	public List<Review> getReviewsByGame(String gameName){
		List<Review> reviewList=new ArrayList<>();
		int reviewID;
		String userID=null,reviewContent=null;

		try {
			preparedStatement= connection.prepareStatement("select * from " +
					"reviews where gameName=?");
			preparedStatement.setString(1,gameName);
			resultSet1= preparedStatement.executeQuery();
			while(resultSet1.next()){
				reviewID=resultSet1.getInt(1);
				reviewContent=resultSet1.getString(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				reviewList.add(new Review(reviewID,reviewContent,gameName,
						userID));
			}

		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return reviewList;
	}

	public List<Review> getReviewsByUser(String userID){
		List<Review> reviewList=new ArrayList<>();
		int reviewID;
		String gameName=null,reviewContent=null;

		try {
			preparedStatement= connection.prepareStatement("select * from " +
					"reviews where userID=?");
			preparedStatement.setString(1,userID);
			resultSet1= preparedStatement.executeQuery();
			while(resultSet1.next()){
				reviewID=resultSet1.getInt(1);
				reviewContent=resultSet1.getString(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				reviewList.add(new Review(reviewID,reviewContent,gameName,
						userID));
			}

		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return reviewList;
	}

	public Review getReviewByID(int reviewID){
		Review reviewResult=null;

		String userID=null, gameName=null,reviewContent=null;

		try {
			preparedStatement= connection.prepareStatement("select * from " +
					"reviews where reviewID=?");
			preparedStatement.setInt(1,reviewID);
			resultSet1= preparedStatement.executeQuery();
			if(resultSet1.next()){
				reviewContent=resultSet1.getString(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				reviewResult=new Review(reviewID,reviewContent,gameName,userID);
			}

		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return reviewResult;
	}

	public boolean insertReview(Review review){
		int reviewID;
		String reviewContent=null,gameName=null,userID=null;
		boolean insertSucess=false;

		reviewID=review.getReviewID();
		reviewContent=review.getComment();
		gameName=review.getGameName();
		userID=review.getUserID();
		try {
			preparedStatement= connection.prepareStatement("insert into reviews " +
					"values(?,?,?,?)");
			preparedStatement.setInt(1,reviewID);
			preparedStatement.setString(2,reviewContent);
			preparedStatement.setString(3,gameName);
			preparedStatement.setString(4,userID);
			updateCount= preparedStatement.executeUpdate();
			if (updateCount==1){
				insertSucess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return insertSucess;
	}

	public boolean updateReview(Review review){
		int reviewID;
		String reviewContent=null,gameName=null,userID=null;
		boolean updateSucess=false;

		reviewID=review.getReviewID();
		reviewContent=review.getComment();
		gameName=review.getGameName();
		userID=review.getUserID();
		try {
			preparedStatement= connection.prepareStatement("update reviews set " +
					"reviewContent=?,gameName=?,userID=? where reviewID=?");
			preparedStatement.setString(1,reviewContent);
			preparedStatement.setString(2,gameName);
			preparedStatement.setString(3,userID);
			preparedStatement.setInt(4,reviewID);
			updateCount=preparedStatement.executeUpdate();
			if (updateCount==1){
				updateSucess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return updateSucess;
	}

	public boolean deleteReview(Review review){
		int reviewID=review.getReviewID();
		boolean deleteSuccess=false;

		try {
			preparedStatement= connection.prepareStatement("delete from reviews " +
					"where reviewID=?");
			preparedStatement.setInt(1,reviewID);
			updateCount=preparedStatement.executeUpdate();
			if (updateCount==1){
				deleteSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return deleteSuccess;
	}

	public List<Rating> getAllRatings(){
		List<Rating> ratingList=new ArrayList<Rating>();
		double rating=0.0;
		String gameName=null;
		String userID=null;
		int ratingID;

		try {
			preparedStatement= connection.prepareStatement("select * from ratings");
			resultSet1=preparedStatement.executeQuery();
			while (resultSet1.next()){
				ratingID=resultSet1.getInt(1);
				rating=resultSet1.getDouble(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				ratingList.add(new Rating(ratingID,rating,gameName,userID));
			}
		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}

		return ratingList;
	}

	public List<Rating> getRatingsByGame(String gameName){
		List<Rating> ratingList=new ArrayList<Rating>();
		int ratingID;
		double rating=0.0;
		String userID=null;

		try {
			preparedStatement= connection.prepareStatement("select * from ratings" +
					" where gameName=?");
			preparedStatement.setString(1,gameName);
			resultSet1= preparedStatement.executeQuery();
			while (resultSet1.next()){
				ratingID=resultSet1.getInt(1);
				rating=resultSet1.getDouble(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				ratingList.add(new Rating(ratingID,rating,gameName,userID));
			}
		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return ratingList;
	}

	public List<Rating> getRatingsByUser(String userID){
		List<Rating> ratingList=new ArrayList<Rating>();
		int ratingID;
		double rating=0.0;
		String gameName=null;

		try {
			preparedStatement= connection.prepareStatement("select * from ratings" +
					" where userID=?");
			preparedStatement.setString(1,userID);
			resultSet1= preparedStatement.executeQuery();
			while (resultSet1.next()){
				ratingID=resultSet1.getInt(1);
				rating=resultSet1.getDouble(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				ratingList.add(new Rating(ratingID,rating,gameName,userID));
			}
		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return ratingList;
	}

	public Rating getRatingByID(int ratingID){
		Rating ratingResult=null;
		double rating=0.0;
		String gameName=null;
		String userID=null;

		try {
			preparedStatement= connection.prepareStatement("select * from ratings" +
					" where ratingID=?");
			preparedStatement.setInt(1,ratingID);
			resultSet1= preparedStatement.executeQuery();

			if (resultSet1.next()){
				ratingID=resultSet1.getInt(1);
				rating=resultSet1.getDouble(2);
				gameName=resultSet1.getString(3);
				userID=resultSet1.getString(4);

				ratingResult=new Rating(ratingID,rating,gameName,userID);
			}
		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return ratingResult;
	}

	public boolean insertRating(Rating theRating){
		boolean insertSuccess=false;
		int ratingID;
		double rating=0.0;
		String gameName=null;
		String userID=null;

		ratingID=theRating.getRatingID();
		rating= theRating.getRating();
		gameName= theRating.getGameName();
		userID= theRating.getUserID();

		try {
			preparedStatement= connection.prepareStatement("insert into ratings " +
					"values(?,?,?,?)");
			preparedStatement.setInt(1,ratingID);
			preparedStatement.setDouble(2,rating);
			preparedStatement.setString(3,gameName);
			preparedStatement.setString(4,userID);
			updateCount= preparedStatement.executeUpdate();
			if (updateCount==1){
				insertSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return insertSuccess;
	}

	public boolean updateRating(Rating theRating){
		boolean updateSuccess=false;
		int ratingID;
		double rating=0.0;
		String gameName=null;
		String userID=null;

		ratingID=theRating.getRatingID();
		rating= theRating.getRating();
		gameName= theRating.getGameName();
		userID= theRating.getUserID();

		try {
			preparedStatement= connection.prepareStatement("update ratings " +
					"set rating=?,gameName=?,userID=? where ratingID=?");

			preparedStatement.setDouble(1,rating);
			preparedStatement.setString(2,gameName);
			preparedStatement.setString(3,userID);
			preparedStatement.setInt(4,ratingID);
			updateCount= preparedStatement.executeUpdate();
			if (updateCount==1){
				updateSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return updateSuccess;
	}

	public boolean deleteRating(Rating rating){
		boolean deleteSuccess=false;
		int ratingID;

		ratingID=rating.getRatingID();
		try {
			preparedStatement= connection.prepareStatement("delete from ratings " +
					"where ratingID=?");
			preparedStatement.setInt(1,ratingID);
			updateCount=preparedStatement.executeUpdate();
			if (updateCount==1){
				deleteSuccess=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return deleteSuccess;
	}

}

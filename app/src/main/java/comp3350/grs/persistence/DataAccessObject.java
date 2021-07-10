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
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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

	//open the database, create tables, load default value, initiate variables
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
				cmdString="CREATE TABLE GAMES(GAMENAME VARCHAR(40) NOT NULL PRIMARY KEY,DEVELOPER VARCHAR(20),DESCRIPTION VARCHAR(400),PRICE double)";
				statement1.executeUpdate(cmdString);
				if (!checkTableExist("genres")) {
					cmdString = "CREATE TABLE genres(GAMENAME VARCHAR(40),genre varchar(20), genreID integer identity primary key,foreign key(GAMENAME) references GAMES(GAMENAME))";
					statement1.executeUpdate(cmdString);
				}
				for (int i = 0; i < games.size(); i++) {
					insertGame(games.get(i));
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}


		if (!checkTableExist("ratings")){
			cmdString="create TABLE ratings (RatingValue double, GAMENAME VARCHAR(40), USERID VARCHAR(20),primary key(GAMENAME,USERID),foreign key(GAMENAME) references GAMES(GAMENAME),foreign key(USERID) references USERS(USERID))";
			try {
				statement1.executeUpdate(cmdString);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		if (!checkTableExist("reviews")){
			cmdString="CREATE TABLE reviews(reviewID integer identity primary key,reviewContent VARCHAR(140),GAMENAME VARCHAR(40), USERID VARCHAR(20),foreign key(GAMENAME) references GAMES(GAMENAME),foreign key(USERID) references USERS(USERID))";
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
			tableName=tableName.toUpperCase();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			resultSet1 = databaseMetaData.getTables(null, null, tableName, null);
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
		deleteTable("reviews");
		deleteTable("ratings");
		deleteTable("users");
		deleteTable("genres");
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

	public void clearUsers(){
		clearTable("users");
	}

	public void clearGames(){
		clearTable("genres");
		clearTable("games");
	}

	public void clearReviews(){
		clearTable("reviews");
	}

	public void clearRatings(){
		clearTable("ratings");
	}

	public void clearTable(){
		clearReviews();
		clearRatings();
		clearUsers();
		clearGames();
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

	private ResultSet getAll(String tableName){
		ResultSet resultSet=null;
		try {
			preparedStatement=
					connection.prepareStatement("select * from " + tableName);
			resultSet=preparedStatement.executeQuery();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return resultSet;
	}

	private List<User> getUsersByResultset(ResultSet resultSet ){
		User user=null;
		List<User> userList=new ArrayList<User>();
		String password=null;
		String userID=null;
		try {
			while (resultSet.next()){
				userID=resultSet.getString(1);
				password=resultSet.getString(2);
				if (password==null){
					user=new Guest();
				}
				else{
					user=new RegisteredUser(userID,password);
				}
				userList.add(user);
			}
		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return userList;
	}

	public List<User> getAllUsers(){
		List<User> userList=new ArrayList<User>();
		String userID,password;
		resultSet1 = getAll("users");
		userList=getUsersByResultset(resultSet1);
		return userList;
	}


	public User getUserByID(String userID){
		User userResult=null;
		List<User> userList;
		if (userID!=null){
			try {
				preparedStatement=connection.prepareStatement("select * from " +
						"users where USERID = ?");
				preparedStatement.setString(1,userID);
				resultSet1 =preparedStatement.executeQuery();
				userList=getUsersByResultset(resultSet1);
				if (userList.size()>0){
					userResult=userList.get(0);
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return userResult;
	}

	public List<User> getUsersByIDImplicit(String userIDImp){
		List<User> userList=new ArrayList<>();

		if (userIDImp!=null){
			try {
				preparedStatement=connection.prepareStatement("select * from " +
						"users where USERID like ?");
				preparedStatement.setString(1,userIDImp);
				resultSet1 =preparedStatement.executeQuery();
				userList=getUsersByResultset(resultSet1);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return userList;
	}

	public boolean insertUser(User user) {
		String values;
		result = null;
		boolean insertSuccess=false;

		if (user!=null&&user.validUser()){
			try {
				preparedStatement=connection.prepareStatement("insert into users " +
						"values(?,?)");
				preparedStatement.setString(1,user.getUserID());
				if (user instanceof Guest){
					preparedStatement.setNull(2, Types.VARCHAR);
				}
				else {
					user=(RegisteredUser)user;
					preparedStatement.setString(2,((RegisteredUser) user).getPassword());
				}
				updateCount = preparedStatement.executeUpdate();
				if (updateCount==1){
					insertSuccess=true;
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return insertSuccess;

	}

	public boolean updateUser(User user){
		String userID;
		String password;
		boolean updateSuccess=false;

		if (user!=null&&user.validUser()){
			try {
				userID=user.getUserID();
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
		}

		return updateSuccess;
	}

	public boolean deleteUser(User user){
		String userID;
		boolean deleteSuccess=false;

		if (user!=null&&user.validUser()){
			try {
				userID=user.getUserID();
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
		}

		return deleteSuccess;
	}

	private boolean insertGenres(Game game){
		List<String> gameGenres=game.getGenres();
		boolean insertAllSuccess=false;//all the inserts succeed
		boolean insertFail=false;//keep track if any one of the insert fails

		try {
			for (int i = 0; i < gameGenres.size()&&!insertFail; i++) {
				preparedStatement = connection.prepareStatement("insert into genres" +
						"(gameName,genre) values (?,?)");
				preparedStatement.setString(1,game.getName());
				preparedStatement.setString(2,gameGenres.get(i));
				updateCount= preparedStatement.executeUpdate();
				if (updateCount!=1){
					insertFail=true;
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		if (!insertFail){
			insertAllSuccess=true;
		}
		return insertAllSuccess;
	}

	private boolean updateGenres(Game game){
		boolean success=false;
		deleteGenres(game);
		success=insertGenres(game);
		return success;
	}

	private boolean deleteGenres(Game game){
		boolean success=false;
		try {
			preparedStatement= connection.prepareStatement("delete from genres " +
					"where gameName=?");
			preparedStatement.setString(1,game.getName());
			updateCount=preparedStatement.executeUpdate();
			if (updateCount>=1){
				success=true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return success;
	}

	public boolean insertGame(Game game){
		boolean insertSuccess=false;

		if (game!=null&&game.validGame()){
			try {
				preparedStatement=connection.prepareStatement("Insert into Games " +
						"values(?,?,?,?)");
				preparedStatement.setString(1,game.getName());
				preparedStatement.setString(2,game.getDev());
				preparedStatement.setString(3,game.getDescription());
				preparedStatement.setDouble(4, game.getPrice());
				updateCount=preparedStatement.executeUpdate();
				if (updateCount==1){
					insertSuccess=true;
					insertSuccess=insertGenres(game);
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return insertSuccess;
	}

	public boolean updateGame(Game game){
		String gameName=null,developer=null,description=null;
		double price=0.0;
		boolean updateSuccess=false;

		if (game!=null&&game.validGame()){
			try {
				gameName=game.getName();
				developer=game.getDev();
				description=game.getDescription();
				price=game.getPrice();

				updateGenres(game);
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
		}

		return updateSuccess;
	}

	public boolean deleteGame(Game game){
		String gameName=null;
		boolean deleteSuccess=false;

		if (game!=null&&game.validGame()){
			try {
				gameName=game.getName();
				deleteGenres(game);
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
		}

		return deleteSuccess;
	}

	private List<String> getGameGenresByName(String gameName){
		List<String> genreList=new ArrayList<String>();
		try {
			preparedStatement= connection.prepareStatement("select * from " +
					"genres where gameName=?");
			preparedStatement.setString(1,gameName);
			resultSet1= preparedStatement.executeQuery();
			while (resultSet1.next()){
				genreList.add(resultSet1.getString(2));
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return genreList;
	}

	private List<Game> getGamesByResultset(ResultSet resultSet){
		List<Game> gameList=new ArrayList<Game>();
		List<String> genres;
		String gameName,developer,description;
		double price;
		try {
			while (resultSet.next()){
				gameName=resultSet.getString(1);
				developer=resultSet.getString(2);
				description=resultSet.getString(3);
				price=resultSet.getDouble(4);
				genres=getGameGenresByName(gameName);
				gameList.add(new Game(gameName,developer,description,price,genres));
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return gameList;
	}

	public List<Game> getAllGames(){
		List<Game> gameList=new ArrayList<Game>();
		resultSet1 =getAll("games");
		gameList=getGamesByResultset(resultSet1);
		return gameList;
	}

	public List<Game> getGamesByNameImplicit(String gameNameImp){
		List<Game> gameList=new ArrayList<Game>();

		if (gameNameImp!=null){
			try {
				preparedStatement= connection.prepareStatement("select * from games " +
						"where LOWER(gameName) like LOWER(?)");
				preparedStatement.setString(1,gameNameImp);
				resultSet1 =preparedStatement.executeQuery();
				gameList=getGamesByResultset(resultSet1);

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return gameList;
	}

	public Game getGameByName(String gameName){
		Game gameResult=null;
		List<Game> gameList;

		if (gameName!=null){
			try {
				preparedStatement= connection.prepareStatement("select * from games " +
						"where gameName=?");
				preparedStatement.setString(1,gameName);
				resultSet1 =preparedStatement.executeQuery();
				gameList=getGamesByResultset(resultSet1);
				if (gameList.size()>0){
					gameResult=gameList.get(0);
				}

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return gameResult;
	}


	private List<Review> getReviewsByResultset(ResultSet resultSet){
		List<Review> reviewList=new ArrayList<>();

		int reviewID;
		String gameName=null,userID=null,reviewContent=null;
		try {

			while(resultSet.next()){
				reviewID= resultSet.getInt(1);
				reviewContent= resultSet.getString(2);
				gameName= resultSet.getString(3);
				userID= resultSet.getString(4);

				reviewList.add(new Review(reviewID,reviewContent,gameName,
						userID));
			}

		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}
		return reviewList;
	}

	public List<Review> getAllReviews(){
		List<Review> reviewList=new ArrayList<>();
		resultSet1=getAll("reviews");
		reviewList=getReviewsByResultset(resultSet1);

		return reviewList;
	}


	public List<Review> getReviewsByGame(String gameName){
		List<Review> reviewList=new ArrayList<>();

		if (gameName!=null){
			try {
				preparedStatement= connection.prepareStatement("select * from " +
						"reviews where gameName=?");
				preparedStatement.setString(1,gameName);
				resultSet1 = preparedStatement.executeQuery();
				reviewList=getReviewsByResultset(resultSet1);

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return reviewList;
	}

	public List<Review> getReviewsByUser(String userID){
		List<Review> reviewList=new ArrayList<>();

		if (userID!=null){
			try {
				preparedStatement= connection.prepareStatement("select * from " +
						"reviews where userID=?");
				preparedStatement.setString(1,userID);
				resultSet1 = preparedStatement.executeQuery();
				reviewList=getReviewsByResultset(resultSet1);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return reviewList;
	}

	public Review getReviewByID(int reviewID){
		Review reviewResult=null;
		List<Review> reviewList;

		try {
			preparedStatement= connection.prepareStatement("select * from " +
					"reviews where reviewID=?");
			preparedStatement.setInt(1,reviewID);
			resultSet1 = preparedStatement.executeQuery();
			reviewList=getReviewsByResultset(resultSet1);
			if (reviewList.size()>0){
				reviewResult=reviewList.get(0);
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return reviewResult;
	}



	public boolean insertReview(Review review){
		boolean insertSucess=false;
		int reviewID;
		String reviewContent=null,gameName=null,userID=null;

		if (review!=null&&review.validReview()){
			try {
				reviewID=review.getReviewID();
				reviewContent=review.getComment();
				gameName=review.getGameName();
				userID=review.getUserID();
				if (reviewID==-1){
					preparedStatement= connection.prepareStatement("insert into reviews(reviewContent,GAMENAME,USERID) values (?,?,?);");
					preparedStatement.setString(1,reviewContent);
					preparedStatement.setString(2,gameName);
					preparedStatement.setString(3,userID);
				}
				else {
					preparedStatement= connection.prepareStatement("insert into reviews " +
							"values(?,?,?,?)");
					preparedStatement.setInt(1,reviewID);
					preparedStatement.setString(2,reviewContent);
					preparedStatement.setString(3,gameName);
					preparedStatement.setString(4,userID);
				}

				updateCount= preparedStatement.executeUpdate();
				if (updateCount==1){
					insertSucess=true;
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return insertSucess;
	}

	public boolean updateReview(Review review){
		int reviewID;
		String reviewContent=null,gameName=null,userID=null;
		boolean updateSucess=false;

		if (review!=null&&review.validReview()){
			try {
				reviewID=review.getReviewID();
				reviewContent=review.getComment();
				gameName=review.getGameName();
				userID=review.getUserID();
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
		}

		return updateSucess;
	}

	public boolean deleteReview(Review review){
		boolean deleteSuccess=false;
		if (review!=null&&review.validReview()){
			try {
				int reviewID=review.getReviewID();
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
		}

		return deleteSuccess;
	}

	private List<Rating> getRatingsByResultset(ResultSet resultSet){
		List<Rating> ratingList=new ArrayList<Rating>();
		double rating=0.0;
		String gameName=null;
		String userID=null;

		try {
			while (resultSet.next()){
				rating= resultSet.getDouble(1);
				gameName= resultSet.getString(2);
				userID= resultSet.getString(3);

				ratingList.add(new Rating(rating,gameName,userID));
			}
		} catch (SQLException | IncorrectFormat sqlException) {
			sqlException.printStackTrace();
		}

		return ratingList;
	}

	public List<Rating> getAllRatings(){
		List<Rating> ratingList=new ArrayList<Rating>();

		resultSet1=getAll("ratings");
		ratingList=getRatingsByResultset(resultSet1);
		return ratingList;
	}

	public List<Rating> getRatingsByGame(String gameName){
		List<Rating> ratingList=new ArrayList<Rating>();

		if (gameName!=null){
			try {
				preparedStatement= connection.prepareStatement("select * from ratings" +
						" where gameName=?");
				preparedStatement.setString(1,gameName);
				resultSet1 = preparedStatement.executeQuery();
				ratingList=getRatingsByResultset(resultSet1);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return ratingList;
	}

	public List<Rating> getRatingsByUser(String userID){
		List<Rating> ratingList=new ArrayList<Rating>();

		if (userID!=null){
			try {
				preparedStatement= connection.prepareStatement("select * from ratings" +
						" where userID=?");
				preparedStatement.setString(1,userID);
				resultSet1 = preparedStatement.executeQuery();
				ratingList=getRatingsByResultset(resultSet1);
			} catch (SQLException  sqlException) {
				sqlException.printStackTrace();
			}
		}

		return ratingList;
	}

	public Rating getRating(String gameName,String userID){
		Rating ratingResult=null;
		List<Rating> ratingList;

		if (gameName!=null&&userID!=null){
			try {
				preparedStatement= connection.prepareStatement("select * from ratings" +
						" where gameName=? and userID=?");
				preparedStatement.setString(1,gameName);
				preparedStatement.setString(2,userID);
				resultSet1 = preparedStatement.executeQuery();
				ratingList=getRatingsByResultset(resultSet1);
				if (ratingList.size()>0){
					ratingResult=ratingList.get(0);
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return ratingResult;
	}

	public boolean insertRating(Rating rating){
		boolean insertSuccess=false;
		double ratingValue=0.0;
		String gameName=null;
		String userID=null;

		if (rating!=null&&rating.validRating()){
			try {
				ratingValue= rating.getRatingValue();
				gameName= rating.getGameName();
				userID= rating.getUserID();
				preparedStatement= connection.prepareStatement("insert into ratings " +
						"values(?,?,?)");
				preparedStatement.setDouble(1,ratingValue);
				preparedStatement.setString(2,gameName);
				preparedStatement.setString(3,userID);
				updateCount= preparedStatement.executeUpdate();
				if (updateCount==1){
					insertSuccess=true;
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return insertSuccess;
	}

	public boolean updateRating(Rating rating){
		boolean updateSuccess=false;
		double ratingValue=0.0;
		String gameName=null;
		String userID=null;

		if (rating!=null&& rating.validRating()){
			try {
				ratingValue= rating.getRatingValue();
				gameName= rating.getGameName();
				userID= rating.getUserID();
				preparedStatement= connection.prepareStatement("update ratings " +
						"set ratingValue=? where gameName=? and userID=?");

				preparedStatement.setDouble(1,ratingValue);
				preparedStatement.setString(2,gameName);
				preparedStatement.setString(3,userID);
				updateCount= preparedStatement.executeUpdate();
				if (updateCount==1){
					updateSuccess=true;
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return updateSuccess;
	}

	public boolean deleteRating(Rating rating){
		boolean deleteSuccess=false;
		int ratingID;

		if (rating!=null&&rating.validRating()){
			try {
				preparedStatement= connection.prepareStatement("delete from ratings " +
						"where gameName=? and userID=?");
				preparedStatement.setString(1,rating.getGameName());
				preparedStatement.setString(2,rating.getUserID());
				updateCount=preparedStatement.executeUpdate();
				if (updateCount==1){
					deleteSuccess=true;
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}

		return deleteSuccess;
	}

}

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
import comp3350.grs.objects.RegisteredUser;
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
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			resultSet1= databaseMetaData.getTables(null, null, "USERS", null);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}


		try {
			if (!resultSet1.next()){
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
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			resultSet1= databaseMetaData.getTables(null, null, "GAMES", null);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		try {
			if (!resultSet1.next()){
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


		System.out.println("Opened " +dbType +" database " +dbPath);
	}

	public void clearDatabase(){
		cmdString="drop table users";
		try {
			statement1.executeUpdate(cmdString);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		cmdString="drop table games";
		try {
			statement1.executeUpdate(cmdString);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void clearTable(){
		cmdString="delete from users";
		try {
			statement1.executeUpdate(cmdString);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		cmdString="delete from games";
		try {
			statement1.executeUpdate(cmdString);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
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

	public User getOneUser(User user){
		User userResult=null;
		String userID=user.getUserID();
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
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}


		try {
			updateCount = preparedStatement.executeUpdate();
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

	public void updateUser(User user){
		String userID;
		String password;

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
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	public void deleteUser(User user){
		String userID;

		userID=user.getUserID();
		try {
			preparedStatement= connection.prepareStatement("delete from users " +
					"where userid=?");
			preparedStatement.setString(1,userID);
			updateCount= preparedStatement.executeUpdate();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
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

	public Game getOneGame(Game game){
		Game gameResult=null;
		String gameName=null,developer=null,description=null;
		double price=0.0;

		gameName=game.getName();

		try {
			preparedStatement= connection.prepareStatement("select * from games " +
					"where gameName=?");
			preparedStatement.setString(1,gameName);
			resultSet1=preparedStatement.executeQuery();
			if (resultSet1.next()){
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

	public void updateGame(Game game){
		String gameName=null,developer=null,description=null;
		double price=0.0;

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

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}

	public void deleteGame(Game game){
		String gameName=null;

		gameName=game.getName();
		try {
			preparedStatement= connection.prepareStatement("delete from games" +
					" " +
					"where gameName=?");
			preparedStatement.setString(1,gameName);
			updateCount= preparedStatement.executeUpdate();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}



}

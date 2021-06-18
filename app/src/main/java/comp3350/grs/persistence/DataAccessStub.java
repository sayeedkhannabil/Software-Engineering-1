package comp3350.grs.persistence;
// CLASS: DataAccessStub
//
// Author: Shiqing Guo
//
// REMARKS: the database which stores users and games
//
//-----------------------------------------



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.User;
import comp3350.grs.objects.Game;
import comp3350.grs.application.Main;
import comp3350.grs.presentation.MainActivity;


public class DataAccessStub
{
	private String dbName;
	private String dbType = "stub";

	private ArrayList<User> users;
	private ArrayList<Game> games;

	public DataAccessStub(String dbName)
	{
		this.dbName = dbName;
	}

	public DataAccessStub()
	{
		this(Main.dbName);
	}



	//------------------------------------------------------
	// open
	//
	// PURPOSE:    open the database, create a bunch of objects according to
	// the json file scraped from steam
	// PARAMETERS:
	//     dbName: name of the database
	//		content: the string read from json file. when we are dealing with
	//		pure java(eg. test database), we can just pass null and let this
	//		method to read from json. but when we are running on a android
	//		simulator, the path of json is not valid anymore, we must read
	//		the file using the android way in activity class, and pass the
	//		string content as parameter
	// Returns: void
	//------------------------------------------------------
	public void open() {
		String content;
		InputStream inputStream=null;
		games=new ArrayList<Game>();


		if (MainActivity.getIsRunning()){
			inputStream=
					this.getClass().getClassLoader().getResourceAsStream("res/raw/csvjson.json");
		}
		else {
			try {
				inputStream=new FileInputStream("src/main/res/raw/csvjson.json");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}


		Scanner s = new Scanner(inputStream).useDelimiter("\\A");
		content = s.hasNext() ? s.next() : "";

		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(content);
			String gameName,gameDev,desc;
			double price;
			Game newGame;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				gameName=jsonObject.getString("title");
				gameDev=jsonObject.getString("developer");
				desc=jsonObject.getString("description");
				price=jsonObject.getDouble("price");
				newGame=new Game(gameName,gameDev,desc,price);
				games.add(newGame);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


		users=new ArrayList<User>();
		User guest= null;
		try {
			guest = new Guest();
		} catch (IncorrectFormat incorrectFormat) {
			incorrectFormat.printStackTrace();
		}
		users.add(guest);
	}

	public void close()
	{
		System.out.println("Closed " +dbType +" database " +dbName);
	}

	//get all the users in database
	public List<User> getAllUsers()
	{
		List<User> result=new ArrayList<User>();
		result.addAll(users);
		return result;
	}

	//get a specific user
	public User getUser(User user){
		int index= users.indexOf(user);
		User result=null;
		if (index>=0){
			result=users.get(index);
		}
		return result;
	}


	public boolean insertUser(User newUser)
	{
		if (newUser.validUser()){
			users.add(newUser);
			return true;
		}
		else {
			return false;
		}
	}

	public void updateUser(User currentUser)
	{
		int index;
		
		index = users.indexOf(currentUser);
		if (index >= 0)
		{
			users.set(index, currentUser);
		}
	}

	public void deleteUser(User user)
	{
		int index;
		
		index = users.indexOf(user);
		if (index >= 0)
		{
			users.remove(index);
		}
	}
	
	public List<Game> getAllGames()
	{
		return games; 
	}
	
	public Game getGame(Game toFind)
	{
		Game toReturn = null;
		int index = games.indexOf(toFind);
		if(index >= 0)
		{
			toReturn = games.get(index);
		}
		return toReturn; 
	}
	
	public void insertGame(Game toAdd)
	{
		games.add(toAdd);
	}
	
	public void updateGame(Game toUpdate)
	{
		int index = games.indexOf(toUpdate); 
		if(index >= 0)
		{
			games.set(index, toUpdate); 
		}
	}
	
	public void deleteGame(Game toDel)
	{
		int index = games.indexOf(toDel);
		if(index >= 0)
		{
			games.remove(index);
		}
	}
}

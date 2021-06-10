package comp3350.grs.persistence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import comp3350.grs.objects.Guest;
import comp3350.grs.objects.User;
import comp3350.grs.objects.Game;
import comp3350.grs.application.Main;

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

	public void open(String dbName) {
		games=new ArrayList<Game>();
		String content = null;
		try {
			content = new Scanner(new File("src/main/java/comp3350/grs/persistence/csvjson.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
		User guest=new Guest();
		users.add(guest);
	}

	public void close()
	{
		System.out.println("Closed " +dbType +" database " +dbName);
	}

	public List<User> getAllUsers()
	{
		List<User> result=new ArrayList<User>();
		result.addAll(users);
		return result;
	}

	public User getUser(User user){
		int index= users.indexOf(user);
		User result=null;
		if (index>=0){
			result=users.get(index);
		}
		return result;
	}


	public void insertUser(User newUser)
	{
		users.add(newUser);
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

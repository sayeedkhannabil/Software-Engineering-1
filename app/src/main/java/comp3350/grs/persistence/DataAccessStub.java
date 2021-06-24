package comp3350.grs.persistence;
// CLASS: DataAccessStub
//
// Author: Shiqing Guo
//
// REMARKS: the database which stores users and games
//
//-----------------------------------------




import java.util.ArrayList;
import java.util.List;

import comp3350.grs.objects.User;
import comp3350.grs.objects.Game;


public class DataAccessStub extends DataAccess implements DataAccessI
{

	@Override
	public void open(String dbPath) {
		super.open(dbPath);
		dbType = "stub";
	}

	public DataAccessStub(String dbName)
	{
		super(dbName);
	}

	public DataAccessStub()
	{
		super();
	}

	public void clearDatabase(){
		users=null;
		games=null;
	}

	public void clearTable(){
		users.clear();
		games.clear();
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
	public User getOneUser(User user){
		int index= users.indexOf(user);
		User result=null;
		if (index>=0){
			result=users.get(index);
		}
		return result;
	}


	public boolean insertUser(User newUser)
	{
		return users.add(newUser);
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


	public Game getOneGame(Game toFind)
	{
		Game toReturn = null;
		int index = games.indexOf(toFind);
		if(index >= 0)
		{
			toReturn = games.get(index);
		}
		return toReturn; 
	}
	
	public boolean insertGame(Game toAdd)
	{
		return games.add(toAdd);
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

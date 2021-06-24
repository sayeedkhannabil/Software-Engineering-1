package comp3350.grs.persistence;


import java.sql.SQLException;
import java.util.List;

import comp3350.grs.objects.Game;
import comp3350.grs.objects.User;

public interface DataAccessI
{
	void open(String dbPath);

	void close();

	void clearDatabase();

	void clearTable();

	List<User> getAllUsers();

	User getOneUser(User user);

	boolean insertUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

	List<Game> getAllGames();

	Game getOneGame(Game game);

	boolean insertGame(Game game);

	void updateGame(Game game);

	void deleteGame(Game game);


}

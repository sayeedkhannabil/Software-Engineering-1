/* 
    this is a modification of the business objects from the example files provided on UMLearn.
    hence most work here is not original, mostly just modified for our project's purposes.
    as a result, many parts are still incomplete (require connection to stub database, other objects, etc) 
*/


package comp3350.grs.business; 
import java.util.List; 
import java.util.ArrayList;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.persistence.DataAccessStub;
import comp3350.grs.objects.Game; 


public class AccessGames
{
    private DataAccessStub dataAccess; // stub database (name taken from example file)
    private List<Game> gameList; 
    private Game currGame; 
    private int currGameIndex; 

    public AccessGames()
    {
        dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
        gameList = null; 
        currGame = null; 
        currGameIndex = 0; 
    }

    public List<Game> getGames()
    {
        gameList=dataAccess.getAllGames();
        return gameList;
    }

    public Game findGame(String name)
    {
        return dataAccess.getGame(new Game(name));
    }

    public Game getSequential()
    {
        if(gameList == null)
        {
            gameList = new ArrayList<Game>();
            getGames();
            currGameIndex = 0; 
        }
        if(currGameIndex < gameList.size())
        {
            currGame = (Game) gameList.get(currGameIndex);
            currGameIndex++; 
        }
        else
        {
            gameList = null;
            currGame = null; 
//            currRating = 0;
        }
        return currGame; 
    }

    public void insertGame(Game currentGame)
    {
        dataAccess.insertGame(currentGame);
    }

    public void updateGame(Game currentGame)
    {
        dataAccess.updateGame(currentGame);
    }

    public void deleteGame(Game currentGame)
    {
        dataAccess.deleteGame(currentGame);
    }
}
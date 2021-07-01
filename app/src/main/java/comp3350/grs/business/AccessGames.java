/* 
    this is a modification of the business objects from the example files provided on UMLearn.
    hence most work here is not original, mostly just modified for our project's purposes.
    as a result, many parts are still incomplete (require connection to stub database, other objects, etc) 
*/


package comp3350.grs.business; 
import java.util.Collections;
import java.util.Comparator;
import java.util.List; 
import java.util.ArrayList;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.objects.Game;


public class AccessGames
{
    private DataAccessI dataAccess;
    private List<Game> gameList; 
    private Game currGame; 
    private int currGameIndex;
    private AccessRatings accessRatings;

    public AccessGames()
    {
        dataAccess =  Services.getDataAccess(Main.dbName);
        accessRatings=new AccessRatings();
        gameList = null; 
        currGame = null; 
        currGameIndex = 0; 
    }

    public List<Game> getAllGames()
    {
        gameList=dataAccess.getAllGames();
        return gameList;
    }
    
    // Sorts the Game(s) in accending order of Name
    public List<Game> accendingNameSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return String.valueOf(o1.getName()).compareTo(o2.getName());
            }
        });

        return gameList;
    }

    // Sorts the Game(s) in descending order of Name
    public List<Game> descendingNameSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return String.valueOf(o2.getName()).compareTo(o1.getName());
            }
        });

        return gameList;
    }

    // Sorts the Game(s) in accending order of price
    public List<Game> accendingPriceSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return Double.valueOf(o1.getPrice()).compareTo(o2.getPrice());
            }
        });

        return gameList;
    }

    // Sorts the Game(s) in descending order of price
    public List<Game> descendingPriceSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return Double.valueOf(o2.getPrice()).compareTo(o1.getPrice());
            }
        });

        return gameList;
    }

    // Sorts the Game(s) in accending order of # of ratings
    public List<Game> accendingRatingSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return accessRatings.getRatingNumByGame(o1.getName())-accessRatings.getRatingNumByGame(o2.getName());
            }
        });

        return gameList;
    }

    // Sorts the Game(s) in descending order of # of ratings
    public List<Game> descendingRatingSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return accessRatings.getRatingNumByGame(o2.getName())-accessRatings.getRatingNumByGame(o1.getName());
            }
        });

        return gameList;
    }

    // Sorts the Game(s) in accending order of # of reviews
//    public List<Game> accendingReviewSort() {
//
//        getGames();
//
//        Collections.sort(gameList, new Comparator<Game>() {
//            @Override
//            public int compare(Game o1, Game o2) {
//                return Integer.valueOf(o1.getNumReviews()).compareTo(o2.getNumReviews());
//            }
//        });
//
//        return gameList;
//    }
//
//    // Sorts the Game(s) in descending order of # of reviews
//    public List<Game> descendingReviewSort() {
//
//        getGames();
//
//        Collections.sort(gameList, new Comparator<Game>() {
//            @Override
//            public int compare(Game o1, Game o2) {
//                return Integer.valueOf(o2.getNumReviews()).compareTo(o1.getNumReviews());
//            }
//        });
//
//        return gameList;
//    } todo

    public Game findGame(String name)
    {
        return dataAccess.getGameByName(name);
    }

    public Game getSequential()
    {
        if(gameList == null)
        {
            gameList = new ArrayList<Game>();
            getAllGames();
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

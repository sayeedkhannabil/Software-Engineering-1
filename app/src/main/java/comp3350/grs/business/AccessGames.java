/* 
    this is a modification of the business objects from the example files provided on UMLearn.
    hence most work here is not original, mostly just modified for our project's purposes.
    as a result, many parts are still incomplete (require connection to stub database, other objects, etc) 
*/


package comp3350.grs.business; 
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.grs.application.Services;
import comp3350.grs.objects.Game;
import comp3350.grs.persistence.DataAccessI;

//business objeect of game
public class AccessGames
{
    private DataAccessI dataAccessI;
    private List<Game> gameList; 
    private Game currGame; 
    private int currGameIndex;
    private AccessRatings accessRatings;
    private AccessReviews accessReviews;

    public AccessGames() {
        dataAccessI =  Services.getDataAccess();
        accessRatings=new AccessRatings();
        accessReviews=new AccessReviews();
        gameList = null; 
        currGame = null; 
        currGameIndex = 0; 
    }

    public void clear(){
        dataAccessI.clearGames();
    }

    public List<Game> getAllGames() {
        gameList= dataAccessI.getAllGames();
        return gameList;
    }
    
    // Sorts the Game(s) in ascending order of Name
    public List<Game> ascendingNameSort() {

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

    // Sorts the Game(s) in ascending order of price
    public List<Game> ascendingPriceSort() {

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

    // Sorts the Game(s) in ascending order of # of ratings
    public List<Game> ascendingRatingSort() {

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

//     Sorts the Game(s) in ascending order of # of reviews
    public List<Game> ascendingReviewSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return accessReviews.getReviewNumByGame(o1.getName())-accessReviews.getReviewNumByGame(o2.getName());
            }
        });

        return gameList;
    }

    // Sorts the Game(s) in descending order of # of reviews
    public List<Game> descendingReviewSort() {

        getAllGames();

        Collections.sort(gameList, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return accessReviews.getReviewNumByGame(o2.getName())-accessReviews.getReviewNumByGame(o1.getName());
            }
        });

        return gameList;
   }

    public Game getGameByName(String name) {
        return dataAccessI.getGameByName(name);
    }

    public List<Game> getGamesByNameImplicit(String gameName){
        String gameNameImp="%"+gameName+"%";
        return dataAccessI.getGamesByNameImplicit(gameNameImp);
    }

    public Game getSequential() {
        if(gameList == null) {
            getAllGames();
            currGameIndex = 0; 
        }
        if(currGameIndex < gameList.size()) {
            currGame = (Game) gameList.get(currGameIndex);
            currGameIndex++; 
        }
        else {
            gameList = null;
            currGame = null; 
        }
        return currGame; 
    }

    public boolean insertGame(Game currentGame) {
        return dataAccessI.insertGame(currentGame);
    }

    public boolean updateGame(Game currentGame) {
        return dataAccessI.updateGame(currentGame);
    }

    public boolean deleteGame(Game currentGame) {
        return dataAccessI.deleteGame(currentGame);
    }
}

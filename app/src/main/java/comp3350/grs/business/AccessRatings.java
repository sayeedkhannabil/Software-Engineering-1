/* 
    this is a modification of the business objects from the example files provided on UMLearn.
    hence most work here is not original, mostly just modified for our project's purposes.
    as a result, many parts are still incomplete (require connection to stub database, other objects, etc) 
*/


package comp3350.grs.business; 
import java.util.List; 
import java.util.ArrayList; 

import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Game;
import comp3350.grs.persistence.DataAccessStub; 


public class AccessRatings
{
    private DataAccessStub dataAccess; // stub database (name taken from example file)
    private List <Rating> ratings; 
    private Rating currRating; 
    private int currRatingIndex; 

    public AccessRatings()
    {
        dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
        ratings = null; 
        rating = null; 
        currRating = 0; 
    }

    public String getRatings(Game game)
    {
        ratings.clear();
        return dataAccess.getAllRatings(game); //returns the ratings for a specific game provided as parameter 
    }

    public Rating getSequential(Game game)
    {
        String result = null; 
        if(ratings == null)
        {
            ratings = new ArrayList<Rating>();
            result = dataAccess.getRatingSequential(game);
            currRatingIndex = 0; 
        }
        if(currRatingIndex < ratings.size())
        {
            currRating = (Rating) ratings.get(currRating);
            currRating++; 
        }
        else
        {
            ratings = null;
            rating = null; 
            currRating = 0;
        }
        return rating; 
    }

   
    public void insertRating(Rating currentRating)
    {
        return dataAccess.insertRating(currentRating);
    }

    public void updateRating(Rating currentRating)
    {
        return dataAccess.updateRating(currentRating);
    }

    public void deleteRating(Rating currentRating)
    {
        return dataAccess.deleteRating(currentRating);
    }
}

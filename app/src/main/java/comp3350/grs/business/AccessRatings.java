// CLASS: AccessRatings
//
// Author: Katharine Kowalchuk
//
// REMARKS: This is the business object for the Rating class. It allows for access to the databases.
//-----------------------------------------

package comp3350.grs.business;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.Rating;
import comp3350.grs.persistence.DataAccessI;

public class AccessRatings {

    private DataAccessI dataAccess;
    private List<Rating> ratings;
    private Rating currentRating;
    private int currentRatingIndex;//index of the rating in the current position in the list

    public AccessRatings()
    {
        dataAccess = (DataAccessI) Services.createDataAccess(Main.dbName);
        ratings = null;
        currentRating = null;
        currentRatingIndex = 0;
    }

    //get a list of all the ratings
    public List<Rating> getRatings()
    {
        ratings = dataAccess.getAllRatings();
        return ratings;
    }

    //get the next rating
    public Rating getSequential()
    {
        if (ratings == null)
        {
            ratings = dataAccess.getAllRatings();
            currentRatingIndex = 0;
        }
        if (currentRatingIndex < ratings.size())
        {
            currentRating =  ratings.get(currentRatingIndex);
            currentRatingIndex++;
        }
        else
        {
            ratings = null;
            currentRating = null;
            currentRatingIndex = 0;
        }
        return currentRating;
    }

    public List<Rating> getRatingsByUser(String userID)
    {
        return dataAccess.getRatingsByUser(userID);
    }

    public List<Rating> getRatingsByGame(String gameName)
    {
        return dataAccess.getRatingsByGame(gameName);
    }

    public Rating getRating(String gameName,String userID){
        return dataAccess.getRating(gameName,userID);
    }

    //get an overall rating for a game by the game name
    public double getOverallRating(String gameName){
        double overallRating = 0.0;
        double totalPts = 0.0;
        int numRatings = 0;
        Rating thisRating;
        if (!gameName.trim().equals(""))
        {
            getRatings(); //get ratings from database
            for(int i = 0; i < ratings.size(); i++)
            {
                thisRating = ratings.get(i);
                if((thisRating.getGameName()).equals(gameName))
                {
                    totalPts += thisRating.getRating();
                    numRatings ++;
                }
            }
            overallRating = totalPts/numRatings;
        }
        return overallRating;
    }

    public boolean insertRating(Rating newRating)
    {
        boolean inserted = false;
        Rating test = null;
        if (newRating != null){
            dataAccess.insertRating(newRating);
            test = dataAccess.getRating(newRating.getGameName(), newRating.getUserID()); //should
            // return a
            // Rating object associated with newRating's ratingID

            if(test != null) //if test is null, the newRating was not found in the database (not inserted properly)
            {
                inserted = true;
            }
        }

        return inserted;
    }

    public boolean updateRating(Rating updatedRating)
    {
        boolean result=false;
        if (updatedRating != null){
            result=dataAccess.updateRating(updatedRating);
        }
        return result;
    }

    public boolean deleteRating(Rating toDelete)
    {
        boolean result=false;
        if (toDelete != null){
            result=dataAccess.deleteRating(toDelete);
        }
        return result;
    }
}

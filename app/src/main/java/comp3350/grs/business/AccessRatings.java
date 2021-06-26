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
        dataAccess = (DataAccessI) Services.getDataAccess(Main.dbName);
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
            test = dataAccess.getRatingByID(newRating.getRatingID()); //should return a Rating object associated with newRating's ratingID

            if(test != null) //if test is null, the newRating was not found in the database (not inserted properly)
            {
                inserted = true;
            }
        }

        return inserted;
    }

    public void updateRating(Rating updatedRating)
    {
        if (updatedRating != null){
            dataAccess.updateRating(updatedRating);
        }

    }

    public boolean deleteRating(Rating toDelete)
    {
        boolean deleted = false;
        Rating test = null;
        if (toDelete != null){
            dataAccess.deleteRating(toDelete);
            test = dataAccess.getRatingByID(toDelete.getRatingID()); //should NOT return a Rating object associated with newRating's ratingID

            if(test == null) //if test is null, the newRating was not found in the database (deletion successful)
            {
                deleted = true;
            }
        }

        return deleted;
    }
}

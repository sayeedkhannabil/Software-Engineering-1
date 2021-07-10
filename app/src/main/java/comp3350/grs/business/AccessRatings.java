// This is the business object for the Rating class.

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

    public AccessRatings() {
        dataAccess = (DataAccessI) Services.getDataAccess(Main.dbName);
        ratings = null;
        currentRating = null;
        currentRatingIndex = 0;
    }

    //get a list of all the ratings
    public List<Rating> getRatings() {
        ratings = dataAccess.getAllRatings();
        return ratings;
    }

    //get the next rating
    public Rating getSequential() {
        if (ratings == null) {
            ratings = dataAccess.getAllRatings();
            currentRatingIndex = 0;
        }
        if (currentRatingIndex < ratings.size()) {
            currentRating =  ratings.get(currentRatingIndex);
            currentRatingIndex++;
        }
        else {
            ratings = null;
            currentRating = null;
            currentRatingIndex = 0;
        }
        return currentRating;
    }

    public List<Rating> getRatingsByUser(String userID){
        return dataAccess.getRatingsByUser(userID);
    }

    public List<Rating> getRatingsByGame(String gameName) {
        return dataAccess.getRatingsByGame(gameName);
    }

    public Rating getRating(String gameName,String userID){
        return dataAccess.getRating(gameName,userID);
    }

    public int getRatingNumByGame(String gameName){
        return dataAccess.getRatingsByGame(gameName).size();
    }

    public int getRatingNumByUser(String userID){
        return dataAccess.getRatingsByUser(userID).size();
    }

    //get an overall rating for a game by the game name
    public double getOverallRating(String gameName){
        double overallRating = 0.0;
        double totalPts = 0.0;
        int numRatings = 0;
        Rating thisRating;
        if (!gameName.trim().equals("")) {
            this.getRatings(); //get ratings from database
            for(int i = 0; i < ratings.size(); i++) {
                thisRating = ratings.get(i);
                if((thisRating.getGameName()).equals(gameName)) {
                    totalPts += thisRating.getRatingValue();
                    numRatings ++;
                }
            }
            overallRating = totalPts/numRatings;
        }
        return overallRating;
    }

    public boolean insertRating(Rating newRating) {
        return dataAccess.insertRating(newRating);
    }

    public boolean updateRating(Rating updatedRating) {
        return dataAccess.updateRating(updatedRating);
    }

    public boolean deleteRating(Rating toDelete) {
        return dataAccess.deleteRating(toDelete);
    }
}

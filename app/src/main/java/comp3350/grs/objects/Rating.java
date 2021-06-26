// CLASS: Rating
//
// Author: Katharine Kowalchuk
//
// REMARKS: This class represents the rating of a game in the system.
//          Any time a valid rating is added to a game, a Rating object is created and holds the rating value,
//          the active user giving the rating, as well as a toString() method, input validation,
//          and will have more functionality in the future.
//-----------------------------------------
package comp3350.grs.objects;

import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;

public class Rating
{
    private int ratingID;//used to uniquely identify a rating
    private double rating; //from one to five
    private String gameName;
    private String userID;

    private static int nextRatingID=1;

    //create a rating, use auto generated ratingID
    public Rating (double ratingGiven,String gameName,String userID) {
        rating = 0.0; // default -- if no ratings (or no valid ratings) given yet
        checkRating(ratingGiven);
        //input validation
        rating = ratingGiven;
        this.userID=userID;
        this.gameName=gameName;
        this.ratingID=nextRatingID;
        nextRatingID++;
    }

    //create a rating, specify the ratingID
    public Rating (int ratingID,double ratingGiven,String gameName,
                   String userID) throws IncorrectFormat {
        rating = 0.0; // default -- if no ratings (or no valid ratings) given yet
        checkRating(ratingGiven);
        //input validation
        rating = ratingGiven;
        this.userID=userID;
        this.gameName=gameName;
        this.ratingID=ratingID;
    }

    private void checkRating(double rating){
        if(rating <= 0 || rating > 5){
            //surrounding with a try-catch was the only way to avoid the unchecked exception.
            //Will try to come up with a better way to deal with this in the future, but needed to use the Rating class so needed a quick solution
            try {
                throw new IncorrectFormat("rating should be between 0 and 5.");
            } catch (IncorrectFormat incorrectFormat) {
                incorrectFormat.printStackTrace();
            }
        }
    }

    public double getRating()
    {
        return rating;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getGameName(){
        return gameName;
    }

    public int getRatingID(){
        return ratingID;
    }

    public boolean equals(Rating rating){
        return this.ratingID==rating.getRatingID();
    }

    public String toString()
    {
        String str = "UserID: "+userID+"\nRating: "+rating+" out of 5 points.\n";
        if(rating == 0)
        {
            str = "Invalid rating.";
        }
        return str;
    }
}
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
    private double rating; //from one to five
    private String gameName;
    private String userID;


    //create a rating, use auto generated ratingID
    public Rating (double ratingGiven,String gameName,String userID) throws IncorrectFormat {
        rating = 0.0; // default -- if no ratings (or no valid ratings) given yet
        checkRating(ratingGiven);
        //input validation
        rating = ratingGiven;
        this.userID=userID;
        this.gameName=gameName;

    }

    public Rating(double rating) throws IncorrectFormat {
        rating = 0.0; // default -- if no ratings (or no valid ratings) given yet
        checkRating(rating);
        //input validation
        rating = rating;
        this.userID=null;
        this.gameName=null;
    }


    private void checkRating(double rating) throws IncorrectFormat {
        if(rating <= 0 || rating > 5){
            throw new IncorrectFormat("rating should be between 0 and 5.");
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


    public boolean equals(Rating rating){
        return this.userID.equals(rating.userID)&&this.gameName.equals(rating.gameName);
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
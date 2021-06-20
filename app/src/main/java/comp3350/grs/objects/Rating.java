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

public class Rating
{
    private double rating; //from one to five
    private User user;

    public Rating (double ratingGiven, User currUser) {
        rating = 0.0; // default -- if no ratings (or no valid ratings) given yet

        //input validation
        if(ratingGiven > 0 && ratingGiven <= 5)
        {
            rating = ratingGiven;
            user = currUser;
        }
        else
        {
            System.out.println("Rating must be between 1 and 5, inclusive. Try again.");
        }
    }

    public double getRating()
    {
        return rating;
    }

    public String getUser()
    {
        return user.getUserID();
    }

    public String toString()
    {
        String str = "UserID: "+user.getUserID()+"\nRating: "+rating+" out of 5 points.\n";
        if(rating == 0)
        {
            str = "Invalid rating.";
        }
        return str;
    }
}
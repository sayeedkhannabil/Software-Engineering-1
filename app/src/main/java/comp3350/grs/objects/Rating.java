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
    private int rating; //from one to five
    private User user;

    public Rating (int ratingGiven) {
        rating = 0; // default -- if no ratings (or no valid ratings) given yet

        //input validation
        if(ratingGiven > 0 && ratingGiven <= 5)
        {
            rating = ratingGiven;
            user = AccessUsers.getActiveUser();
        }
        else
        {
            System.out.println("Rating must be an integer between 1 and 5, inclusive. Try again.");
        }
    }

    public int getRating()
    {
        return rating;
    }

    public User getUser()
    {
        return user;
    }

    public String toString()
    {
        String str = "UserID: "+user.getUserID()+"Rating: "+rating+" out of 5 points.";
        if(rating == 0)
        {
            str = "Invalid rating.";
        }
        return str;
    }
}
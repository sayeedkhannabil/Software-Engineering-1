// This class represents the rating of a game in the system.
// Any time a valid rating is added to a game, a Rating object is created and holds the rating value,
// the active user giving the rating, as well as a toString() method, input validation,
// and will have more functionality in the future.
package comp3350.grs.objects;
import comp3350.grs.exceptions.IncorrectFormat;
//domain object, rating of a game
public class Rating
{
    private double ratingValue; //from one to five
    private String gameName;
    private String userID;


    public Rating (double ratingGiven,String gameName,String userID) throws IncorrectFormat {
        checkRating(ratingGiven);
        ratingValue = ratingGiven;
        this.userID=userID;
        this.gameName=gameName;
    }

    public Rating(double ratingGiven) throws IncorrectFormat {
        checkRating(ratingGiven);
        ratingValue = ratingGiven;
        this.userID = null;
        this.gameName = null;

    }

    public Rating(){
        ratingValue = 0.0;
    }

    //check if the rating is valid(important info is not null)
    public boolean valid(){
        return this.userID!=null&&this.gameName!=null;
    }

    //check the format of rating is correct
    private void checkRating(double rating) throws IncorrectFormat {
        final int MAX_RATING = 5;
        if(rating <= 0 || rating > MAX_RATING){
            throw new IncorrectFormat("rating should be between 0 and 5.");
        }
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public String getUserID() {
        return userID;
    }

    public String getGameName(){
        return gameName;
    }

    @Override
    public boolean equals(Object object){
        boolean result;
        result = false;
        Rating rating;

        if (object!=null&& valid()&& object instanceof Rating){
            rating=(Rating)object;
            result=
                    this.userID.equals(rating.userID)&&this.gameName.equals(rating.gameName);
        }
        return result;
    }

    public String toString() {
        return  "UserID: "+userID+"\nRating: "+ ratingValue +" out of 5 points.\n";
    }
}
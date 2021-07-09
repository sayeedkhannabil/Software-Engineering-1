// This class represents the rating of a game in the system.
// Any time a valid rating is added to a game, a Rating object is created and holds the rating value,
// the active user giving the rating, as well as a toString() method, input validation,
// and will have more functionality in the future.
package comp3350.grs.objects;
import comp3350.grs.business.AccessGames;
import comp3350.grs.exceptions.IncorrectFormat;

public class Rating
{
    private final int maxRating = 5;
    private double ratingValue; //from one to five
    private String gameName;
    private String userID;


    public Rating (double ratingGiven,String gameName,String userID) {
        ratingValue = 0.0; // default -- if no ratings (or no valid ratings) given yet

        try {
            checkRating(ratingGiven);
            checkGameName(gameName);

            ratingValue = ratingGiven;
            this.userID=userID;
            this.gameName=gameName;
        }
        catch (IncorrectFormat incorrectFormat) {
            System.out.println(incorrectFormat.getMessage());
        }
    }

    public Rating(double ratingGiven) {
        ratingValue = 0.0; // default -- if no ratings (or no valid ratings) given yet
        try {
            checkRating(ratingGiven);

            ratingValue = ratingGiven;
            this.userID = null;
            this.gameName = null;
        }
        catch (IncorrectFormat incorrectFormat) {
            System.out.println(incorrectFormat.getMessage());
        }
    }

    public Rating(){
        ratingValue = 0.0;
    }


    public boolean validRating(){
        return this.userID!=null&&this.gameName!=null;
    }

    private void checkRating(double rating) throws IncorrectFormat {
        if(rating <= 0 || rating > maxRating){
            throw new IncorrectFormat("rating should be between 0 and 5.");
        }
    }

    private void checkGameName(String game) throws IncorrectFormat {
        AccessGames gameAccess = new AccessGames();
        if(gameAccess.findGame(game) == null){
            throw new IncorrectFormat("The game does not exist in the database. Cannot rate a non-existent game.");
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


    public boolean equals(Object object){
        boolean result;
        result = false;
        Rating rating;

        if (object instanceof Rating){
            rating=(Rating)object;
            result=
                    this.userID.equals(rating.userID)&&this.gameName.equals(rating.gameName);
        }
        return result;
    }

    public String toString() {
        String str = "UserID: "+userID+"\nRating: "+ ratingValue +" out of 5 points.\n";
        if(ratingValue == 0){
            str = "Invalid rating.";
        }
        return str;
    }
}
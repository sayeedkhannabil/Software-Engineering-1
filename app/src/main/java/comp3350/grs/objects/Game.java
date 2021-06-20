// CLASS: Game
//
// Author: Katharine Kowalchuk + some modifications by other group members
//
// REMARKS: This class represents a game in the system.
//          A game object contains a list of feedback, which consists of ratings and reviews given by a user.
//-----------------------------------------

package comp3350.grs.objects;
import java.util.ArrayList;

import comp3350.grs.business.AccessUsers;

public class Game
{
    private String name;
    private String dev; //name of company/game developer
    private String genre;
    private String description;
    private double currPrice; //current price of game

    private double overallRating; //average of all ratings provided by users for this game
    private ArrayList<Review> reviews;
    private double qualPts; //all ratings added (to get average)
    private int numRatings;
    private int numReviews;

    //detailed constructor
    public Game(String gameName, String gameDev, String g, String desc, double price)
    {
        name = gameName;
        dev = gameDev;
        genre = g;
        description = desc;
        currPrice = price;

        qualPts = 0;
        numRatings = 0;
        numReviews = 0;

        //initialize overallRating and reviews to 0 and empty as the game is just being added (hence has no reviews/rating yet)
        overallRating = 0.0;
        reviews = new ArrayList<Review>();
    }

    //simple constructor
    public Game(String gameName)
    {
        name = gameName;
        dev = null;
        genre = null;
        description = null;
        currPrice = -1;

        qualPts = 0;
        numRatings = 0;
        numReviews = 0;

        //initialize overallRating and reviews to 0 and empty as the game is just being added (hence has no reviews/rating yet)
        overallRating = 0.0;
        reviews = new ArrayList<Review>();
    }

    //default constructor
    public Game()
    {
        name = "null";
        dev = null;
        genre = null;
        description = null;
        currPrice = -1;

        qualPts = 0;
        numRatings = 0;
        numReviews = 0;

        //feedback is a null list for a game with no name (default)
        overallRating = 0.0;
        reviews = new ArrayList<Review>();
    }

    public String getName()
    {
        return name;
    }

    public String getDev()
    {
        return dev;
    }

    public String getGenre() { return genre; }

    //------------------------------------------------------
    // addRating
    //
    // PURPOSE:    creates Rating object and Feedback object, updates game's overallRating,
    //              and adds Feedback object to game's Feedback list
    // PARAMETERS:
    //     newRating(int): the rating with which to create Rating object and update overall rating, if the rating given is valid
    // Returns: void
    //------------------------------------------------------
    public void addRating(double newRating)
    {
        /* create new Rating object, update overall rating*/
        Rating rating = new Rating(newRating, AccessUsers.getActiveUser());
        if(rating.getRating() > 0 && !name.equals("null"))
        {
            updateOverall(rating);
        }else {
            System.out.println("Unable to add rating.");
        }
    }

    public double getRating()
    {
        if(overallRating == 0.0 && !name.equals("null"))
        {
            System.out.println("The game "+name+" has not yet been rated.");
        }
        return overallRating;
    }

    //------------------------------------------------------
    // addReview
    //
    // PURPOSE:    creates Rating object, Review object, and Feedback object, updates game's overallRating,
    //              and adds Feedback object to game's Feedback list
    // PARAMETERS:
    //     newRating(int): the rating with which to create Rating object and update overall rating, if the rating given is valid
    //     newReview(String): the review that will be used to create the Review object, if the String is not empty
    // Returns: void
    //------------------------------------------------------

    public void addReview(String newReview)
    {
        /* create new Rating object, new Review object and new Feedback object, add Feedback object to list*/
        Review review = new Review(newReview, AccessUsers.getActiveUser());
        if(!newReview.equals("") && !name.equals("null"))
        {
            reviews.add(review);
            numReviews++;
        }
        else {
            System.out.println("Unable to add review.");
        }
    }

    //------------------------------------------------------
    // updateOverall (private method)
    //
    // PURPOSE:    updates the overall rating to display to users for this game.
    //               this is the logic component of the program so far.
    // PARAMETERS:
    //     rating (Rating): obtains the integer value of the rating, and updates the game accordingly.
    // Returns: void
    //------------------------------------------------------

    private void updateOverall(Rating rating)
    {
        double toAdd = rating.getRating();
        qualPts += toAdd;
        numRatings++;
        overallRating = 1.0 * qualPts/numRatings;
    }

    //------------------------------------------------------
    // getReviews
    //
    // PURPOSE:    extracts Reviews from Feedback list (which is a mix of Reviews and Ratings), returns list of reviews
    // PARAMETERS:
    //     none
    // Returns: Review ArrayList
    //------------------------------------------------------
    public ArrayList<Review> getReviews()
    {
        ArrayList<Review> reviews = new ArrayList<>();
        if(numReviews == 0 && name != null)
        {
            System.out.println("The game "+name+" has not yet been reviewed.");
        }
        return reviews;
    }

    public String getDescription()
    {
        return description;
    }

    public double getPrice()
    {
        return currPrice;
    }

    public int getNumRatings() { return numRatings; }

    public int getNumReviews() { return numReviews; }

    //------------------------------------------------------
    // equals
    //
    // PURPOSE:    compares another object with this Game to see if they are equal.
    //              equality depends on the name of the game, for now.
    // PARAMETERS:
    //     otherGame (Object): the other object with which to compare this one
    // Returns: boolean value
    //------------------------------------------------------
    public boolean equals(Object otherGame)
    {
        Game other = null;
        boolean isSame = false;

        if(otherGame instanceof Game)
        {
            other = (Game) otherGame;
            if((this.name).equals(other.getName()) || ((this.name == null) && (other.getName() == null)))
            {
                isSame = true;
            }
        }
        return isSame;
    }

    public String toString()
    {
        return "Game: "+name+", Developer: "+dev+", Genre: "+genre+", Overall Rating: "+overallRating+", Current Price: "+currPrice+"\nDescription: "+description;
    }
}


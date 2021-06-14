// CLASS: Game
//
// Author: Katharine Kowalchuk + some modifications by other group members
//
// REMARKS: This class represents a game in the system.
//          A game object contains a list of feedback, which consists of ratings and reviews given by a user.
//-----------------------------------------

package comp3350.grs.objects;
import java.util.ArrayList;

public class Game
{
    private String name;
    private String dev; //name of company/game developer
    private String description; // genre, etc
    private double currPrice; //current price of game

    private double overallRating; //average of all ratings provided by users for this game 
    private ArrayList<Feedback> feedback; //these will determine overallRating (the rating the user sees)
    private int qualPts; //all ratings added (to get average)
    private int numRatings;
    private int numReviews;

    //detailed constructor
    public Game(String gameName, String gameDev, String desc, double price)
    {
        name = gameName;
        dev = gameDev;
        description = desc;
        currPrice = price;

        qualPts = 0;
        numRatings = 0;
        numReviews = 0;

        //initialize overallRating and reviews to 0 and empty as the game is just being added (hence has no reviews/rating yet)
        overallRating = 0.0;
        feedback = new ArrayList<Feedback>();
    }

    //simple constructor
    public Game(String gameName)
    {
        name = gameName;
        dev = null;
        description = null;
        currPrice = -1;

        qualPts = 0;
        numRatings = 0;
        numReviews = 0;

        //initialize overallRating and reviews to 0 and empty as the game is just being added (hence has no reviews/rating yet)
        overallRating = 0.0;
        feedback = new ArrayList<Feedback>();
    }

    //default constructor
    public Game()
    {
        name = "null";
        dev = null;
        description = null;
        currPrice = -1;

        qualPts = 0;
        numRatings = 0;
        numReviews = 0;

        //feedback is a null list for a game with no name (default)
        overallRating = 0.0;
        feedback = new ArrayList<Feedback>();
    }

    public String getName()
    {
        return name;
    }

    public String getDev()
    {
        return dev;
    }

    //------------------------------------------------------
    // addRating
    //
    // PURPOSE:    creates Rating object and Feedback object, updates game's overallRating,
    //              and adds Feedback object to game's Feedback list
    // PARAMETERS:
    //     newRating(int): the rating with which to create Rating object and update overall rating, if the rating given is valid
    // Returns: void
    //------------------------------------------------------
    public void addRating(int newRating)
    {
        /* create new Rating object and new Feedback object, add Feedback object to list*/
        Rating rating;
        Feedback newFeedback;
        if(newRating >= 1 && !name.equals("null"))
        {
            rating = new Rating(newRating);
            newFeedback = new Feedback(rating);
            feedback.add(newFeedback);
            //update overallRating
            updateOverall(rating);
        }else {
            System.out.println("Unable to add rating.");
        }
    }

    public double getRating()
    {
        if(overallRating == 0.0 && !name.equals(null))
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

    public void addReview(int newRating, String newReview)
    {
        /* create new Rating object, new Review object and new Feedback object, add Feedback object to list*/
        Rating rating = new Rating(newRating);
        Review review = new Review(newReview);
        Feedback newFeedback;
        if(rating.getRating() >= 1 && !newReview.equals("") && !name.equals("null"))
        {
            newFeedback = new Feedback(rating, review);
            feedback.add(newFeedback);
            //update overallRating
            updateOverall(rating);
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
        int toAdd = rating.getRating();
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
        else
        {
            for(int i = 0; i < feedback.size(); i++)
            {
                if((feedback.get(i)).isReview())
                {
                    reviews.add((Review) feedback.get(i).getReview());
                }
            }
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
        return "Game: "+name+", Developer: "+dev+", Overall Rating: "+overallRating+", Current Price: "+currPrice+"\nDescription: "+description;
    }
}


package comp3350.grs.objects; 
import java.util.List; 
import java.util.ArrayList;

public class Game
{
    private String name;
    private String dev; //name of company/game developer
    private String description; // genre, etc
    private double currPrice; //current price of game

    private double overallRating; //average of all ratings provided by users for this game 
    private List<Feedback> feedback; //these will determine overallRating (the rating the user sees)
    private int qualPts; //all ratings added (to get average)
    private int numRatings;
    private int numReviews;

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

    public String getName()
    {
        return name;
    }

    public String getDev()
    {
        return dev;
    }

    public void addRating(int newRating)
    {
        /* create new Rating object and new Feedback object, add Feedback object to list*/
        Rating rating = new Rating(newRating);
        Feedback newFeedback = new Feedback(rating);
        feedback.add(newFeedback);

        //update overallRating
        updateOverall(rating);
    }

    public double getRating()
    {
        if(overallRating == 0.0)
        {
            System.out.println("This game has not yet been rated.");
        }
        return overallRating;
    }

    public void addReview(int newRating, String newReview)
    {
        /* create new Rating object, new Review object and new Feedback object, add Feedback object to list*/
        Rating rating = new Rating(newRating);
        Review review = new Review(newReview);
        Feedback newFeedback = new Feedback(rating, review);
        feedback.add(newFeedback);

        updateOverall(rating);
        numReviews++;
    }

    private void updateOverall(Rating rating)
    {
        int toAdd = rating.getRating();
        qualPts += toAdd;
        numRatings++;
        overallRating = 1.0 * qualPts/numRatings;
    }

    public List<Review> getReviews()
    {
        Feedback curr = null;
        List reviews = new ArrayList<Review>();
        if(numReviews == 0)
        {
            System.out.println("This game has not yet been reviewed.");
        }
        else
        {
            for(int i = 0; i < feedback.size(); i++)
            {
                curr = feedback.get(i);
                if(curr.isReview())
                {
                    System.out.println(curr.getReview().toString());
                    reviews.add(curr.getReview());
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

    /* right now two games are the same if they have the same name,
        we can change this though if needed  */
    public boolean equals(Object otherGame)
    {
        Game other = null;
        boolean isSame = false;

        if(otherGame instanceof Game)
        {
            other = (Game) otherGame;
            if((this.name).equals(other.name))
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


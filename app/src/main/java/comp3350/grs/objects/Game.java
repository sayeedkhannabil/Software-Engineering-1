package comp3350.grs.objects; 
import java.util.List; 
import java.util.ArrayList; 
import comp3350.grs.objects.Review;
import comp3350.grs.objects.Rating;

public class Game
{
    private String name; 
    private String dev; //name of company/game developer 
    private double overallRating; //average of all ratings provided by users for this game 
    private List<Rating> ratings; //these will determine overallRating (the rating the user sees)
    private int qualPts; //all ratings added (to get average)
    private int numRatings; 
    private List<Review> reviews; //all reviews for this game 
    private int numReviews; 
    private String description; // genre, etc 
    private double currPrice; //current price of game 

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

    public void addRating(Rating newRating)
    {
        ratings.add(newRating);
        int toAdd = newRating.getRating();
        
        //update overallRating 
        qualPts += toAdd; 
        numRatings ++; 
        overallRating = 1.0 * qualPts/numRatings; 
    }

    public double getRating()
    {
        if(overallRating == 0.0)
        {
            System.out.println("This game has not yet been rated.");
        }
        return overallRating; 
    }

    public void addReview(Review newReview)
    {
        reviews.add(newReview);
        numReviews++; 
    }

    public List<Review> getReviews()
    {
        if(reviews == null)
        {
            System.out.println("This game has not yet been reviewed.");
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


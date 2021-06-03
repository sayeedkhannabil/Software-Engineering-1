package comp3350.grs.objects;

public class Rating
{
    private int rating; //from one to five 
    //potentially make a field with the associated Game object (?), so the new rating can directly update the overall game rating 

    public Rating(int ratingGiven)
    {
        rating = 0; // default -- if no ratings (or no valid ratings) given yet
        if(ratingGiven > 0 && ratingGiven <= 5)
        {
            rating = ratingGiven; 
        }
    }

    public int getRating()
    {
        return rating; 
    }

    public String toString()
    {
        String msg = ""; 
        if(rating == 0)
        {
            msg = "No ratings have yet been given for this game.";
        }
        else
        {
            msg = "Game Rating: "+rating+" out of 5 points.";
        }
        return msg;
    }
}

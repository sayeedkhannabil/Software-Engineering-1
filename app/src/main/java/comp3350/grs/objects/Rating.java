package comp3350.grs.objects;

public class Rating
{
    private int rating; //from one to five 

    public Rating(int ratingGiven)
    {
        rating = 0; // default -- if no ratings (or no valid ratings) given yet

        if(ratingGiven > 0 && ratingGiven <= 5)
        {
            rating = ratingGiven;
        }
        else
        {
            System.out.println("Invalid rating. Try again.");
        }
    }

    public int getRating()
    {
        return rating;
    }

    public String toString()
    {
        return "Rating: "+rating+" out of 5 points.";
    }
}
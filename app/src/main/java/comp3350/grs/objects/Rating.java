package comp3350.grs.objects;

import comp3350.grs.objects.Game;

public class Rating
{
    private int rating; //from one to five 
    private Game belongsTo; //so the new rating can directly update the overall game rating -- if there is a better way to do this feel free to change it

    public Rating(int ratingGiven, Game rated)
    {
        rating = 0; // default -- if no ratings (or no valid ratings) given yet
        belongsTo = rated; 
        
        if(ratingGiven > 0 && ratingGiven <= 5)
        {
            rating = ratingGiven;
            belongsTo.addRating(this); //add this rating to the game 
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
        return "New rating for game "+belongsTo.getName()+": "+rating+" out of 5 points.";
    }
}

/* 
    this is a modification of the business objects from the example files provided on UMLearn.
    hence most work here is not original, mostly just modified for our project's purposes.
    as a result, many parts are still incomplete (require connection to stub database, other objects, etc) 
*/


package comp3350.grs.business; 
import java.util.List; 
import java.util.ArrayList; 

import comp3350.grs.objects.Rating;
//add imports for Main, Services, and Database once implemented 


public class AccessRatings
{
    //private DataAccessStub dataAccess; // stub database (name taken from example file)
    private List <Rating> ratings; 
    private Rating rating; 
    private int currRating; 

    public AccessRatings()
    {
        //dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
        ratings = null; 
        rating = null; 
        currRating = 0; 
    }

    public String getRatings(List <Rating> ratings)
    {
        //ratings.clear(); --- this was in the example, but why clear it ?
        return " "; // actually return dataAccess.getRatingSequential(ratings); once database implemented
    }

    public Rating getSequential()
    {
        //String result = null; 
        if(ratings == null)
        {
            ratings = new ArrayList<Rating>();
            //result = dataAccess.getRatingSequential(ratings);
            currRating = 0; 
        }
        if(currRating < ratings.size())
        {
            rating = (Rating) ratings.get(currRating);
            currRating++; 
        }
        else
        {
            ratings = null;
            rating = null; 
            currRating = 0;
        }
        return rating; 
    }

    //change these three below to String return type once import Database class
    public void insertRating(Rating currentRating)
    {
        //return dataAccess.insertRating(currentRating);
    }

    public void updateRating(Rating currentRating)
    {
        //return dataAccess.updateRating(currentRating);
    }

    public void deleteRating(Rating currentRating)
    {
        //return dataAccess.deleteStudent(currentRating);
    }
}
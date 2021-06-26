// CLASS: Game
//
// Author: Katharine Kowalchuk + some modifications by other group members
//
// REMARKS: This class represents a game in the system.
//          A game object contains a list of feedback, which consists of ratings and reviews given by a user.
//-----------------------------------------

package comp3350.grs.objects;
import java.util.ArrayList;

import comp3350.grs.business.AccessRatings;

public class Game
{
    private String name;
    private String dev; //name of company/game developer
    private ArrayList<String> genres;
    private String description; // genre, etc
    private double currPrice; //current price of game
    private double overallRating; //average of all ratings provided by users for this game

    //detailed constructor
    public Game(String gameName, String gameDev, ArrayList<String> gen, String desc, double price)
    {
        name = gameName;
        dev = gameDev;
        description = desc;
        genres = gen;
        currPrice = price;
        overallRating = 0.0;
    }

    //simple constructor
    public Game(String gameName)
    {
        name = gameName;
        dev = null;
        description = null;
        currPrice = -1;

        overallRating = 0.0;
    }

    //default constructor
    public Game()
    {
        name = "null";
        dev = null;
        description = null;
        currPrice = -1;

        overallRating = 0.0;
    }

    public String getName()
    {
        return name;
    }

    public String getDev()
    {
        return dev;
    }

    public double getRating()
    {
        AccessRatings newAccess = new AccessRatings();
        overallRating = newAccess.getOverallRating(name);

        if(overallRating == 0.0 && !name.equals("null"))
        {
            System.out.println("The game "+name+" has not yet been rated.");
        }
        return overallRating;
    }

    public ArrayList<String> getGenres()
    {
        return genres;
    }

    public String getDescription()
    {
        return description;
    }

    public double getPrice()
    {
        return currPrice;
    }

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
        String genreList = "";
        for(int i = 0; i < genres.size(); i++)
        {
            genreList += genres.get(i) + ", ";
        }
        return "Game: "+name+", Developer: "+dev+", Overall Rating: "+overallRating+", Current Price: "+currPrice+"\nGenres: "+genreList+"\nDescription: "+description;
    }
}


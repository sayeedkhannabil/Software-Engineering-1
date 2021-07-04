// CLASS: Game
//
// Author: Katharine Kowalchuk + some modifications by other group members
//
// REMARKS: This class represents a game in the system.
//          A game object contains a list of feedback, which consists of ratings and reviews given by a user.
//-----------------------------------------

package comp3350.grs.objects;
import java.util.ArrayList;
import java.util.List;

import comp3350.grs.business.AccessRatings;
import comp3350.grs.exceptions.IncorrectFormat;

public class Game
{
    private String name;
    private String dev; //name of company/game developer
    private List<String> genres;
    private String description;
    private double currPrice; //current price of game

    //detailed constructor
    public Game(String gameName, String gameDev, String desc, double price,
                List<String> gen) {
        //check for valid input
        try {
            validName(gameName);
            validPrice(price);
        }
        catch(IncorrectFormat incorrectFormat)
        {
            System.out.println(incorrectFormat.getMessage());
        }

        name = gameName;
        dev = gameDev;
        description = desc;
        genres = gen;
        currPrice = price;
    }

    //simple constructor
    public Game(String gameName)
    {
        try
        {
            validName(gameName);
        }
        catch (IncorrectFormat incorrectFormat)
        {
            System.out.println(incorrectFormat.getMessage());
        }
        name = gameName;
        dev = null;
        description = null;
        currPrice = -1;
        genres=new ArrayList<String>();
    }

    //default constructor
    public Game()
    {
        name = null;
        dev = null;
        description = null;
        currPrice = -1;
        genres=new ArrayList<String>();
    }

    public String getName()
    {
        return name;
    }

    public String getDev()
    {
        return dev;
    }

    public List<String> getGenres()
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

    private void validName(String name) throws IncorrectFormat {
        //check that name is not blank or just space(s)
        if (name.trim().equals(""))
        {
            throw new IncorrectFormat("Game name cannot be blank/empty.");
        }

    }

    private void validPrice(double price) throws IncorrectFormat{

        if (price < 0.0)
        {
            throw new IncorrectFormat("Price cannot be negative.");
        }
    }

    public boolean validGame()
    {
        boolean valid = false;
        if(name != null )
        {
            if(!name.trim().equals("") && currPrice >= 0.0) {
                valid = true;
            }
        }
        return valid;
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
            if(this.name != null)
            {
                if(this.name.equals(other.getName()))
                {
                    isSame = true;
                }
            }
            else
            {
                if(other.getName() == null)
                {
                    isSame = true;
                }
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
        return "Game: "+name+", Developer: "+dev+", Current Price: "+currPrice+"\nGenres: "+genreList+"\nDescription: "+description;
    }
}


// This class represents a game in the system.

package comp3350.grs.objects;
import java.util.ArrayList;
import java.util.List;

import comp3350.grs.business.AccessRatings;
import comp3350.grs.exceptions.IncorrectFormat;

public class Game
{
    private String name;
    private String dev; //name of company/game developer
    private List<String> genres;// a list of genre of the game
    private String description;//description of the game
    private double currPrice; //current price of game

    //detailed constructor
    public Game(String gameName, String gameDev, String desc, double price,
                List<String> gen) throws IncorrectFormat {
        //check for valid input
        checkName(gameName);
        checkPrice(price);
        name = gameName;
        dev = gameDev;
        description = desc;
        genres = gen;
        currPrice = price;
    }

    //simple constructor
    public Game(String gameName) throws IncorrectFormat {
        checkName(gameName);
        name = gameName;
        dev = null;
        description = null;
        currPrice = 0.0;
        genres=new ArrayList<String>();
    }

    //default constructor
    public Game() {
        name = null;
        dev = null;
        description = null;
        currPrice = -1;
        genres= null;
    }

    private void checkName(String name) throws IncorrectFormat{
        //check that name is not blank or just space(s) or null
        if (name.trim().equals("")) {
            throw new IncorrectFormat("Game name cannot be blank/empty.");
        }
    }

    private void checkPrice(double price) throws IncorrectFormat{
        if (price < 0.0) {
            throw new IncorrectFormat("Price cannot be negative.");
        }
    }

    public String getName() {
        return name;
    }

    public String getDev() {
        return dev;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getDescription() {
        return description;
    }


    public double getPrice() {
        return currPrice;
    }

    //important info is not null
    public boolean validGame() {
        return name!=null;
    }



    // compares another object with this Game to see if they are equal (if names are same)
    @Override
    public boolean equals(Object object)
    {
        Game other = null;
        boolean isSame = false;

        if(object != null && validGame() && object instanceof Game)
        {
            other = (Game) object;
            if((this.name).equals(other.getName()) )
            {
                isSame = true;
            }
        }
        return isSame;
    }

    public String toString()
    {
        String genreList = "";
        for(int i = 0; i < genres.size(); i++) {
            genreList += genres.get(i) + ", ";
        }
        return "Game: "+name+", Developer: "+dev+", Current Price: "+currPrice+"\nGenres: "+genreList+"\nDescription: "+description;
    }
}


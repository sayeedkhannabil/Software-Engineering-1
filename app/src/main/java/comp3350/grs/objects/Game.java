// This class represents a game in the system.

package comp3350.grs.objects;
import java.util.ArrayList;
import java.util.List;

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

            name = gameName;
            dev = gameDev;
            description = desc;
            genres = gen;
            currPrice = price;
        }
        catch(IncorrectFormat incorrectFormat) {
            System.out.println(incorrectFormat.getMessage());
        }
    }

    //simple constructor
    public Game(String gameName) {
        try {
            validName(gameName);

            name = gameName;
            dev = null;
            description = null;
            currPrice = 0.0;
            genres=new ArrayList<String>();
        }
        catch (IncorrectFormat incorrectFormat) {
            System.out.println(incorrectFormat.getMessage());
        }
    }

    //default constructor
    public Game() {
        name = null;
        dev = null;
        description = null;
        currPrice = -1;
        genres= null;
    }

    public String getName() {
        return name;
    }

    public String getDev() {
        return dev;
    }

    public List<String> getGenres() {
        List<String> genreCopy = new ArrayList<>();
        genreCopy.addAll(genres);
        return genreCopy;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return currPrice;
    }

    public boolean validGame() {
        boolean valid = false;
        if(name != null) {
            if(!name.trim().equals("") && currPrice >= 0.0) {
                valid = true;
            }
        }
        return valid;
    }

    private void validName(String name) throws IncorrectFormat, NullPointerException {
        //check that name is not blank or just space(s) or null
        if (name.trim().equals("")) {
            throw new IncorrectFormat("Game name cannot be blank/empty.");
        }
    }

    private void validPrice(double price) throws IncorrectFormat{
        if (price < 0.0) {
            throw new IncorrectFormat("Price cannot be negative.");
        }
    }

    // compares another object with this Game to see if they are equal (if names are same)
    public boolean equals(Object otherGame)
    {
        Game other = null;
        boolean isSame = false;

        if(otherGame instanceof Game)
        {
            other = (Game) otherGame;
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


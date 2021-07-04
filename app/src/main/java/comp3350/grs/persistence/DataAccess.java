package comp3350.grs.persistence;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.User;
import comp3350.grs.presentation.MainActivity;

public abstract class DataAccess {
    protected String dbName;
    protected String dbType;
    protected ArrayList<User> users;
    protected ArrayList<Game> games;
    protected ArrayList<Game> results;

    public DataAccess(String dbName){
        this.dbName=dbName;
    }

    public DataAccess(){
        this.dbName= Main.dbName;
    }

    //------------------------------------------------------
    // open
    //
    // PURPOSE:    open the database, create a bunch of objects according to
    // the json file scraped from steam
    // PARAMETERS:
    //     dbName: name of the database
    //		content: the string read from json file. when we are dealing with
    //		pure java(eg. test database), we can just pass null and let this
    //		method to read from json. but when we are running on a android
    //		simulator, the path of json is not valid anymore, we must read
    //		the file using the android way in activity class, and pass the
    //		string content as parameter
    // Returns: void
    //------------------------------------------------------
    public void open(String dbPath) {
        String content=null;
        InputStream inputStream=null;
        games=new ArrayList<Game>();


        if (MainActivity.getIsRunning()){
            inputStream=
                    this.getClass().getClassLoader().getResourceAsStream("res/raw/csvjson.json");
        }
        else {
            try {
                inputStream=new FileInputStream("src/main/res/raw/csvjson.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        try {
            content= IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray gameJsonArray = null;
        JSONArray genreJsonArray = null;
        JSONObject currGame=null;
        JSONObject currGenre=null;
        try {
            gameJsonArray = new JSONArray(content);
            String gameName,gameDev,desc;
            double price;
            List<String>genres=new ArrayList<String>();
            Game newGame;
            for (int i = 0; i < gameJsonArray.length(); i++) {
                currGame = gameJsonArray.getJSONObject(i);
                gameName=currGame.getString("title");
                gameDev=currGame.getString("developer");
                desc=currGame.getString("description");
                price=currGame.getDouble("price");
                genreJsonArray=currGame.getJSONArray("genres");
                for (int j = 0; j < genreJsonArray.length(); j++) {
                    currGenre=genreJsonArray.getJSONObject(j);
                    genres.add(currGenre.getString("genres"));
                }
                newGame=new Game(gameName, gameDev, desc, price, genres);
                games.add(newGame);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        users=new ArrayList<User>();
        User guest= null;
        try {
            guest = new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        users.add(guest);

    }


}

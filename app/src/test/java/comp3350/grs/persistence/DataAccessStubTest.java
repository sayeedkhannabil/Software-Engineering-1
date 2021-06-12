package comp3350.grs.persistence;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import comp3350.grs.objects.Game;
import comp3350.grs.persistence.DataAccessStub;

public class DataAccessStubTest {
    DataAccessStub db;

    @Before
    public void initialization(){
        db=new DataAccessStub("testDb");
    }

    @Test
    public void testOpen(){
        DataAccessStub db;
        List<Game> games;
        Game game;
        db=new DataAccessStub("testDb");
        db.open("testDb",null);
        games= db.getAllGames();
        for (int i = 0; i < games.size(); i++) {
            game=games.get(i);
            System.out.println(game);
        }
    }
}
package comp3350.grs.persistence;

import org.junit.Test;

import java.util.List;

import comp3350.grs.objects.Game;

import static org.junit.Assert.*;

public class DataAccessStubTest {

    @Test
    public void testOpen(){
        DataAccessStub dataAccessStub=new DataAccessStub("TestDb");
        dataAccessStub.open();
        List<Game> games=dataAccessStub.getAllGames();
        for (int i = 0; i < games.size(); i++) {
            System.out.println(games.get(i));
        }
    }
}
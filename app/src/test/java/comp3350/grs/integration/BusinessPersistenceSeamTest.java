package comp3350.grs.integration;
import junit.framework.TestCase;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.business.AccessGames;
import comp3350.grs.business.AccessPosts;
import comp3350.grs.objects.Game;

public class BusinessPersistenceSeamTest extends TestCase{
    private String className;
    private boolean inserted, updated, deleted;
    public BusinessPersistenceSeamTest(String arg0)
    {
        super(arg0);
    }

    public void before(){
        System.out.println("\nStarting Integration test of "+className+" to persistence");
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);
        inserted = false;
        updated = false;
        deleted = false;
    }

    public void after(){
        Services.closeDataAccess();
        System.out.println("Finished Integration test of "+className+" to persistence");
    }

    public void testAccessGames(){
        className = "AccessGames";
        AccessGames accessGames = new AccessGames();
        Game game;
        String gameName;
        before();

        //representative test cases
        game = accessGames.getSequential();
        gameName = game.getName();
        assertEquals(game, accessGames.findGame(gameName));

        inserted = accessGames.insertGame(game);
        assertFalse(inserted); //because the game already exists
        after();
    }

    public void testAccessPosts(){
        className = "AccessPosts";
        AccessPosts accessPosts = new AccessPosts();
        before();
        //representative test cases

        after();
    }

    public void testAccessRatings(){
        className = "AccessRatings";
        before();
        //representative test cases

        after();
    }

    public void testAccessReplys(){
        className = "AccessReplys";
        before();
        //representative test cases

        after();
    }

    public void testAccessRequests(){
        className = "AccessRequests";
        before();
        //representative test cases
        after();
    }

    public void testAccessReviews(){
        className = "AccessReviews";
        before();
        //representative test cases
        after();
    }

    public void testAccessUsers(){
        className = "AccessUsers";
        before();
        //representative test cases

        after();
    }

    public void testAccessVoteReplys(){
        className = "AccessVoteReplys";
        before();
        //representative test cases

        after();
    }
}

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

    public void setUp(){
        System.out.println("\nStarting Integration test");
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);
        inserted = false;
        updated = false;
        deleted = false;
    }

    public void tearDown(){
        Services.closeDataAccess();
        System.out.println("Finished Integration test of "+className+" to persistence");
    }

    public void testAccessGames(){
        className = "AccessGames";
        AccessGames accessGames = new AccessGames();
        Game game;
        String gameName;


        //representative test cases
        game = accessGames.getSequential();
        gameName = game.getName();
        assertEquals(game, accessGames.findGame(gameName));

        inserted = accessGames.insertGame(game);
        assertFalse(inserted); //because the game already exists

    }

    public void testAccessPosts(){
        className = "AccessPosts";
        AccessPosts accessPosts = new AccessPosts();

        //representative test cases

    }

    public void testAccessRatings(){
        className = "AccessRatings";

        //representative test cases

    }

    public void testAccessReplys(){
        className = "AccessReplys";

        //representative test cases

    }

    public void testAccessRequests(){
        className = "AccessRequests";

        //representative test cases

    }

    public void testAccessReviews(){
        className = "AccessReviews";

        //representative test cases

    }

    public void testAccessUsers(){
        className = "AccessUsers";
        //representative test cases

    }

    public void testAccessVoteReplys(){
        className = "AccessVoteReplys";
        //representative test cases

    }
}

package comp3350.grs.acceptance;

import androidx.test.espresso.Espresso;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.R;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.business.AccessGames;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//user want to see the detailed information of a game
@RunWith(AndroidJUnit4.class)
@MediumTest
public class GameInfoTest {
    private static DataAccessI dataAccessI;
    private static AccessGames accessGames;
    private Game game1, game2, game3, game4;
    List<String> genres;

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() {
        dataAccessI=new DataAccessObject(Main.testDbName);
        Services.closeDataAccess();
        Services.createDataAccess(dataAccessI);
        dataAccessI.clearAllData();
        accessGames = new AccessGames() ;
        game1 = null;
        game2 = null;
        game3 = null;
        game4 = null;
        genres = null;
    }

    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }

    //String gameName, String gameDev, String desc, double price, List<String> gen
    @Test
    public void testReadPrice(){

        try {
            genres=new ArrayList();
            genres.add("Early Access");
            genres.add("Survival");
            genres.add("Voxel");
            genres.add("Zombies");
            genres.add("Post-apocalyptic");
            genres.add("Open World");
            genres.add("Open World Survival Craft");
            genres.add("Simulation");
            genres.add("Exploration");
            genres.add("Base Building");
            genres.add("Strategy");
            genres.add("Multiplayer");
            genres.add("Procedural Generation");
            genres.add("Tower Defense");
            genres.add("Character Customization");
            genres.add("Online Co-Op");
            genres.add("Sandbox");
            genres.add("Building");
            genres.add("FPS");
            genres.add("Action");

            game1 = new Game(
                    "7 Days to Die",
                    "unicomp",
                    "7 Days to Die is an open-world game that is a unique combination of first person shooter, " +
                            "survival horror, tower defense, and role-playing games. Play the definitive zombie survival" +
                            " sandbox RPG that came first. Navezgane awaits!",
                    23.0,
                    genres
            );
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game1);


        try {
            genres=new ArrayList();
            genres.add("Adventure");
            genres.add("Massively Multiplayer");
            genres.add("Action");
            genres.add("RPG");
            genres.add("Survival");
            genres.add("Indie");
            genres.add("Open World");
            genres.add("Dinosaurs");
            genres.add("Multiplayer");
            genres.add("Violent");
            genres.add("Base Building");
            genres.add("Crafting");
            genres.add("Singleplayer");
            genres.add("Sandbox");
            genres.add("Building");
            genres.add("Sci-fi");
            genres.add("Open World Survival Craft");
            genres.add("Dragons");
            genres.add("Co-op");

            game3 = new Game(
                    "ARK: Genesis Season Pass",
                    "Studio Wildcard",
                    "unknown",
                    87.0,
                    genres
            );
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game3);



        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withId(R.id.game_page_price)).check(matches(withText("$23.0")));

        Espresso.pressBack();
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("ARK: Genesis Season Pass")).check(matches(isDisplayed()));;
        onView(withText("ARK: Genesis Season Pass")).perform(click());
        onView(withText("ARK: Genesis Season Pass")).check(matches(isDisplayed()));
        onView(withId(R.id.game_page_price)).check(matches(withText("$87.0")));

        Espresso.pressBack();
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        accessGames.clear();

    }

    @Test
    public void testReadDescription(){

        try {
            genres=new ArrayList();
            genres.add("Early Access");
            genres.add("Survival");
            genres.add("Voxel");
            genres.add("Zombies");
            genres.add("Post-apocalyptic");
            genres.add("Open World");
            genres.add("Open World Survival Craft");
            genres.add("Simulation");
            genres.add("Exploration");
            genres.add("Base Building");
            genres.add("Strategy");
            genres.add("Multiplayer");
            genres.add("Procedural Generation");
            genres.add("Tower Defense");
            genres.add("Character Customization");
            genres.add("Online Co-Op");
            genres.add("Sandbox");
            genres.add("Building");
            genres.add("FPS");
            genres.add("Action");

            game1 = new Game(
                    "7 Days to Die",
                    "unicomp",
                    "7 Days to Die is an open-world game that is a unique combination of first person shooter, " +
                            "survival horror, tower defense, and role-playing games. Play the definitive zombie survival" +
                            " sandbox RPG that came first. Navezgane awaits!",
                    23.0,
                    genres
            );
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game1);

        try {
            genres=new ArrayList();
            genres.add("Action");
            genres.add("Multiplayer");
            genres.add("Military");
            genres.add("Simulation");
            genres.add("Shooter");
            genres.add("First-Person");
            genres.add("Realistic");
            genres.add("Open World");
            genres.add("Tactical");
            genres.add("FPS");
            genres.add("War");
            genres.add("Co-op");
            genres.add("Strategy");
            genres.add("Sandbox");
            genres.add("Moddable");
            genres.add("Combat");
            genres.add("Online Co-Op");
            genres.add("Singleplayer");
            genres.add("Team-Based");
            genres.add("Third-Person Shooter");
            game4 = new Game(
                    "Arma 3",
                    "Bohemia Interactive",
                    "Experience true combat gameplay in a massive military sandbox. Deploying a " +
                            "wide variety of single- and multiplayer content, over 20 vehicles and" +
                            " 40 weapons, and limitless opportunities for content creation, this is" +
                            " the PC�s premier military game. Authentic, diverse, open - Arma 3 " +
                            "sends you to war.",
                    29.0,
                    genres
            );
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game4);

        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withId(R.id.game_page_description_text)).check(matches(withText("7 Days to Die is an open-world game that is a unique combination of first person shooter," +
                " survival horror, tower defense, and role-playing games. Play the definitive zombie survival sandbox RPG that came first. Navezgane awaits!")));

        Espresso.pressBack();
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("Arma 3")).check(matches(isDisplayed()));;
        onView(withText("Arma 3")).perform(click());
        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withId(R.id.game_page_description_text)).check(matches(withText("Experience true combat gameplay in a massive military sandbox. Deploying a wide variety of single- " +
                "and multiplayer content, over 20 vehicles and 40 weapons, and limitless opportunities for content creation, this is the PC�s premier military game. Authentic, diverse, " +
                "open - Arma 3 sends you to war.")));

        Espresso.pressBack();
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        accessGames.clear();

    }

    @Test
    public void testReadGenre(){
        try {
            genres=new ArrayList();
            genres.add("Open World Survival Craft");
            genres.add("Survival");
            genres.add("Open World");
            genres.add("Multiplayer");
            genres.add("Dinosaurs");
            genres.add("Crafting");
            genres.add("Building");
            genres.add("Adventure");
            genres.add("Base Building");
            genres.add("Co-op");
            genres.add("Action");
            genres.add("First-Person");
            genres.add("Sandbox");
            genres.add("Massively Multiplayer");
            genres.add("Singleplayer");
            genres.add("Early Access");
            genres.add("RPG");
            genres.add("Dragons");
            genres.add("Sci-fi");
            genres.add("Indie");
            game2 = new Game(
                    "ARK: Survival Evolved",
                    "Studio Wildcard",
                    "Stranded on the shores of a mysterious island, you must learn to survive. Use" +
                            " your cunning to kill or tame the primeval creatures roaming the land" +
                            ", and encounter other players to survive, dominate... and escape!",
                    27.0,
                    genres
            );
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game2);

        try {
            genres=new ArrayList();
            genres.add("Action");
            genres.add("Multiplayer");
            genres.add("Military");
            genres.add("Simulation");
            genres.add("Shooter");
            genres.add("First-Person");
            genres.add("Realistic");
            genres.add("Open World");
            genres.add("Tactical");
            genres.add("FPS");
            genres.add("War");
            genres.add("Co-op");
            genres.add("Strategy");
            genres.add("Sandbox");
            genres.add("Moddable");
            genres.add("Combat");
            genres.add("Online Co-Op");
            genres.add("Singleplayer");
            genres.add("Team-Based");
            genres.add("Third-Person Shooter");

            game4 = new Game(
                    "Arma 3",
                    "Bohemia Interactive",
                    "Experience true combat gameplay in a massive military sandbox. Deploying a " +
                            "wide variety of single- and multiplayer content, over 20 vehicles and" +
                            " 40 weapons, and limitless opportunities for content creation, this is" +
                            " the PC�s premier military game. Authentic, diverse, open - Arma 3 " +
                            "sends you to war.",
                    29.0,
                    genres
            );
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game4);


        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("ARK: Survival Evolved")).check(matches(isDisplayed()));
        onView(withText("ARK: Survival Evolved")).perform(click());
        onView(withText("ARK: Survival Evolved")).check(matches(isDisplayed()));
        onView(withId(R.id.genre_wrapper)).check(matches(isDisplayed()));
        onView(withId(R.id.scroll_genre_wrapper)).perform(swipeLeft());
        onView(withText("Sandbox")).perform(scrollTo(),click());

        Espresso.pressBack();
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withText("Arma 3")).perform(click());
        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withId(R.id.genre_wrapper)).check(matches(isDisplayed()));
        onView(withId(R.id.scroll_genre_wrapper)).perform(swipeLeft());
        onView(withText("Action")).perform(scrollTo(),click());

        accessGames.clear();

    }
}

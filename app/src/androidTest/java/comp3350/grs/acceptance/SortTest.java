package comp3350.grs.acceptance;

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
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessVoteReplys;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to sort games
public class SortTest {
    private static DataAccessI dataAccessI;
    private AccessGames accessGames;
    List<String> genre;
    private Game game1, game2, game3, game4;
    private Game game5, game6, game7, game8;

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before(){
        dataAccessI=new DataAccessObject(Main.testDbName);
        Services.closeDataAccess();
        Services.createDataAccess(dataAccessI);
        dataAccessI.clearAllData();
        accessGames = new AccessGames();
        genre = null;
        game1 = null;
        game2 = null;
        game3 = null;
        game4 = null;
        game5 = null;
        game6 = null;
        game7 = null;
        game8 = null;
    }

    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }

    @Test
    public void testSortByName() {
        try{
            game1 = new Game("hh");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try{
            game2 = new Game("gg");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try{
            game3 = new Game("ff");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try{
            game4 = new Game("ee");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try{
            game5 = new Game("dd");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try{
            game6 = new Game("cc");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try{
            game7 = new Game("bb");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try{
            game8 = new Game("aa");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        accessGames.insertGame(game8);
        accessGames.insertGame(game6);
        accessGames.insertGame(game1);
        accessGames.insertGame(game7);
        accessGames.insertGame(game5);
        accessGames.insertGame(game3);
        accessGames.insertGame(game4);
        accessGames.insertGame(game2);

        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());

        onView(withId(R.id.sort_icon)).perform(click());
        onView(withId(R.id.name_sort)).perform(click());
        //onView(withId(R.id.name_sort)).perform(click());

        onView(withText("hh")).check(matches(isDisplayed()));
        onView(withText("gg")).check(matches(isDisplayed()));
        onView(withText("ff")).check(matches(isDisplayed()));

        onView(withId(R.id.sort_icon)).perform(click());
        onView(withId(R.id.sort_icon)).perform(click());
        onView(withId(R.id.name_sort)).perform(click());
        onView(withId(R.id.name_sort)).perform(click());

        onView(withText("aa")).check(matches(isDisplayed()));
        onView(withText("bb")).check(matches(isDisplayed()));
        onView(withText("cc")).check(matches(isDisplayed()));

        accessGames.clear();

    }

    @Test
    public void testSortByPrice() {
        try{
            genre = new ArrayList<>();
            genre.add("Action");
            game1 = new Game("aa", "dev", "unknown", 2.5, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        try{
            genre = new ArrayList<>();
            genre.add("Fighting");
            game2 = new Game("bb", "dev", "unknown", 4, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try{
            genre = new ArrayList<>();
            genre.add("Adventure");
            game3 = new Game("cc", "dev", "unknown", 9, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try{
            genre = new ArrayList<>();
            genre.add("Action");
            game4 = new Game("dd", "dev", "unknown", 12.2, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try{
            genre = new ArrayList<>();
            genre.add("Racing");
            game5 = new Game("ee", "dev", "unknown", 17, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try{
            genre = new ArrayList<>();
            genre.add("Action");
            game6 = new Game("ff", "dev", "unknown", 21, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try{
            genre = new ArrayList<>();
            genre.add("Fantasy");
            game7 = new Game("gg", "dev", "unknown", 21.1, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try{
            genre = new ArrayList<>();
            genre.add("Action");
            game8 = new Game("hh", "dev", "unknown", 22.5, genre);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        accessGames.insertGame(game8);
        accessGames.insertGame(game6);
        accessGames.insertGame(game1);
        accessGames.insertGame(game7);
        accessGames.insertGame(game5);
        accessGames.insertGame(game3);
        accessGames.insertGame(game4);
        accessGames.insertGame(game2);

        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());

        onView(withId(R.id.sort_icon)).perform(click());
        onView(withId(R.id.price_sort)).perform(click());

        onView(withText("hh")).check(matches(isDisplayed()));
        onView(withText("gg")).check(matches(isDisplayed()));
        onView(withText("ff")).check(matches(isDisplayed()));

        onView(withId(R.id.sort_icon)).perform(click());
        onView(withId(R.id.sort_icon)).perform(click());
        onView(withId(R.id.price_sort)).perform(click());
        onView(withId(R.id.price_sort)).perform(click());

        onView(withText("aa")).check(matches(isDisplayed()));
        onView(withText("bb")).check(matches(isDisplayed()));
        onView(withText("cc")).check(matches(isDisplayed()));

    }



    @Test
    public void testSortByRating() {

    }

    @Test
    public void testSortByReview() {

    }

}
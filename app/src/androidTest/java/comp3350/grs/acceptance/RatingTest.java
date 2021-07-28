package comp3350.grs.acceptance;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.TypeTextAction;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.grs.R;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.business.AccessGames;
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessVoteReplys;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to rate a game
public class RatingTest {

    private static DataAccessI dataAccessI;
    private static AccessUsers accessUsers;
    private static AccessGames accessGames;
    private static AccessRatings accessRatings;
    private Game game1, game2;
    private User user1, user2;

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before(){
        dataAccessI=new DataAccessObject(Main.testDbName);
        Services.closeDataAccess();
        Services.createDataAccess(dataAccessI);
        dataAccessI.clearAllData();
        accessGames = new AccessGames() ;
        accessUsers = new AccessUsers();
        accessRatings = new AccessRatings();
        game1 = null;
        game2 = null;
        user1 = null;
        user2 = null;
    }


    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }



    @Test
    public void testRating() {
        try {
            game1 = new Game("7 Days to Die");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        try {
            game2 = new Game("Arma 3");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }

        accessGames.insertGame(game1);
        accessGames.insertGame(game2);


        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 4.0));
        onView(withId(R.id.rating_value_text)).check(matches(withText("4.0")));

        Espresso.pressBack();

        onView(withText("Game Gallery")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 2.0));
        onView(withId(R.id.rating_value_text)).check(matches(withText("2.0")));

        Espresso.pressBack();

        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withText("Arma 3")).perform(click());
        onView(withText("Arma 3")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(click());
        onView(withId(R.id.rating_value_text)).check(matches(withText("3.0")));

        Espresso.pressBack();

        onView(withText("Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withText("Arma 3")).perform(click());
        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));

        accessGames.clear();

    }

    @Test
    public void testMultipleUserRating(){

        try {
            game1 = new Game("7 Days to Die");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game1);

        onView(withText("SIGNUP")).check(matches(isDisplayed()));
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("123"));
        onView(withHint("password")).perform(typeText("123"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 4.0));
        onView(withId(R.id.rating_value_text)).check(matches(withText("4.0")));

        Espresso.pressBack();

        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        Espresso.pressBack();

        Espresso.pressBack();


        onView(withText("SIGNUP")).check(matches(isDisplayed()));
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("456"));
        onView(withHint("password")).perform(typeText("123"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 2.0));
        onView(withId(R.id.rating_value_text)).check(matches(withText("3.0")));

        accessGames.clear();

    }

    @Test
    public void testEdge(){
        try {
            game1 = new Game("7 Days to Die");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game1);

        onView(withText("SIGNUP")).check(matches(isDisplayed()));
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("123"));
        onView(withHint("password")).perform(typeText("123"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 0.5));
        onView(withId(R.id.rating_value_text)).check(matches(withText("0.5")));

        Espresso.pressBack();

        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        Espresso.pressBack();

        Espresso.pressBack();


        onView(withText("SIGNUP")).check(matches(isDisplayed()));
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("456"));
        onView(withHint("password")).perform(typeText("123"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 4.5));
        onView(withId(R.id.rating_value_text)).check(matches(withText("2.5")));

        accessGames.clear();

    }
}

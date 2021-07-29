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

import comp3350.grs.R;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.business.AccessGames;
import comp3350.grs.business.AccessRequests;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to request a game that is not in the game gallery
public class RequestTest {
    private static DataAccessI dataAccessI;
    private AccessRequests accessRequests;
    private AccessUsers accessUsers;
    private AccessGames accessGames;


    @Before
    public void before(){
        dataAccessI=new DataAccessObject(Main.testDbName);
        Services.closeDataAccess();
        Services.createDataAccess(dataAccessI);
        dataAccessI.clearAllData();
        accessRequests = new AccessRequests();
        accessUsers = new AccessUsers();
        accessGames = new AccessGames();
    }

    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSubmitRequest() {
        //request as a guest
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(typeText("New Game Not In DB"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withId(R.id.request_game_text)).check(matches(withText("New Game Not In DB")));
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());

        //request as a registered user
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("newUser"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(typeText("New Game Not In DB"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withId(R.id.request_game_text)).check(matches(withText("New Game Not In DB")));
        //request another game
        onView(withHint("Game Name")).perform(replaceText("newGame2"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(allOf(withText("New Game Not In DB"),
                withId(R.id.request_game_text))).check(matches(isDisplayed()));
        onView(allOf(withText("newGame2"),
                withId(R.id.request_game_text))).check(matches(isDisplayed()));
    }

    @Test
    public void testMostRequested() {
        //request as a guest
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(replaceText("MostRequested"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withHint("Game Name")).perform(replaceText("MostRequested2"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withHint("Game Name")).perform(replaceText("MostRequested3"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(allOf(withId(R.id.most_requested_num),withText("1"),
                hasSibling(withText("MostRequested"))) ).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.most_requested_num),withText("1"),
                hasSibling(withText("MostRequested2"))) ).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.most_requested_num),withText("1"),
                hasSibling(withText("MostRequested3"))) ).check(matches(isDisplayed()));
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());

        //request as a registered user
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(replaceText("NewUser2"));
        onView(withHint("password")).perform(replaceText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(replaceText("MostRequested"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withId(R.id.request_game_text)).check(matches(withText("MostRequested")));
        onView(withHint("Game Name")).perform(replaceText("MostRequested2"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(allOf(withId(R.id.request_game_text),withText(
                "MostRequested2")) ).check(matches(isDisplayed()));
        onView(withHint("Game Name")).perform(replaceText("MostRequested3"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(allOf(withId(R.id.request_game_text),withText(
                "MostRequested2")) ).check(matches(isDisplayed()));
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(replaceText("LessRequested"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());

        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(replaceText("NewUser3"));
        onView(withHint("password")).perform(replaceText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(replaceText("MostRequested"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(allOf(withId(R.id.most_requested_num),hasSibling(withText("MostRequested"))) ).check(matches(withText(
                "3")));
        onView(allOf(withId(R.id.most_requested_num),hasSibling(withText(
                "MostRequested2"))) ).check(matches(withText(
                "2")));
        onView(allOf(withId(R.id.most_requested_num),hasSibling(withText(
                "MostRequested3"))) ).check(matches(withText(
                "2")));
        onView(allOf(withId(R.id.most_requested_game),withText("LessRequested"))).check(doesNotExist());
    }

    @Test
    public void testInvalidRequest() {
        User user = null;
        Game game = null;
        try{
            user = new RegisteredUser("newRegisteredUser1", "password1");
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user);
        try{
            game = new Game("ExistingGame","dev", "desc", 20.00, new ArrayList<>());
        }catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        accessGames.insertGame(game);

        //try to request game that already exists
        onView(withText("LOGIN")).perform(click());
        onView(withHint("userID")).perform(typeText("newRegisteredUser1"));
        onView(withHint("password")).perform(typeText("password1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button3)).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(typeText(game.getName()));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withText("The game already exists in the gallery. Request can't be made.")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

    }

}
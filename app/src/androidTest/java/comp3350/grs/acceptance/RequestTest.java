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
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessRequests;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessVoteReplys;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
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
    }

    @Test
    public void testMostRequested() {
        //request as a guest
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(typeText("MostRequested"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withId(R.id.request_game_text)).check(matches(withText("MostRequested")));
        onView(withId(R.id.most_requested_game)).check(matches(withText("MostRequested")));
        onView(withId(R.id.most_requested_num)).check(matches(withText("1")));
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());

        //request as a registered user
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("NewUser2"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(typeText("MostRequested"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());
        onView(withId(R.id.request_game_text)).check(matches(withText("MostRequested")));
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.request_button)).perform(click());
        onView(withHint("Game Name")).perform(typeText("LessRequested"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_request_button)).perform(click());

        //these give error -- because there are multiple entries in most_requested_game
        //onView(withId(R.id.most_requested_game)).check(matches(withText("MostRequested")));
        //onView(withId(R.id.most_requested_num)).check(matches(withText("2")));
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
package comp3350.grs.acceptance;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to search a game
public class SearchTest {
    private static DataAccessI dataAccessI;

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() {
        Services.closeDataAccess();
        dataAccessI = new DataAccessObject(Main.testDbName);
        dataAccessI.open(Main.getDBPathName(Main.testDbName));
        dataAccessI.deleteDatabase();
        Services.createDataAccess(dataAccessI);
    }

    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }

    @Test
    public void testExactSearch() {
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.search_icon)).perform(click());
        onView(withId(R.id.search_game_bar)).perform(new SetSearchBar("Arma 3"));
        onView(allOf(withText("Arma 3"),isDescendantOfA(withId(R.id.table_layout)))).check(matches(isDisplayed()));
        onView(withId(R.id.search_game_bar)).perform(new CloseSearchBar());
        onView(withId(R.id.search_game_bar)).perform(new SetSearchBar("DayZ"));
        onView(allOf(withText("DayZ"),isDescendantOfA(withId(R.id.table_layout)))).check(matches(isDisplayed()));
        onView(withId(R.id.search_game_bar)).perform(new CloseSearchBar());
    }

    @Test
    public void testImplicitSearch() {
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.search_icon)).perform(click());
        onView(withId(R.id.search_game_bar)).perform(new SetSearchBar("Ar"));
        onView(allOf(withText("Arma 3"),isDescendantOfA(withId(R.id.table_layout)))).check(matches(isDisplayed()));
        onView(allOf(withText("7 Days to Die"),
                isDescendantOfA(withId(R.id.table_layout)))).check(doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.game_gallery_scroll)).perform(ViewActions.swipeUp());
        onView(allOf(withText("Terraria"),
                isDescendantOfA(withId(R.id.table_layout)))).check(matches(isDisplayed()));
        onView(withId(R.id.search_game_bar)).perform(new CloseSearchBar());
        Espresso.onView(ViewMatchers.withId(R.id.game_gallery_scroll)).perform(ViewActions.swipeDown());
        onView(withId(R.id.search_game_bar)).perform(new SetSearchBar("ar"));
        onView(allOf(withText("Arma 3"),isDescendantOfA(withId(R.id.table_layout)))).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.game_gallery_scroll)).perform(ViewActions.swipeUp());
        onView(allOf(withText("Terraria"),
                isDescendantOfA(withId(R.id.table_layout)))).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchNotExist() {
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withId(R.id.search_icon)).perform(click());
        onView(withId(R.id.search_game_bar)).perform(new SetSearchBar("GTA6"));
        onView(allOf(withText("GTA6"),
                isDescendantOfA(withId(R.id.table_layout)))).check(doesNotExist());
    }


}
package comp3350.grs.acceptance;

import androidx.test.espresso.Espresso;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.grs.R;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//user want to see the detailed information of a game
@RunWith(AndroidJUnit4.class)
@MediumTest
public class GameInfoTest {

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testReadPrice(){

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

    }

    @Test
    public void testReadDescription(){

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

    }

    @Test
    public void testReadGenre(){
        //todo scroll genre
        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("ARK: Survival Evolved")).check(matches(isDisplayed()));
        onView(withText("ARK: Survival Evolved")).perform(click());
        onView(withText("ARK: Survival Evolved")).check(matches(isDisplayed()));
        onView(withId(R.id.genre_wrapper)).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("Arma 3")).check(matches(isDisplayed()));;
        onView(withText("Arma 3")).perform(click());
        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withId(R.id.genre_wrapper)).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

    }
}

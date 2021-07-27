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


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to rate a game
public class RatingTest {

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRating() {

        onView(withText("CONTINUE AS GUEST")).check(matches(isDisplayed()));
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Game Gallery")).check(matches(isDisplayed()));

        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(click());
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));

        Espresso.pressBack();

        onView(withText("Game Gallery")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withText("7 Days to Die")).perform(click());
        onView(withText("7 Days to Die")).check(matches(isDisplayed()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));

        Espresso.pressBack();

        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withText("Arma 3")).perform(click());
        onView(withText("Arma 3")).check(matches(isDisplayed()));

        onView(withId(R.id.ratingBar)).perform(click());
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));

        Espresso.pressBack();

        onView(withText("Game Gallery")).check(matches(isDisplayed()));
        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withText("Arma 3")).perform(click());
        onView(withText("Arma 3")).check(matches(isDisplayed()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));

    }

    @Test
    public void testMultipleUserRating(){

    }

    @Test
    public void testEdge(){

    }
}

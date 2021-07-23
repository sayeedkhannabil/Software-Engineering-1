package comp3350.grs.acceptance;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSubmitRequest() {

    }

    @Test
    public void testMostRequested() {

    }

    @Test
    public void testInvalidRequest() {

    }

}
package comp3350.grs.acceptance;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.grs.R;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.presentation.GameGallery;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to see their account
public class UserAccountTest {
    DataAccessI dataAccessI;

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void Before(){
        dataAccessI= Services.getDataAccess("test");
        dataAccessI.deleteDatabase();
        dataAccessI.open("database\test");
    }

    @After
    public void After(){
        dataAccessI.close();
    }


    @Test
    public void testGuest() {
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withId(R.id.user_button)).perform(click());
        onView(withId(R.id.user_page_user_info)).check(matches(withText(containsString("userID:Guest"))));
    }


}
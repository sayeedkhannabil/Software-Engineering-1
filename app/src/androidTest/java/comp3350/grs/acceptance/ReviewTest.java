package comp3350.grs.acceptance;

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
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessReviews;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessVoteReplys;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to write a review to some game
public class ReviewTest {
    private static DataAccessI dataAccessI;
    private AccessReviews accessReviews;
    private AccessGames accessGames;
    private AccessUsers accessUsers;

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);
    @Before
    public void before(){
        dataAccessI=new DataAccessObject(Main.testDbName);
        Services.closeDataAccess();
        Services.createDataAccess(dataAccessI);
        dataAccessI.clearAllData();
        accessReviews=new AccessReviews();
        accessGames=new AccessGames();
        accessUsers=new AccessUsers();
    }


    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }


    @Test
    public void testWriteReview() {
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Arma 3")).perform(click());
        onView(withId(R.id.game_page_review_button));
        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 5.0));
        onView(withId(R.id.rating_value_text)).check(matches(withText("5.0")));
        onView(withText("WRITE REVIEW")).perform(click());
        onView(withId(R.id.write_review_edittext)).perform(typeText("good game"));
        onView(withText("SUBMIT REVIEW")).perform(click());
    }


    //@Test
    //public void testWriteInvalidReview() {

    //}


}

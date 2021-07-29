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

import comp3350.grs.R;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to write a review to some game
public class ReviewTest {
    private static DataAccessI dataAccessI;


    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);
    @Before
    public void before(){
        dataAccessI=new DataAccessObject(Main.testDbName);
        Services.closeDataAccess();
        dataAccessI.open(Main.getDBPathName(Main.testDbName));
        dataAccessI.deleteDatabase();
        Services.createDataAccess(dataAccessI);

    }


    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }


    @Test
    public void testWriteReview() {

        //a user continue as a guest and write a review for a game
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Arma 3")).perform(click());
        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 4.5));
        onView(withId(R.id.rating_value_text)).check(matches(withText("4.5")));
        onView(withText("WRITE REVIEW")).perform(click());
        onView(withId(R.id.write_review_edittext)).perform(typeText("guest thinks it's a good game"));
        onView(withText("SUBMIT REVIEW")).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());

        //a user signup and write a review for a game
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("user10"));
        onView(withHint("password")).perform(typeText("password10"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("7 Days to Die")).perform(click());
        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 5.0));
        onView(withId(R.id.rating_value_text)).check(matches(withText("5.0")));
        onView(withText("WRITE REVIEW")).perform(click());
        onView(withId(R.id.write_review_edittext)).perform(typeText("user10 thinks it's a good game"));
        onView(withText("SUBMIT REVIEW")).perform(click());

    }


    @Test
    public void testWriteInvalidReview() {

        //a user continue as a guest and write a invalid review for a game
        //invalid standard is: didn't rate before review, or length of review content is <1 or >140
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Game Gallery")).perform(click());
        onView(withText("Arma 3")).perform(click());
        onView(withText("WRITE REVIEW")).perform(click());
        onView(withText("please rate the game before writing review")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.ratingBar)).perform(new SetRating((float) 4.0));
        onView(withId(R.id.rating_value_text)).check(matches(withText("4.0")));
        onView(withText("WRITE REVIEW")).perform(click());
        onView(withId(R.id.write_review_edittext)).perform(typeText(""));
        onView(withText("SUBMIT REVIEW")).perform(click());
        onView(withText("letters of review content should be between 1 and 140")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        String content="";
        for(int i=0;i<16;i++){
            content+="over size!";
        }
        onView(withId(R.id.write_review_edittext)).perform(replaceText(content));
        onView(withText("SUBMIT REVIEW")).perform(click());
        onView(withText("letters of review content should be between 1 and 140")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

    }


}

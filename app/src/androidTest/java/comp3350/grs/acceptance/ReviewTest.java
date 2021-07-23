package comp3350.grs.acceptance;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.grs.presentation.MainActivity;


@RunWith(AndroidJUnit4.class)
@MediumTest
//user want to write a review to some game
public class ReviewTest {

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testWriteReview() {

    }


    @Test
    public void testWriteInvalidReview() {

    }


}
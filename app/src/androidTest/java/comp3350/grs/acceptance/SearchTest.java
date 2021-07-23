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
//user want to search a game
public class SearchTest {

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testExactSearch() {

    }

    @Test
    public void testImplicitSearch() {

    }

    @Test
    public void testSearchNotExist() {

    }


}
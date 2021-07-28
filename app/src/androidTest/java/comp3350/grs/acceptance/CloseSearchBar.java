package comp3350.grs.acceptance;

import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.allOf;

//used to help testing the search bar
//close the search bar
public final class CloseSearchBar implements ViewAction {

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
    }

    @Override
    public String getDescription() {
        return "close search view";
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((SearchView) view).onActionViewCollapsed();
    }
}


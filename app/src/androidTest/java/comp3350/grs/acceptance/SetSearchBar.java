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
public final class SetSearchBar implements ViewAction {
    private String text;//the rating value you want to set

    public SetSearchBar(String text){
        this.text=text;
    }

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
    }

    @Override
    public String getDescription() {
        return "Change view text";
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((SearchView) view).setQuery(this.text,false);
    }
}


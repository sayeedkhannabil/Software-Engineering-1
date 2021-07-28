package comp3350.grs.acceptance;

import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;
//used to help testing the rating bar
public final class SetRating implements ViewAction {
    private float ratingValue;//the rating value you want to set

    public SetRating(float ratingValue){
        this.ratingValue=ratingValue;
    }

    @Override
    public Matcher<View> getConstraints() {
        Matcher <View> isRatingBarConstraint = ViewMatchers.isAssignableFrom(RatingBar.class);
        return isRatingBarConstraint;
    }

    @Override
    public String getDescription() {
        return "Custom view action to set rating.";
    }

    @Override
    public void perform(UiController uiController, View view) {
        GeneralClickAction viewAction = new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    //calculate the coordinations of the correct position to
                    // click
                    public float[] calculateCoordinates(View view) {
                        int[] locationOnScreen = new int[2];
                        view.getLocationOnScreen(locationOnScreen);
                        int screenX = locationOnScreen[0];
                        int screenY = locationOnScreen[1];
                        int numStars = ((RatingBar) view).getNumStars();
                        float widthPerStar = 1f * view.getWidth() / numStars;
                        float percent = ratingValue / numStars;
                        float x = screenX + view.getWidth() * percent;
                        float y = screenY + view.getHeight() * 0.5f;
                        return new float[]{x - widthPerStar * 0.5f, y};
                    }
                },
                Press.FINGER,
                InputDevice.SOURCE_UNKNOWN,
                MotionEvent.BUTTON_PRIMARY
        );
        viewAction.perform(uiController, view);
    }
}


package comp3350.grs.presentation;
// CLASS: Game_page...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// game detail page
//-----------------------------------------
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessGames;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Review;

public class Game_page extends AppCompatActivity {
    private AccessGames accessGames;
    private Game game;//the game we will show the details
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;//review edit text
    private boolean isWritingReview;//indicate if the user pressed the "write
    // review" button
    private LinearLayout review_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        isWritingReview=false;
        Bundle extras = getIntent().getExtras();
        String game_name = null;
        if (extras != null) {
            game_name = extras.getString("game");//get the game name
        }
        accessGames = new AccessGames();
        game = accessGames.findGame(game_name);
        TextView game_text = (TextView) findViewById(R.id.textView5);
        TextView dev_text = (TextView) findViewById(R.id.textView6);
        TextView des_text = (TextView) findViewById(R.id.textView7);
        TextView price_text = (TextView) findViewById(R.id.game_page_price);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        review_layout =
                (LinearLayout) findViewById(R.id.review_layout);
        Button write_review_button=(Button) findViewById(R.id.button5);


        game_text.setText(game.getName());
        dev_text.setText("dev: " + game.getDev());
        des_text.setText(game.getDescription());
        price_text.setText("$" + game.getPrice());
        ratingBar.setRating((float) game.getRating());
        showReviews();

        //when user clicked on the rating bar,update the rating
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                game.addRating((int)rating);
                accessGames.updateGame(game);
                ratingBar.setRating((float) game.getRating());
            }
        });
        LinearLayout scroll_wrapper=
                (LinearLayout) findViewById(R.id.scroll_wrapper);
        write_review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the user just clicked "write review" button
                if(!isWritingReview){
                    isWritingReview=true;
                    write_review_button.setText("submit review");//change the
                    // text of the button
                    textInputLayout=new TextInputLayout(Game_page.this);
                    textInputLayout.setBackground(getDrawable(R.drawable.rounded_rectangle));
                    //show a input box used to write review
                    textInputEditText=
                            new TextInputEditText(Game_page.this);
                    textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                    textInputLayout.addView(textInputEditText);
                    review_layout.addView(textInputLayout);
                    //extend the review part to the whole screen
                    scroll_wrapper.getLayoutParams().height=
                            LinearLayout.LayoutParams.MATCH_PARENT;
                }
                else{
                    isWritingReview=false;
                    write_review_button.setText("write review");
                    String review=textInputEditText.getText().toString();
                    review_layout.removeView(textInputLayout);
                    review_layout.removeView(textInputEditText);
                    game.addReview(5,review);
                    showReviews();//update the review
                    scroll_wrapper.getLayoutParams().height=900;//restore the
                    // writing review part to normal
                }

            }
        });

    }

    //remove all reviews generated before, and regenerate them according to
    // the updated game object, to show the newest review
    private void showReviews(){
        //remove reviews, except the "review" text itself
        while (review_layout.getChildCount() > 1){
            review_layout.removeView(review_layout.getChildAt(review_layout.getChildCount() - 1));
        }

        List<Review> reviews = game.getReviews();
        //add all reviews one by one
        for (int i = 0; i < reviews.size(); i++) {
            TextView newReview = new TextView(this);
            newReview.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            newReview.setText(reviews.get(i).toString());
            review_layout.addView(newReview);
        }
    }
}
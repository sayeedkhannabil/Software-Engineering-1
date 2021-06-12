package comp3350.grs.presentation;

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
    private Game game;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private boolean isWritingReview;
    private LinearLayout review_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        isWritingReview=false;
        Bundle extras = getIntent().getExtras();
        String game_name = null;
        if (extras != null) {
            game_name = extras.getString("game");
        }
        accessGames = new AccessGames();
        game = accessGames.findGame(game_name);
        TextView game_text = (TextView) findViewById(R.id.textView5);
        TextView dev_text = (TextView) findViewById(R.id.textView6);
        TextView des_text = (TextView) findViewById(R.id.textView7);
        TextView price_text = (TextView) findViewById(R.id.textView9);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        review_layout =
                (LinearLayout) findViewById(R.id.review_layout);
        Button write_review_button=(Button) findViewById(R.id.button5);


        game_text.setText(game.getName());
        dev_text.setText(game.getDev());
        des_text.setText(game.getDescription());
        price_text.setText("$" + game.getPrice());
        ratingBar.setRating((float) game.getRating());
        showReviews();

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
                if(!isWritingReview){
                    isWritingReview=true;
                    write_review_button.setText("submit review");
                    textInputLayout=new TextInputLayout(Game_page.this);
                    textInputLayout.setBackground(getDrawable(R.drawable.rounded_background4));
                    textInputEditText=
                            new TextInputEditText(Game_page.this);
                    textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                    textInputLayout.addView(textInputEditText);
                    review_layout.addView(textInputLayout);
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
                    showReviews();
                    scroll_wrapper.getLayoutParams().height=900;
                }

            }
        });

    }

    private void showReviews(){
        while (review_layout.getChildCount() > 1){
            review_layout.removeView(review_layout.getChildAt(review_layout.getChildCount() - 1));
        }

        List<Review> reviews = game.getReviews();
        for (int i = 0; i < reviews.size(); i++) {
            TextView newReview = new TextView(this);
            newReview.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            newReview.setText(reviews.get(i).toString());
            review_layout.addView(newReview);
        }
    }
}
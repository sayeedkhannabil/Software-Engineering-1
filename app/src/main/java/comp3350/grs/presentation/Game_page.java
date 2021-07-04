package comp3350.grs.presentation;
// CLASS: Game_page...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// game detail page
//-----------------------------------------

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessGames;
import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Review;

public class Game_page extends Activity {
    private AccessGames accessGames;
    private AccessRatings accessRatings;
    private Game game;//the game we will show the details
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;//review edit text
    private boolean isWritingReview;//indicate if the user pressed the "write
    // review" button
    private LinearLayout review_wrapper;
    private ConstraintLayout review_background;
    private Button review_button;
    private RatingBar ratingBar;
    private TextView description_text;
    private ConstraintSet constraintSet;
    private ConstraintLayout game_page_main;

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
        accessRatings=new AccessRatings();
        game = accessGames.findGame(game_name);
        TextView game_text = (TextView) findViewById(R.id.textView5);
        TextView dev_text = (TextView) findViewById(R.id.textView6);
        TextView des_text = (TextView) findViewById(R.id.game_page_description_text);
        TextView price_text = (TextView) findViewById(R.id.game_page_price);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        review_wrapper =
                (LinearLayout) findViewById(R.id.review_wrapper);
        review_button =(Button) findViewById(R.id.game_page_review_button);
        review_background =
                (ConstraintLayout) findViewById(R.id.game_page_review_background);
        description_text =
                findViewById(R.id.game_page_description_text);
        game_page_main=findViewById(R.id.game_page_main);
        constraintSet=new ConstraintSet();
        constraintSet.clone(game_page_main);

        game_text.setText(game.getName());
        dev_text.setText("dev: " + game.getDev());
        des_text.setText(game.getDescription());
        price_text.setText("$" + game.getPrice());
        ratingBar.setRating((float) accessRatings.getOverallRating(game_name));
        showReviews();

        //when user clicked on the rating bar,update the rating
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(accessRatings.getRating(game.getName(),
                        AccessUsers.getActiveUser().getUserID())==null){

                    /*I commented out try-catch as the Rating constructor no longer throws an error
                    * (there is a try-catch inside the constructor) and an error was given since there is an additional
                    * try-catch here, where now no error is thrown in this try. If the constructor should actually be throwing
                    * an error and my code is not ideal, let me know and I can change it.
                    * - Katharine */

                   // try {
                        accessRatings.insertRating(new Rating(rating,
                                game.getName(),AccessUsers.getActiveUser().getUserID()));
                  /*  } catch (IncorrectFormat incorrectFormat) {
                        incorrectFormat.printStackTrace();
                    }*/
                }
                else {
                    //try {
                        accessRatings.updateRating(new Rating(rating,
                                game.getName(),
                                AccessUsers.getActiveUser().getUserID()));
                   /* } catch (IncorrectFormat incorrectFormat) {
                        incorrectFormat.printStackTrace();
                    }*/
                }

                ratingBar.setRating((float) accessRatings.getOverallRating(game.getName()));
            }
        });

        setReviewButton();

    }

    //set the behaviour when pressed review button
    private void setReviewButton(){
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the user just clicked "write review" button
                if(!isWritingReview){
                    if(ratingBar.getRating()==0.0) {
                        AlertDialog alertDialog = Utilities.createAlertDialog(
                                "please rate the game before writing review",
                                Game_page.this);
                        alertDialog.show();
                    }else {
                        isWritingReview=true;
                        review_button.setText("submit review");//change the
                        // text of the button
                        textInputLayout=new TextInputLayout(Game_page.this);
                        textInputLayout.setBackground(getDrawable(R.drawable.rounded_rectangle));
                        //show a input box used to write review
                        textInputEditText=
                                new TextInputEditText(Game_page.this);
                        textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                        textInputLayout.addView(textInputEditText);
                        review_wrapper.addView(textInputLayout);
                        //extend the review part to the whole screen
                        constraintSet.connect(review_background.getId(),
                                ConstraintSet.TOP,game_page_main.getId(),ConstraintSet.TOP);
                        constraintSet.applyTo(game_page_main);
                    }

                }
                else{
                    isWritingReview=false;
                    review_button.setText("write review");
                    String review=textInputEditText.getText().toString();
                    review_wrapper.removeView(textInputLayout);
                    review_wrapper.removeView(textInputEditText);

//                    game.addReview(ratingBar.getRating(),review);// TODO: 6/28/2021
                    showReviews();//update the review
                    constraintSet.connect(review_background.getId(),
                            ConstraintSet.TOP,
                            description_text.getId(),
                            ConstraintSet.BOTTOM);
                    constraintSet.applyTo(game_page_main);
                }

            }
        });
    }

    //remove all reviews generated before, and regenerate them according to
    // the updated game object, to show the newest review
    private void showReviews(){
        //remove reviews, except the "review" text itself
        while (review_wrapper.getChildCount() > 1){
            review_wrapper.removeView(review_wrapper.getChildAt(review_wrapper.getChildCount() - 1));
        }

//        List<Review> reviews = game.getReviews();// TODO: 6/28/2021
        //add all reviews one by one
//        for (int i = 0; i < reviews.size(); i++) {
//            TextView newReview = new TextView(this);
//            newReview.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
//            newReview.setText(reviews.get(i).toString());
//            review_wrapper.addView(newReview);
//        }
    }
}
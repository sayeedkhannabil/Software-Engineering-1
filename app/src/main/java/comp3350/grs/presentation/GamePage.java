package comp3350.grs.presentation;


// game detail page

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessGames;
import comp3350.grs.business.AccessRatings;
import comp3350.grs.business.AccessReviews;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Review;

public class GamePage extends AppCompatActivity {
    private AccessGames accessGames;
    private AccessRatings accessRatings;
    private AccessReviews accessReviews;
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
    private LinearLayout genre_wrapper;
    private String game_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        isWritingReview=false;
        Bundle extras = getIntent().getExtras();
        game_name = null;
        if (extras != null) {
            game_name = extras.getString("game");//get the game name
        }
        accessGames = new AccessGames();
        accessRatings=new AccessRatings();
        accessReviews=new AccessReviews();
        game = accessGames.getGameByName(game_name);
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
        genre_wrapper=findViewById(R.id.genre_wrapper);

        game_text.setText(game.getName());
        dev_text.setText("dev: " + game.getDev());
        des_text.setText(game.getDescription());
        price_text.setText("$" + game.getPrice());
        showReviews();
        //when user clicked on the rating bar,update the rating
        setRatingBar();
        setReviewButton();
        setGenre();
    }

    private void setRatingBar(){
        float ratingValue;
        ratingValue=(float) accessRatings.getOverallRating(game_name);
        TextView ratingValueText=findViewById(R.id.rating_value_text) ;
        ratingBar.setRating(ratingValue);
        ratingValueText.setText(String.format("%.1f",ratingValue));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser){
                    if(accessRatings.getRating(game.getName(),
                            AccessUsers.getActiveUser().getUserID())==null){
                        try {
                            accessRatings.insertRating(new Rating(rating,
                                    game.getName(),AccessUsers.getActiveUser().getUserID()));
                        } catch (IncorrectFormat incorrectFormat) {
                            incorrectFormat.printStackTrace();
                        }
                    }
                    else {
                        try {
                            accessRatings.updateRating(new Rating(rating,
                                    game.getName(),
                                    AccessUsers.getActiveUser().getUserID()));
                        } catch (IncorrectFormat incorrectFormat) {
                            incorrectFormat.printStackTrace();
                        }
                    }
                    float ratingValue=
                            (float) accessRatings.getOverallRating(game_name);
                    ratingBar.setRating(ratingValue);
                    ratingValueText.setText(String.format("%.1f",ratingValue));
                }

            }
        });



    }

    private void setGenre(){
        List<String> genreList=game.getGenres();
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout myLayout;
        TextView genre;

        for (int i = 0; i < genreList.size(); i++) {
            myLayout = (RelativeLayout) inflater.inflate(R.layout.genre, null, false);
            genre= (TextView) myLayout.getChildAt(0);
            genre.setText(genreList.get(i));
            genre_wrapper.addView(myLayout);
        }
    }

    //set the behaviour when pressed review button
    private void setReviewButton(){
        Utilities.setOnTouchEffect(review_button);
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the user just clicked "write review" button
                if(!isWritingReview){
                    if(accessRatings.getRating(game.getName(),
                            AccessUsers.getActiveUser().getUserID())==null) {
                        AlertDialog alertDialog = Utilities.createAlertDialog(
                                "please rate the game before writing review",
                                GamePage.this);
                        alertDialog.show();
                    }else {
                        isWritingReview=true;
                        review_button.setText("submit review");//change the
                        // text of the button
                        textInputLayout=new TextInputLayout(GamePage.this);
                        textInputLayout.setBackground(getDrawable(R.drawable.rounded_rectangle));
                        //show a input box used to write review
                        textInputEditText=
                                new TextInputEditText(GamePage.this);
                        textInputEditText.setId(R.id.write_review_edittext);
                        textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                        textInputLayout.addView(textInputEditText);
                        review_wrapper.addView(textInputLayout);
                        //extend the review part to the whole screen
                        constraintSet.connect(review_background.getId(),
                                ConstraintSet.TOP,R.id.game_page_top_bar,
                                ConstraintSet.BOTTOM);
                        constraintSet.applyTo(game_page_main);
                    }

                }
                else{
                    try {
                        String comment=textInputEditText.getText().toString();
                        Review newReview=new Review(comment,game_name,
                                AccessUsers.getActiveUser().getUserID());
                        accessReviews.insertReview(newReview);
                        showReviews();//update the review
                        isWritingReview=false;
                        review_button.setText("write review");
                        review_wrapper.removeView(textInputLayout);
                        review_wrapper.removeView(textInputEditText);
                        constraintSet.connect(review_background.getId(),
                                ConstraintSet.TOP,
                                description_text.getId(),
                                ConstraintSet.BOTTOM);
                        constraintSet.applyTo(game_page_main);
                    } catch (IncorrectFormat incorrectFormat) {
                        AlertDialog alertDialog =
                                Utilities.createAlertDialog(incorrectFormat.getMessage(), GamePage.this);
                        alertDialog.show();
                    }
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

        List<Review> reviews = accessReviews.getReviewsByGame(game_name);
//        add all reviews one by one
        for (int i = 0; i < reviews.size(); i++) {
            TextView newReview = new TextView(this);
            newReview.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            newReview.setText(reviews.get(i).toString());
            review_wrapper.addView(newReview);
        }
    }
}
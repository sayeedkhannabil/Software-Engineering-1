package comp3350.grs.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessRequests;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.Duplicate;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Request;

public class RequestPage extends AppCompatActivity {
    private TextView submit_request_button;
    private AccessRequests accessRequests;
    private TextInputEditText request_game_input;
    private LinearLayout games_requested_wrapper;
    private LinearLayout most_requested_game_wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);

        initiate();
        showGamesRequested();
        showMostRequestedGames();
        setTopBar();
        setSubmit_request_button();
    }

    private void initiate(){
        submit_request_button=findViewById(R.id.submit_request_button);
        accessRequests=new AccessRequests();
        request_game_input=findViewById(R.id.request_game_input);
        games_requested_wrapper=findViewById(R.id.games_requested_wrapper);
        most_requested_game_wrapper=
                findViewById(R.id.most_requested_game_wrapper);
    }

    private void setTopBar(){
        TextView top_bar_text;
        top_bar_text=findViewById(R.id.top_bar_text);
        top_bar_text.setText("Request Game");
    }

    private void setSubmit_request_button(){
        Utilities.setOnTouchEffect(submit_request_button);
        submit_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameName=request_game_input.getText().toString();
                String userID= AccessUsers.getActiveUser().getUserID();
                try {
                    Request request=new Request(gameName,userID);
                    accessRequests.insertRequest(request);
                    showGamesRequested();
                    showMostRequestedGames();
                } catch (IncorrectFormat | Duplicate incorrectFormat) {
                    AlertDialog alertDialog;
                    alertDialog=
                            Utilities.createAlertDialog(incorrectFormat.getMessage(),RequestPage.this);
                    alertDialog.show();
                }
            }
        });
    }


    private void showGamesRequested(){
        List<Request> requestList;
        String currGame;
        LayoutInflater inflater;
        View view;
        TextView request_game_text;

        requestList=
                accessRequests.getRequestsByUser(AccessUsers.getActiveUser().getUserID());
        games_requested_wrapper.removeAllViews();
        inflater=getLayoutInflater();
        for (int i = 0; i < requestList.size(); i++) {
            currGame=requestList.get(i).getGameName();
            view=inflater.inflate(R.layout.games_requested,
                    null,false);
            request_game_text=view.findViewById(R.id.request_game_text);
            request_game_text.setText(currGame);
            games_requested_wrapper.addView(view);
        }
    }

    private void showMostRequestedGames(){
        List<String> gameList;
        String currGame;
        String num;
        LayoutInflater inflater;
        View view;
        TextView most_requested_game,most_requested_num;

        most_requested_game_wrapper.removeAllViews();
        gameList=accessRequests.getGamesByRequestNum(3);
        inflater=getLayoutInflater();
        for (int i = 0; i < gameList.size(); i++) {
            currGame=gameList.get(i);
            num=accessRequests.getRequestsByGame(currGame).size()+"";
            view=inflater.inflate(R.layout.most_requested,null,false);
            most_requested_game=view.findViewById(R.id.most_requested_game);
            most_requested_num=view.findViewById(R.id.most_requested_num);
            most_requested_game.setText(currGame);
            most_requested_num.setText(num);
            most_requested_game_wrapper.addView(view);
        }
    }
}
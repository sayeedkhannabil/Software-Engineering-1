package comp3350.grs.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessGames;
import comp3350.grs.objects.Game;

public class Game_gallery extends AppCompatActivity {
    private TableLayout tableLayout;
    private Game game;
    private View thumbnail;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_gallery);

        tableLayout=(TableLayout) findViewById(R.id.table_layout);
        addGames();
    }

    private void addGames(){
        TableRow newRow=null;

        LayoutInflater inflater;
        AccessGames accessGames=new AccessGames();
        List<Game>games=accessGames.getGames();
        for (int i = 0; i < games.size(); i++) {
            game=games.get(i);
            if(i%2==0){
                newRow=new TableRow(this);
                tableLayout.addView(newRow);
            }
            inflater = getLayoutInflater();
            thumbnail = inflater.inflate(R.layout.game_thumbnail, newRow, false);
            textView=thumbnail.findViewById(R.id.game_name);
            textView.setText(game.getName());
            thumbnail.setId(i);
            newRow.addView(thumbnail);
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Game_gallery.this,Game_page.class);
                    game=games.get(v.getId());
                    intent.putExtra("game",game.getName());
                    startActivity(intent);
                }
            });
        }
    }


}
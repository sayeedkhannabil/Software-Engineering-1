package comp3350.grs.presentation;
// CLASS: DataAccessStub
//
// Author: Shiqing Guo
//
// REMARKS: a gallery of game thumbnail
// for now icon is hard coded, all the icon are the same
//
//-----------------------------------------

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessGames;
import comp3350.grs.objects.Game;

public class Game_gallery extends AppCompatActivity {
    private TableLayout tableLayout;//the layout to add thumbnail
    private Game game;//the current game to add
    private View thumbnail;
    private TextView textView;//name of the game
    private TextView top_bar_text;
    private SearchView search_game_bar;
    private AccessGames accessGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_gallery);

        accessGames=new AccessGames();
        setTop_bar();
        tableLayout=(TableLayout) findViewById(R.id.table_layout);
        List<Game>games=accessGames.getAllGames();
        generateGamesThumbnail(games);
        setSearch_game_bar();
    }


    private void setTop_bar(){
        top_bar_text=findViewById(R.id.top_bar_text);
        top_bar_text.setText("Game Gallery");
    }

    //generate game thumbnail according to database
    private void generateGamesThumbnail(List<Game>games){
        tableLayout.removeAllViews();
        TableRow newRow=null;
        LayoutInflater inflater;

        for (int i = 0; i < games.size(); i++) {
            game=games.get(i);
            //every row contains two games
            if(i%2==0){
                newRow=new TableRow(this);
                tableLayout.addView(newRow);
            }
            inflater = getLayoutInflater();
            thumbnail = inflater.inflate(R.layout.game_thumbnail, newRow, false);
            textView= (TextView) thumbnail.findViewById(R.id.game_page_price);
            textView.setText(game.getName());
            thumbnail.setId(i);
            newRow.addView(thumbnail);
            //after click the thumbnail, the page should turned into that
            // game's detail page
            Utilities.setOnTouchEffect(thumbnail);
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Game_gallery.this,Game_page.class);
                    game=games.get(v.getId());
                    intent.putExtra("game",game.getName());//pass the game
                    // name to game detail page
                    startActivity(intent);
                }
            });
        }
    }

    private void setSearch_game_bar(){// TODO: 6/30/2021
        search_game_bar=findViewById(R.id.search_game_bar);
        search_game_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                generateGamesOnSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                generateGamesOnSearch(newText);
                return false;
            }
        });

        search_game_bar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                List<Game>games=accessGames.getAllGames();
                generateGamesThumbnail(games);
                return false;
            }
        });


    }

    private void generateGamesOnSearch(String text){
        List<Game> gameList=new ArrayList<Game>();
        Game game=accessGames.findGame(text);
        if (game!=null){
            gameList.add(game);
        }
        generateGamesThumbnail(gameList);
    }

}
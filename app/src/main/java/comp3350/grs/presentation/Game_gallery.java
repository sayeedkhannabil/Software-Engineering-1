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

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
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
    private LinearLayout top_bar_layout;
    private LinearLayout sort_bar;
    private boolean [] sortAsc;//keep track what sort order is(ascending or
    // descending)
    private boolean [] activeSort;//keep track which sort button is active
    private boolean openSort,openSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_gallery);

        accessGames=new AccessGames();
        sortAsc=new boolean[4];
        Arrays.fill(sortAsc,false);
        activeSort=new boolean[4];
        Arrays.fill(activeSort,false);
        tableLayout=findViewById(R.id.table_layout);
        sort_bar=findViewById(R.id.sort_bar);
        search_game_bar=findViewById(R.id.search_game_bar);
        List<Game>games=accessGames.getAllGames();
        openSort=false;
        openSearch=false;

        setTop_bar();
        generateGamesThumbnail(games);
        setSearch_game_bar();
        setSortBar();
    }

    //set the sort buttons
    private void setSortDisplay(){
        List<Game> gameList=new ArrayList<Game>();
        boolean notSorting=true;//all the sort button is inactive
        for (int i = 0; i < activeSort.length&&notSorting; i++) {
            if (activeSort[i]){
                notSorting=false;
                if (i==0){
                    if (sortAsc[i]){
                        gameList=accessGames.ascendingNameSort();
                    }
                    else {
                        gameList=accessGames.descendingNameSort();
                    }
                }
                else if(i==1){
                    if (sortAsc[i]){
                        gameList=accessGames.ascendingPriceSort();
                    }
                    else {
                        gameList=accessGames.descendingPriceSort();
                    }
                }
                else if(i==2){
                    if (sortAsc[i]){
                        gameList=accessGames.ascendingRatingSort();
                    }
                    else {
                        gameList=accessGames.descendingRatingSort();
                    }
                }
                else{
                    if (sortAsc[i]){
                        gameList=accessGames.ascendingReviewSort();
                    }
                    else {
                        gameList=accessGames.descendingReviewSort();
                    }
                }
            }
        }

        if (notSorting){
            gameList=accessGames.getAllGames();
        }

        generateGamesThumbnail(gameList);
    }

    private void setSortBackground(List<LinearLayout> sortButtons){
        LinearLayout sortButton;
        ImageView icon;
        for (int i = 0; i < sortButtons.size(); i++) {
            sortButton=sortButtons.get(i);
            icon= (ImageView) sortButton.getChildAt(0);
            if (activeSort[i]){
                sortButton.setBackgroundColor(getResources().getColor(R.color.grey));
                if (sortAsc[i]){//ascending , set the icon to be up arrow
                    icon.setImageResource(R.drawable.up_arrow);
                }
                else {//descending , set the icon to be down arrow
                    icon.setImageResource(R.drawable.down_arrow);
                }
            }
            else {//not sorting,set icon to be up and down
                sortButton.setBackgroundColor(getResources().getColor(R.color.white));
                icon.setImageResource(R.drawable.sort);
            }
        }
    }


    private void setSortButton(int index,List<LinearLayout> sortButtons){
        LinearLayout sortButton=sortButtons.get(index);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeSort[index]){
                    if (sortAsc[index]){
                        activeSort[index]=false;
                    }
                    else {
                        sortAsc[index]=true;
                    }
                }
                else {
                    sortAsc[index] = false;
                    Arrays.fill(activeSort, false);
                    activeSort[index] = true;
                }
                //set icons and background of the sort buttons
                setSortBackground(sortButtons);
                //display the sort result
                setSortDisplay();
            }
        });
    }

    private void setSortBar(){
        List<LinearLayout> sortButtons=new ArrayList<LinearLayout>();
        sortButtons.add(findViewById(R.id.name_sort));
        sortButtons.add(findViewById(R.id.price_sort));
        sortButtons.add(findViewById(R.id.rating_sort));
        sortButtons.add(findViewById(R.id.review_sort));

        for (int i = 0; i < sortButtons.size(); i++) {
            setSortButton(i,sortButtons);
        }
    }

    private void setTop_bar(){
        search_game_bar.setVisibility(View.GONE);
        sort_bar.setVisibility(View.GONE);
        top_bar_text=findViewById(R.id.top_bar_text);
        top_bar_text.setText("Game Gallery");
        top_bar_layout=findViewById(R.id.top_bar_layout);
        ImageView search_icon=new ImageView(this);
        search_icon.setImageResource(R.drawable.search);
        setIcon(search_icon);
        ImageView sort_icon=new ImageView(this);
        sort_icon.setImageResource(R.drawable.sort);
        setIcon(sort_icon);

        top_bar_layout.addView(search_icon,2);
        top_bar_layout.addView(sort_icon,2);

        Utilities.setOnTouchEffect(search_icon);
        Utilities.setOnTouchEffect(sort_icon);

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openSearch){
                    search_game_bar.setVisibility(View.GONE);
                }else {
                    search_game_bar.setVisibility(View.VISIBLE);
                }
                openSearch=!openSearch;
            }
        });

        sort_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openSort){
                    sort_bar.setVisibility(View.GONE);
                }else {
                    sort_bar.setVisibility(View.VISIBLE);
                }
                openSort=!openSort;
            }
        });
    }

    private void setIcon(ImageView imageView){
        int width= (int) getResources().getDimension(R.dimen.top_bar_icon_width);
        int height=(int) getResources().getDimension(R.dimen.match_parent);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(width,height,0f);

        imageView.setLayoutParams(layoutParams);

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

    private void setSearch_game_bar(){

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

        //when user close the search bar, reset all the games
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
        List<Game> gameList;
        gameList=accessGames.getGamesByNameImplicit(text);
        generateGamesThumbnail(gameList);
    }

}
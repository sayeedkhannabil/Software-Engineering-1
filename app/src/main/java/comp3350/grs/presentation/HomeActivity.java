package comp3350.grs.presentation ;

import comp3350.grs.R ;

import android.app.Activity ;
import android.os.Bundle ;
import android.view.Menu ;
import android.view.MenuItem ;
import android.view.View ;
import android.content.Intent ;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu) ;
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId() ;
        return super.onOptionsItemSelected(item) ;
    }

    public void onbtnRegisterClicked(View view) {
        Intent browseGames = new Intent(HomeActivity.this, BrowseGames.class);
        HomeActivity.this.startActivity(browseGames);
    }

    public void onbtnRightClicked(View view) {
        Intent browseGames = new Intent(HomeActivity.this, BrowseGames.class);
        HomeActivity.this.startActivity(browseGames);
    }
}

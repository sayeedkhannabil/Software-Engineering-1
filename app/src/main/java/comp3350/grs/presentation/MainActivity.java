package comp3350.grs.presentation;

// homepage
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import comp3350.grs.R;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.persistence.DataAccessI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDB();
        Main.startUp();
    }

    private void setDB(){
        Context context = getApplicationContext();
        File dataDirectory = context.getDir("database", Context.MODE_PRIVATE);
        //when running on the emulator, change the database path to correct
        // position
        Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);
    }

}
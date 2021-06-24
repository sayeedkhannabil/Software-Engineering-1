package comp3350.grs.presentation;
// CLASS: MainActivity...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// homepage
//-----------------------------------------
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import comp3350.grs.R;
import comp3350.grs.application.Main;

public class MainActivity extends AppCompatActivity {
    private static boolean isRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRunning=true;
        Main.startUp();
    }


    public static boolean getIsRunning(){
        return isRunning;
    }
}
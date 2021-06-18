package comp3350.grs.presentation;
// CLASS: MainActivity...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// homepage
//-----------------------------------------
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
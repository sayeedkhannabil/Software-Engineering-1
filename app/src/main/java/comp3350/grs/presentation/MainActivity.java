package comp3350.grs.presentation;
// CLASS: MainActivity...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// homepage
//-----------------------------------------
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

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main.startUp(fileToString());
    }

    //convert json to string, used to open database
    public  String fileToString() {
        InputStream inputStream=getResources().openRawResource(R.raw.csvjson);
        StringBuilder text = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close() ;
        }catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

}
package comp3350.grs.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import comp3350.grs.R;

public class RequestPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);
        setTopBar();
    }

    private void setTopBar(){
        TextView top_bar_text;
        top_bar_text=findViewById(R.id.top_bar_text);
        top_bar_text.setText("Request Page");
    }
}
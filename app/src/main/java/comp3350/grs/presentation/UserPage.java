package comp3350.grs.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import comp3350.grs.R;
import comp3350.grs.business.AccessUsers;

public class UserPage extends AppCompatActivity {
    private TextView top_bar_text;
    private TextView user_page_user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        top_bar_text=top_bar_text=findViewById(R.id.top_bar_text);
        user_page_user_info=findViewById(R.id.user_page_user_info);
        top_bar_text.setText("User Account");
        user_page_user_info.setText(AccessUsers.getActiveUser().toString());
    }
}
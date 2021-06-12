package comp3350.grs.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;

import comp3350.grs.R;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button sinup=(Button)findViewById(R.id.button4);
        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText userid=
                        (TextInputEditText)findViewById(R.id.input_userid);
                TextInputEditText password=
                        (TextInputEditText)findViewById(R.id.input_password);
                try {
                    User user=new RegisteredUser(userid.getText().toString(),
                            password.getText().toString());
                    AccessUsers accessUsers=new AccessUsers();
                    accessUsers.insertUser(user);
                    AccessUsers.setCurrentUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(Signup.this,Game_gallery.class);
                startActivity(intent);
            }
        });
    }
}
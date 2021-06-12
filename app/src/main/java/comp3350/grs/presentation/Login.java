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


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login=(Button)findViewById(R.id.button3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText userid=
                        (TextInputEditText)findViewById(R.id.input_userid);
                TextInputEditText password=
                        (TextInputEditText)findViewById(R.id.input_password);
                String userids=userid.getText().toString();
                String passwords=password.getText().toString();
                try {
                    RegisteredUser user;
                    AccessUsers accessUsers=new AccessUsers();
                    user=(RegisteredUser) accessUsers.getRandom(userids);
                    if(user!=null&&user.validPass(passwords)){
                        AccessUsers.setCurrentUser(user);
                        Intent intent=new Intent(Login.this,Game_gallery.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
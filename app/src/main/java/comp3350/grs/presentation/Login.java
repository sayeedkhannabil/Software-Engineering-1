package comp3350.grs.presentation;
// CLASS: Login...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// login page
//-----------------------------------------
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
                String useridStr=userid.getText().toString();
                String passwordStr=password.getText().toString();
                try {
                    RegisteredUser user;
                    AccessUsers accessUsers=new AccessUsers();
                    user=(RegisteredUser) accessUsers.getRandom(useridStr);
                    //user exists in the database, and the password is valid
                    if(user!=null&&user.validPass(passwordStr)){
                        AccessUsers.setActiveUser(user);//set the user as active
                        Intent intent=new Intent(Login.this,Game_gallery.class);
                        //open the game gallery page
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
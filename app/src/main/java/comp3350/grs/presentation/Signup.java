package comp3350.grs.presentation;
// CLASS: Signup...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// Signup page
//-----------------------------------------
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;


import com.google.android.material.textfield.TextInputEditText;

import comp3350.grs.R;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;

public class Signup extends FragmentActivity {

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
                    user=accessUsers.getUserByID(userid.getText().toString());
                    if(user!=null){//the registration succeed, login to game
                        // gallery
                        AccessUsers.setActiveUser(user);
                        Intent intent=new Intent(Signup.this,Game_gallery.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    AlertDialog alertDialog=
                            Utilities.createAlertDialog(e.getMessage(),
                                    Signup.this);
                    alertDialog.show();
                }


            }
        });
    }


}
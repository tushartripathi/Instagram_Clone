package com.vubird.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {


    private EditText signInUsernameBox, signInPasswordBox, loginUsernameBox, loginPasswordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        signInUsernameBox = findViewById(R.id.singinUsernameEdit);
        signInPasswordBox = findViewById(R.id.singinpassowrEdit);
        loginUsernameBox = findViewById(R.id.loginUsernameBox);
        loginPasswordBox = findViewById(R.id.loginPasswordBox);


        }

    public void LoginBtn(View view)
    {
        ParseUser.logInInBackground(loginUsernameBox.getText().toString().trim(), loginPasswordBox.getText().toString().trim(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!= null)
                    FancyToast.makeText(SignUpLoginActivity.this,"Welcome back! "+ loginUsernameBox.getText().toString() ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                else
                    FancyToast.makeText(SignUpLoginActivity.this,"Oops! You guessed it wrong ",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });

    }

    public void SigninBtn(View view)
    {
        ParseUser appuser = new ParseUser();
        appuser.setUsername(signInUsernameBox.getText().toString().trim());
        appuser.setPassword(signInPasswordBox.getText().toString().trim());

        appuser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e)
            {
                if(e==null)
                FancyToast.makeText(SignUpLoginActivity.this,"Welcome ! "+ signInUsernameBox.getText().toString() ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                else
                    FancyToast.makeText(SignUpLoginActivity.this,"Oops! Sorrry "+ signInUsernameBox.getText().toString() ,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }

        });
    }
}

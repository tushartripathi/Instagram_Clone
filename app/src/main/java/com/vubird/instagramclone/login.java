package com.vubird.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class login extends AppCompatActivity {

    EditText usernameBox, passwordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameBox = findViewById(R.id.usernameBox);
        passwordBox = findViewById(R.id.passwordBox);

        passwordBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i==KeyEvent.KEYCODE_ENTER &&  keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                {
                    LoginUser(view);
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser()!=null)
        {
//            Intent i = new Intent(this, SignUpLoginActivity.class);
//            startActivity(i);
        }

    }

    public  void gotoSignIn(View view)
    {
        Intent i = new Intent(this, signUp.class);
        startActivity(i);
    }

    public void LoginUser(View view)
    {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In " + usernameBox.getText().toString() + "......... ");
        progressDialog.show();
        ParseUser.logInInBackground(usernameBox.getText().toString().trim(), passwordBox.getText().toString().trim(), new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {


                if(user!=null && e==null)
                {

                    FancyToast.makeText(login.this, "Welcome back! " + usernameBox.getText().toString(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    progressDialog.dismiss();
                    gotoWelcome();

                }

            }
        });
    }

    public void gotoWelcome()
    {

    }
    public  void rootClick(View view)
    {
        try {


            InputMethodManager inputMethod = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e)
        {

        }
    }
}

package com.vubird.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {


    EditText nameET, PunchSpeedET, PunchPowerET, KickSpeedET, KickPOwerET;
    variablesGetterSetter obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FancyToast.makeText(MainActivity.this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

        FancyToast.makeText(this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();


        FancyToast.makeText(this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();


        FancyToast.makeText(this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();


        FancyToast.makeText(this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.CONFUSING,true).show();




        nameET = findViewById(R.id.namebox);
        PunchSpeedET = findViewById(R.id.punchSpeedBox);
        PunchPowerET = findViewById(R.id.punchPowerBox);
        KickPOwerET = findViewById(R.id.KickPowerBox);
        KickSpeedET = findViewById(R.id.kickSpeedBox);



    }



    public  void addDataToServer(View view)
    {

        obj = variablesGetterSetter.getInstance();
        obj.setName(nameET.getText().toString());
        obj.setPunchSpeed(PunchSpeedET.getText().toString());
        obj.setKickSpeed(KickSpeedET.getText().toString());
        obj.setPunchPower(PunchPowerET.getText().toString());
        obj.setKickPower(KickPOwerET.getText().toString());

        ParseObject player = new ParseObject("kickBoxer");
        player.put("name",obj.getName());
        player.put("punchSpeed",obj.getPunchSpeed());
        player.put("punchPower",obj.getPunchPower());
        player.put("kickSpeed",obj.getKickSpeed());
        player.put("kickPower",obj.getKickPower());

        player.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                nameET.setText("Done");
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT);
                FancyToast.makeText(MainActivity.this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

            }
        });
    }
}

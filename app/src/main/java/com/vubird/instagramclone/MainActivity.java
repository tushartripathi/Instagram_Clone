package com.vubird.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText nameET, PunchSpeedET, PunchPowerET, KickSpeedET, KickPOwerET;
    TextView tv;
    Button getBtn;
    private  String punchPower;
    variablesGetterSetter obj;
    private String allKickBoxer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.dataTextView);
        getBtn = findViewById(R.id.getAllvalueBtn);
        nameET = findViewById(R.id.namebox);
        PunchSpeedET = findViewById(R.id.punchSpeedBox);
        PunchPowerET = findViewById(R.id.punchPowerBox);
        KickPOwerET = findViewById(R.id.KickPowerBox);
        KickSpeedET = findViewById(R.id.kickSpeedBox);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ParseQuery <ParseObject> parsequery = ParseQuery.getQuery("kickBoxer");
                parsequery.getInBackground("pHdd9AITWY", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e)
                    {
                        if(object!=null)
                        {
                            tv.setText(object.getString("name"));
                            FancyToast.makeText(MainActivity.this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        }
                    }
                });
            }
        });
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

    public void getAllValues(View view)
    {

        allKickBoxer = "";
        punchPower = "";
        ParseQuery<ParseObject>  queryAll = ParseQuery.getQuery("kickBoxer");
        queryAll.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if(!objects.isEmpty())
                {


                    for(ParseObject kickBoxerParseObj :  objects) {
                        allKickBoxer = allKickBoxer + kickBoxerParseObj.get("name") + "\n";
                        punchPower = punchPower + kickBoxerParseObj.get("punchSpeed") + "\n";
                    }
                    FancyToast.makeText(MainActivity.this, allKickBoxer ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    FancyToast.makeText(MainActivity.this, punchPower ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                }
            }
        });

    }
}

package com.vubird.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class userPost extends AppCompatActivity {


    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        linearLayout = findViewById(R.id.linearlayout);
        Intent i = getIntent();
        final String username = i.getStringExtra("username");


        setTitle(username );
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("photo");
        parseQuery.whereEqualTo("username",username);
        parseQuery.orderByDescending("createdAt");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Post");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(objects.size() > 0 && e==null)
                {
                    for(ParseObject obj : objects)
                    {
                        final TextView imgDes = new TextView(userPost.this);
                        imgDes.setText(obj.getString("image_des"));
                        ParseFile file = (ParseFile)obj.get("picture");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if(data!=null && e==null)
                                {

                                    Bitmap bitMap = BitmapFactory.decodeByteArray(data,0,data.length);

                                    ImageView postImageView = new ImageView(userPost.this);

                                    LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(30,20,30,0);

                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitMap);



                                    LinearLayout.LayoutParams desc_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    desc_Params.setMargins(5,0,5,5);
                                    imgDes.setLayoutParams(desc_Params);
                                    imgDes.setGravity(Gravity.CENTER);
                                    imgDes.setBackgroundColor(Color.BLUE);
                                    imgDes.setTextColor(Color.WHITE);
                                    imgDes.setTextSize(30f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(imgDes);


                                }

                            }
                        });
                    }
                }
                else
                {
                    FancyToast.makeText(userPost.this, "No Post", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    finish();
                }

                progressDialog.dismiss();
            }
        });



    }
}

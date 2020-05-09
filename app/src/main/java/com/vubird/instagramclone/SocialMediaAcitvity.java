package com.vubird.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class SocialMediaAcitvity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TapAdapter tapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_acitvity);


        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        setTitle("InstaClone");
        viewPager = findViewById(R.id.viewpager);
        tapAdapter = new TapAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tapAdapter);


        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager,false);

    }

    // create Menu in Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    //called when menu is clicked
    //swtich case to check which menu item is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.postImageItem:
                //if camera menu item is clicked then first check for permission then capture the image
                getPermission();
                break;
            case R.id.logOutUser:
                ParseUser.logOut();
                finish();
                Intent i = new Intent(this, login.class);
                startActivity(i);


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPermission()
    {

        //check fro build version
        //if build is greate or == to Marshmello (23) then we have to ask for permission from user
        //permisson foer extenal storeage

        if(Build.VERSION.SDK_INT >= 23 &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)  // checkign if permission is already given // If not then send a requst for permsionn
        {

            //requesting permission from user fro external storage
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},3000);

            //when user give response to permison request tap then onRequestPermissionsResult() is called


        }
        else
        {
            //if permisson is gvien
            captureImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //chek the response of user fro permisson request

        if(requestCode==3000)
        {
            //if permsision is granted granted
            if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                captureImage();
            }
        }
    }

    public void captureImage()
    {
        //creating intent for accessing phone camera
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //when we want to receive result from the intent then we user startActivityForResult()
        startActivityForResult(i,4000);

        //on Response from user onActivityResult is called
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

// called when data is coming from external intnet
        if(requestCode==4000 && resultCode == RESULT_OK && data !=null)
        {
            try
            {
                    Uri captureImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), captureImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                UploadImageToServer(bytes);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private void UploadImageToServer( byte[] bytes)
    {
        ParseFile parseFile = new ParseFile("pic.png",bytes);
        ParseObject parseObject = new ParseObject("photo");
        parseObject.put("picture",parseFile);
     //   parseObject.put("image_des",editText.getText().toString().trim());
        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image to server");
        progressDialog.show();

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e)
            {
                if(e==null)
                {

                    FancyToast.makeText(SocialMediaAcitvity.this, "Image Uploaded!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                }
                else
                {
                    FancyToast.makeText(SocialMediaAcitvity.this, "Oops!  Something went wrong", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();

                }
                progressDialog.dismiss();
            }
        });
    }
}

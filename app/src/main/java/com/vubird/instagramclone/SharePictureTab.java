package com.vubird.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


public class SharePictureTab extends Fragment implements View.OnClickListener
{

    private ImageView imageView;
    private Button button;
    private EditText editText;
    Bitmap receivedImageBitmap;
    public SharePictureTab()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imageView = view.findViewById(R.id.imageView);
        button =   view.findViewById(R.id.shareBtn);
        editText = view.findViewById(R.id.descriptionBox);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view)
    {
            switch (view.getId())
            {
                case R.id.shareBtn:
                        shareImageToserver();
                    break;
                case R.id.imageView:
                    checkPremisson();
                    break;
            }
    }

    public void checkPremisson()
    {
       //Marshmello and above will ask  permission
        if(Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE},1000);

        }
        else
        {
            getChoosenImage();
        }
    }

    private void getChoosenImage()
    {
        Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI );

        startActivityForResult(i,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getChoosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==2000)
        {
            if(resultCode == Activity.RESULT_OK )
            {
                try
                {
                    Uri selectedImage= data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn,
                            null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imageView.setImageBitmap(receivedImageBitmap);
                }
                catch (Exception e)
                {

                }
            }
        }
    }

    void shareImageToserver()
    {
        if(receivedImageBitmap!=null)
        {
            if(editText.getText().toString().equals(""))
            {
               Toast.makeText(getContext(), "Add Description",Toast.LENGTH_SHORT);
            }
            else
            {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                ParseFile parseFile = new ParseFile("pic.png",bytes);
                ParseObject parseObject = new ParseObject("photo");
                parseObject.put("picture",parseFile);
                parseObject.put("image_des",editText.getText().toString().trim());
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Uploading Image to server");
                progressDialog.show();

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e)
                    {
                        if(e==null)
                        {

                            FancyToast.makeText(getContext(), "Image Uploaded!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        }
                        else
                        {
                            FancyToast.makeText(getContext(), "Oops!  Something went wrong", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();

                        }
                        progressDialog.dismiss();
                    }
                });
            }
        }

        else
        {

        }
    }
}

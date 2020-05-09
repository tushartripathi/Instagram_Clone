package com.vubird.instagramclone;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Profiletab extends Fragment implements View.OnClickListener {


    EditText nameET, professionET, hobbiesET, sportsET, bioET ;
    Button btn ;


    public Profiletab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profiletab, container, false);
        nameET =   view.findViewById(R.id.nameBox);
        bioET =   view.findViewById(R.id.bioBox);
        professionET =   view.findViewById(R.id.ProfessionBox);
        hobbiesET =   view.findViewById(R.id.hobbiesBox);
        sportsET =   view.findViewById(R.id.sportsBox);
        btn = view.findViewById(R.id.button);
        btn.setOnClickListener(this);
        return view;

        }


        public void updateOnClick(ParseUser user) {
            user.put("Profilename", nameET.getText().toString().trim());
            user.put("ProfileBio", bioET.getText().toString().trim());
            user.put("Profession", professionET.getText().toString().trim());
            user.put("Hobbies", hobbiesET.getText().toString().trim());
            user.put("FavSports", sportsET.getText().toString().trim());

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Updating Profile");
            progressDialog.show();
            user.saveInBackground(new SaveCallback()
            {
                @Override
                public void done(ParseException e)
                {
                    progressDialog.dismiss();

                    if (e == null)
                    {
                        FancyToast.makeText(getContext(), "Profile Updated", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    }

                    else
                        {
                            FancyToast.makeText(getContext(), "Error Updating", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                        }
                }
            });
        }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button:
                updateOnClick(ParseUser.getCurrentUser());
                break;
        }
    }

}

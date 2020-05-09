package com.vubird.instagramclone;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> userArrayList;
    private ArrayAdapter arrayAdapter;


    public UsersTab()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView = view.findViewById(R.id.listview);

        listView.setOnItemLongClickListener(UsersTab.this);
        listView.setOnItemClickListener(this);
        userArrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, userArrayList);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("sdfdf");
//        progressDialog.show();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null && objects.size() > 0)
                {
                    for(ParseUser user : objects)
                    {
                        userArrayList.add(user.getUsername());
                    }
                    listView.setAdapter(arrayAdapter);
                }
  //              progressDialog.dismiss();
            }
        });




        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = new Intent(getContext(),userPost.class);
        intent.putExtra("username", userArrayList.get(i));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        final ParseQuery<ParseUser> parseQuery = new ParseUser().getQuery();
        parseQuery.whereEqualTo("username", userArrayList.get(i));
parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
    @Override
    public void done(ParseUser user, ParseException e)
    {
            if(user!= null && e==null )
            {
              //  FancyToast.makeText(getContext(), "Found", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                final PrettyDialog prettyDialog = new PrettyDialog(getContext());
                prettyDialog.setTitle(user.getUsername() + "'s Info")
                        .setMessage(user.get("ProfileBio")+"\n"+
                                user.get("Profession")+"\n"+
                                user.get("FavSports")+"\n"+
                                user.getEmail())
                        .setIcon(R.drawable.person)
                        .addButton(
                                "OK", R.color.colorAccent, R.color.pdlg_color_green, new PrettyDialogCallback() {
                                    @Override
                                    public void onClick()
                                    {

                                        prettyDialog.dismiss();
                                    }
                                }
                        ).show();


            }
            }
        });


        return true;
    }
}

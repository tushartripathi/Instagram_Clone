package com.vubird.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SocialMediaAcitvity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TapAdapter tapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_acitvity);

        setTitle("InstaClone");
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewpager);
        tapAdapter = new TapAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tapAdapter);


        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager,false);

    }
}

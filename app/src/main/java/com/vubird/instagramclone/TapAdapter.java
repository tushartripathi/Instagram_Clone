package com.vubird.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TapAdapter extends FragmentPagerAdapter
{
    public TapAdapter(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tapPosition)
    {
        switch (tapPosition)
        {
            case 0:
                Profiletab profiletab = new Profiletab();
                return profiletab;
            case 1:
                UsersTab usersTab = new UsersTab();
                return usersTab;
            case 2:
                SharePictureTab sharePictureTab = new SharePictureTab();
                return sharePictureTab;
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int tapPosition) {

        switch (tapPosition)
        {
            case 0:
                return "Profile";
            case 1:
                return "User";
            case 2:
                return "Picture";
        }
        return null;
    }

}

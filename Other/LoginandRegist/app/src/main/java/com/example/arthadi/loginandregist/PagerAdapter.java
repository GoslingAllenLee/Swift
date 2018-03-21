package com.example.arthadi.loginandregist;

/**
 * Created by Arthadi on 21/03/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter
{
    int mNumofTabs;

    public PagerAdapter(FragmentManager fm, int NumofTabs)
    {
        super(fm);
        this.mNumofTabs = NumofTabs;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                Login Login = new Login();
                return Login;
            case 1:
                Register Register = new Register();
                return Register;
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return mNumofTabs;
    }
}

package com.example.arthadi.loginandregist.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.fragments.AccountFragment;
import com.example.arthadi.loginandregist.fragments.DailyFragment;
import com.example.arthadi.loginandregist.fragments.MonthlyFragment;
import com.example.arthadi.loginandregist.fragments.WeeklyFragment;

/**
 * Created by faisalrizarakhmat on 10/02/18.
 */
//Acc
public class AccountFragmentAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;

    public AccountFragmentAdapter(FragmentManager fm,int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new DailyFragment();
        }
        else if (position == 1)
        {
            fragment = new WeeklyFragment();
        }
        else if (position == 2)
        {
            fragment = new MonthlyFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Daily";
        }
        else if (position == 1)
        {
            title = "Weekly";
        }
        else if (position == 2)
        {
            title = "Monthly";
        }
        return title;
    }
}

package com.example.arthadi.loginandregist.adapters;

/**
 * Created by faisalrizarakhmat on 29/01/18.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arthadi.loginandregist.fragments.DailyFragment;
import com.example.arthadi.loginandregist.fragments.MonthlyFragment;
import com.example.arthadi.loginandregist.fragments.WeeklyFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new DailyFragment();
            case 1:
                return new WeeklyFragment();
            case 2:
                return new MonthlyFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
package com.example.arthadi.loginandregist;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.arthadi.loginandregist.fragments.ExpenseFragment;
import com.example.arthadi.loginandregist.fragments.IncomeFragment;
import com.example.arthadi.loginandregist.models.Transaction;

import java.util.ArrayList;
import java.util.List;


public class AddTransActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Transaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trans);

        getSupportActionBar().setTitle("Expense");

        mTransaction = getIntent().getParcelableExtra("transaction");

        viewPager = (ViewPager) findViewById(R.id.pager3);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewPager);

        CustomViewPager mViewPager = (CustomViewPager) findViewById(R.id.pager3);
        mViewPager.setPagingEnabled(false);

        if(mTransaction != null)
        {
            if(mTransaction.type.equals("Income"))
            {
                mViewPager.setCurrentItem(1);
            }
            else
            {
                mViewPager.setCurrentItem(0);
            }
        }

    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ExpenseFragment expenseFragment = new ExpenseFragment();
        IncomeFragment incomeFragment = new IncomeFragment();

        if(mTransaction !=null)
        {
            Bundle argument = new Bundle();
            argument.putParcelable("transaction", mTransaction);
            expenseFragment.setArguments(argument);
            incomeFragment.setArguments(argument);
        }


        adapter.addFragment(new ExpenseFragment(), "Expense");
        adapter.addFragment(new IncomeFragment(), "Income");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {

                switch (position)
                {
                    case 0:
                        getSupportActionBar().setTitle("Expense");

                        break;
                    case 1:
                        getSupportActionBar().setTitle("Income");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public Transaction getTransaction()
    {
        return mTransaction;
    }
}

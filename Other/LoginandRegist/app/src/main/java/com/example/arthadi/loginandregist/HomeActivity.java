package com.example.arthadi.loginandregist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.arthadi.loginandregist.fragments.AccountFragment;
import com.example.arthadi.loginandregist.fragments.StatisticFragment;
import com.example.arthadi.loginandregist.fragments.TransactionFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
{
    private static final String TAG = "HomeActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseAuth mAuth;
    private  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Transaction");

        //Geting curr user
        //mAuth = FirebaseAuth.getInstance();
        //final FirebaseUser user = mAuth.getCurrentUser();

//        userID = user.getUid();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    // user auth state is changed - user is null
//                    // launch login activity
//                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                    finish();
//                }
//                // ...
//            }
//        };

        viewPager = (ViewPager) findViewById(R.id.pager2);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionFragment(), "Transaction");
        adapter.addFragment(new StatisticFragment(), "Statistic");
        adapter.addFragment(new Account(), "Account");
        adapter.addFragment(new Setting(), "Setting");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // change your title
                switch (position)
                {
                    case 0:
                        getSupportActionBar().setTitle("Transaction");
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Statistic");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Account");
                        break;
                    case 3:
                        getSupportActionBar().setTitle("Setting");
                        break;
                }
                // inflate menu
                // customize your toolbar
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
}



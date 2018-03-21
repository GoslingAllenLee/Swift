package com.example.arthadi.loginandregist.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.adapters.AccountFragmentAdapter;
import com.example.arthadi.loginandregist.adapters.AccountItemAdapter;
import com.example.arthadi.loginandregist.adapters.PagerAdapter;
import com.example.arthadi.loginandregist.models.Account;
import com.example.arthadi.loginandregist.models.Transaction;
import com.example.arthadi.loginandregist.utils.DateUtil;
import com.example.arthadi.loginandregist.utils.PopupUtil;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yohanes Himawan K on 1/12/2018.
 */

public class AccountFragment extends Fragment implements AdapterView.OnClickListener, AdapterView.OnItemClickListener{


    private AccountItemAdapter accadapter;
    private List<Account> accounts = new ArrayList<Account>();
    //private SortedMap<String, Transaction> orderedTransaction = new TreeMap<String, Transaction>();
    //private SortedSetMultimap<String, Transaction> orderedTransaction = TreeMultimap.
    private OkHttpClient okHttpClient = new OkHttpClient();

    private static String locale;
    ViewPager viewPager;
    AccountFragmentAdapter viewPagerAdapter;
    //private static TabLayout tabLayout;

//    public AccountFragment() {
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//    }

    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.sliding_tabs);
//        viewPagerAdapter = new AccountFragmentAdapter(getActivity().getSupportFragmentManager());
//        viewPager.setAdapter(viewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
        //adapter.notifyDataSetChanged();
        //trxRef.addValueEventListener(valueEventListener);
        //trxRef.orderByChild("transactionDate").addValueEventListener(valueEventListener);
        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //trxRef.orderByChild("uid").equalTo(uid).addValueEventListener(valueEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        //trxRef.removeEventListener(valueEventListener);
        //trxRef.orderByChild("transactionDate").removeEventListener(valueEventListener);
        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //trxRef.orderByChild("uid").equalTo(uid).addValueEventListener(valueEventListener);
    }

    private void loadData() {
        PopupUtil.showLoading(getActivity(), "", "Loading...");
        String url = getString(R.string.base_url) + "transaction/get_all2";
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("AccountFragment", "Error: " + e.getMessage());
                PopupUtil.dismissDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                PopupUtil.dismissDialog();
                Log.d("AccountFragment", "body ====== >" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray data = json.getJSONArray("data");

                    // String lastDay = "";
                    //long totalAmount = 0;
                    // Transaction lastSection = null;
                    Calendar calendar = Calendar.getInstance();
                    accounts.clear();

                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        boolean isHeader = jsonObject.getBoolean("isHeader");
                        long amount = Long.parseLong(jsonObject.getString("amount"));
                        String createdAt = jsonObject.getString("date");

                        long date = 0;

                        try {
                            date = DateUtil.toDate(createdAt).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        calendar.setTimeInMillis(date);

                        String day = DateUtil.format(calendar.getTime());
                        String month = DateUtil.formatMonth(calendar.getTime());

                        Account account = new Account();
                        account.amount = amount;
                        account.isHeader = isHeader;

                        if (isHeader){
                            account.month = month;
                            account.amount = amount;
                            account.date = calendar.get(Calendar.DAY_OF_MONTH);
                            account.day = DateUtil.formatDay(calendar.getTime());
                        }

                        else {
                            int id = Integer.parseInt(jsonObject.getString("id"));
                            int userId = Integer.parseInt(jsonObject.getString("user_id"));

                            String category = jsonObject.getString("category");
                            String subCategory = jsonObject.getString("sub_category");
                            String type = jsonObject.getString("type");
                            String note = jsonObject.getString("note");

                            account.id = id;
                            account.transactionDate = date;
                            account.category = category;
                            account.subCategory = subCategory;
                            account.note = note;
                            account.type = type;
                        }



                        //transaction.uid = uid;


                        // tambahkan ke list
                        accounts.add(account);

                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            accadapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
}

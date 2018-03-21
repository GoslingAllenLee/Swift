package com.example.arthadi.loginandregist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.arthadi.loginandregist.adapters.AccountFragmentAdapter;
import com.example.arthadi.loginandregist.adapters.AccountItemAdapter;
import com.example.arthadi.loginandregist.utils.DateUtil;
import com.example.arthadi.loginandregist.utils.PopupUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Arthadi on 05/04/2017.
 */

public class Account extends Fragment implements AdapterView.OnClickListener, AdapterView.OnItemClickListener {
    //private FirebaseAuth auth;
    private Button btn_logout;
    private DatabaseReference mDatabase;

    //private DatabaseReference trxRef;

    private AccountItemAdapter accadapter;
    private List<com.example.arthadi.loginandregist.models.Account> accounts = new ArrayList<com.example.arthadi.loginandregist.models.Account>();
    //private SortedMap<String, Transaction> orderedTransaction = new TreeMap<String, Transaction>();
    //private SortedSetMultimap<String, Transaction> orderedTransaction = TreeMultimap.
    private OkHttpClient okHttpClient = new OkHttpClient();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    AccountFragmentAdapter viewPagerAdapter;

    public Account() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        //auth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //if the user is not logged in
        //that means current user will return null
//        if(auth.getCurrentUser() == null)
//        {
//            //closing this activity
//            getActivity().finish();
//            //starting login activity
//            startActivity(new Intent(getContext(), LoginActivity.class));
//        }
//        //getting current user
//        FirebaseUser user = auth.getCurrentUser();
        //btn_logout = (Button) rootView.findViewById(R.id.btn_logout);
       // btn_logout.setOnClickListener(this);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.sliding_tabs);
        viewPagerAdapter = new AccountFragmentAdapter(getActivity().getSupportFragmentManager(),3);
        viewPager.setAdapter(viewPagerAdapter);
        //tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                //viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        //logging out the user
//        auth.signOut();
//        //closing activity
//        getActivity().finish();
//        //starting login activity
//        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //trxRef = FirebaseDatabase.getInstance().getReference("Account");


//
//        ListView lv_Acc = (ListView) getView().findViewById(R.id.LvAccount);
//        accadapter = new AccountItemAdapter(getActivity(), accounts);
//        lv_Acc.setAdapter(accadapter);
//        lv_Acc.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onResume() {
        super.onResume();

        //loadData();
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

//    private void loadData() {
//        PopupUtil.showLoading(getActivity(), "", "Loading...");
//        String url = getString(R.string.base_url) + "transaction/get_all2";
//        Request request = new Request.Builder().url(url).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("AccountFragment", "Error: " + e.getMessage());
//                PopupUtil.dismissDialog();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String body = response.body().string();
//                PopupUtil.dismissDialog();
//                Log.d("AccountFragment", "body ====== >" + body);
//
//                try {
//                    JSONObject json = new JSONObject(body);
//                    JSONArray data = json.getJSONArray("data");
//
//                    // String lastDay = "";
//                    //long totalAmount = 0;
//                    // Transaction lastSection = null;
//                    Calendar calendar = Calendar.getInstance();
//                    accounts.clear();
//
//                    for(int i = 0; i < data.length(); i++) {
//                        JSONObject jsonObject = data.getJSONObject(i);
//                        boolean isHeader = jsonObject.getBoolean("isHeader");
//                        long amount = Long.parseLong(jsonObject.getString("amount"));
//                        String createdAt = jsonObject.getString("date");
//
//                        long date = 0;
//
//
//
//                        try {
//                            date = DateUtil.toDate(createdAt).getTime();
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//
//                        calendar.setTimeInMillis(date);
//
//                        String day = DateUtil.formatDay(calendar.getTime());
//                        String month = DateUtil.formatMonth(calendar.getTime());
//
//                        com.example.arthadi.loginandregist.models.Account account = new com.example.arthadi.loginandregist.models.Account();
//                        account.amount = amount;
//                        account.isHeader = isHeader;
//
//                        if (isHeader){
//                            account.month = month;
//                            account.amount = amount;
//                            account.date = calendar.get(Calendar.DAY_OF_MONTH);
//                            account.day =day;
//                        }
//
//                        else {
//                            int id = Integer.parseInt(jsonObject.getString("id"));
//                            int userId = Integer.parseInt(jsonObject.getString("user_id"));
//
//                            String category = jsonObject.getString("category");
//                            String subCategory = jsonObject.getString("sub_category");
//                            String type = jsonObject.getString("type");
//                            String note = jsonObject.getString("note");
//
//                            account.id = id;
//                            account.transactionDate = date;
//                            account.category = category;
//                            account.subCategory = subCategory;
//                            account.note = note;
//                            account.type = type;
//                        }
//
//
//
//                        //transaction.uid = uid;
//
//
//                        // tambahkan ke list
//                        accounts.add(account);
//
//                    }
//
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            accadapter.notifyDataSetChanged();
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

}

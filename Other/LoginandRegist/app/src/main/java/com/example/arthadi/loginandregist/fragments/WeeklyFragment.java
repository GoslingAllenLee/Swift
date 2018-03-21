package com.example.arthadi.loginandregist.fragments;

/**
 * Created by faisalrizarakhmat on 29/01/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arthadi.loginandregist.AddTransActivity;
import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.adapters.TransactionItemAdapter;
import com.example.arthadi.loginandregist.adapters.WeeklyAccountAdapter;
import com.example.arthadi.loginandregist.models.Transaction;
import com.example.arthadi.loginandregist.utils.DateUtil;
import com.example.arthadi.loginandregist.utils.PopupUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.example.arthadi.loginandregist.Constanta.URL_Con;

public class WeeklyFragment extends Fragment implements AdapterView.OnItemClickListener {

    private WeeklyAccountAdapter adapter;
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private OkHttpClient okHttpClient = new OkHttpClient();

    private JSONArray JBweek;
    private JSONArray JBtotal;
    private JSONArray JBmonth;
    private JSONArray JBtype;

    public WeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_daily, container, false);
        View rootView = inflater.inflate(R.layout.fragment_weekly, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = (ListView) getView().findViewById(R.id.list);
        //adapter = new WeeklyAccountAdapter(transactions);
        //listView.setAdapter(adapter);
        //listView.setOnItemClickListener(this);
        //loadData();
        loadDataWeekly();
    }

    @Override
    public void onResume() {
        super.onResume();
        //loadData();
        loadDataWeekly();
    }

    @Override
    public void onPause() {
        super.onPause();
        //trxRef.removeEventListener(valueEventListener);
        //trxRef.orderByChild("transactionDate").removeEventListener(valueEventListener);
        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //trxRef.orderByChild("uid").equalTo(uid).addValueEventListener(valueEventListener);
    }

    private void loadDataWeekly(){
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        final Map<String,String> param = new HashMap<>();
        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, URL_Con, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject JBJ = null;
                Log.d(TAG, "URL : "+URL_Con);
                try {
                    JBJ = new JSONObject(response);
                    JBweek = JBJ.getJSONArray("week");
                    JBtotal = JBJ.getJSONArray("total");
                    JBmonth = JBJ.getJSONArray("month");
                    JBtype = JBJ.getJSONArray("type");
                    Log.d(TAG, "onResponse jb: "+JBweek);
//                    if (JBweek != null) {
//                        int len = JBweek.length();
//                        yDataincome = new float[len];
//                        xDataincome = new String[len];
//                        for (int i=0;i<len;i++){
//                            yDataincome[i] = Float.parseFloat(JBValue.get(i).toString());
//                            xDataincome[i] = JBH.get(i).toString();
//                        }
//                    }

                    //Toast.makeText(getActivity(), JBJ.getString("status"), Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                //dialog.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                param.put("Content-Type","application/x-www-form-urlencoded");

                return param;
            }

            @Override
            protected  Map<String,String> getParams() throws AuthFailureError{
                param.put("type", "weekly");
                //param.put("passwordandroid", txtPassword.getText().toString().trim());
                return param;
            }
        };
        rq.add(sr);
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
                Log.e("TransactionFragment", "Error: " + e.getMessage());
                PopupUtil.dismissDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                PopupUtil.dismissDialog();
                Log.d("TransactionFragment", "body ====== >" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray data = json.getJSONArray("data");

                    // String lastDay = "";
                    //long totalAmount = 0;
                    // Transaction lastSection = null;
                    Calendar calendar = Calendar.getInstance();
                    transactions.clear();

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

                        Transaction transaction = new Transaction();
                        transaction.amount = amount;
                        transaction.isHeader = isHeader;

                        if (isHeader){
                            transaction.month = month;
                            transaction.amount = amount;
                            transaction.date = calendar.get(Calendar.DAY_OF_MONTH);
                            transaction.day = DateUtil.formatDay(calendar.getTime());
                        }

                        else {
                            int id = Integer.parseInt(jsonObject.getString("id"));
                            int userId = Integer.parseInt(jsonObject.getString("user_id"));

                            String category = jsonObject.getString("category");
                            String subCategory = jsonObject.getString("sub_category");
                            String type = jsonObject.getString("type");
                            String note = jsonObject.getString("note");

                            transaction.id = id;
                            transaction.transactionDate = date;
                            transaction.category = category;
                            transaction.subCategory = subCategory;
                            transaction.note = note;
                            transaction.type = type;
                        }



                        //transaction.uid = uid;


                        // tambahkan ke list
                        transactions.add(transaction);

                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Transaction transaction = transactions.get(position);

        Intent intent = new Intent(getActivity(), AddTransActivity.class);
        intent.putExtra("transaction",transaction);
        startActivity(intent);
    }
}

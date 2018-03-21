package com.example.arthadi.loginandregist.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arthadi.loginandregist.DetailStatisticActivity;
import com.example.arthadi.loginandregist.HomeActivity;
import com.example.arthadi.loginandregist.Login;
import com.example.arthadi.loginandregist.LoginActivity;
import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.Register;
import com.example.arthadi.loginandregist.adapters.StatisticAdapter;
import com.example.arthadi.loginandregist.models.StatisticValueChart;
import com.example.arthadi.loginandregist.models.Transaction;
import com.example.arthadi.loginandregist.utils.PopupUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.arthadi.loginandregist.Constanta.URL_Con;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends Fragment {
    private PieChart pieChart;
    private Spinner spn_choose;
    private float[] yData;
    private String[] xData;
    private float[] yDataincome;
    private String[] xDataincome;

    private JSONArray JBH;
    private JSONArray JBValue;
    private ArrayList<String> list;
    private ArrayList<Float> listvalue;

    private List<Transaction> transactions = new ArrayList<Transaction>();
    private OkHttpClient okHttpClient = new OkHttpClient();

    private List<StatisticValueChart> stchart = new ArrayList<>();
    private RecyclerView recyclerView;
    private StatisticAdapter mAdapter;
    StatisticValueChart listvaluelist;

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistic, container, false);

        pieChart = (PieChart) v.findViewById(R.id.Piechart);
        spn_choose = (Spinner) v.findViewById(R.id.spinner_chart); //Milih income atau expense

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        mAdapter = new StatisticAdapter(stchart);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        StatisticValueChart movie = new StatisticValueChart("Gaji", "4000000");
//        movieList.add(movie);
//
//        movie = new StatisticValueChart("Bonus", "2500000");
//        movieList.add(movie);
//
//
//
//        mAdapter.notifyDataSetChanged();

        // Create an ArrayAdapter using the string array and a default spinner
//        ArrayAdapter<CharSequence>spinner_aa =
//                ArrayAdapter.createFromResource(getActivity(), R.array.charts,android.R.layout.simple_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        spinner_aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        spn_choose.setAdapter(spinner_aa);



        //int selected_spn = spn_choose.getSelectedItemPosition(); //seleceted_spn di oper ke addDataset

        loadDataIncome();
        loadDataExpense();

        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterTextSize(12);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(14);
        //More options just check out the documentation!

        spn_choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        addDataSetExpense();
                        break;
                    case 1:
                        addDataSetIncome();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //add legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
//        legend.setXEntrySpace(7);
//        legend.setYEntrySpace(5);




//        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h)
//            {
//                String text =String.valueOf((int) h.getX());
//                Toast.makeText(getActivity(), "click chart " + text, Toast.LENGTH_SHORT).show();
//                if(e == null)
//                    return;
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

        return v;
    }


    public ArrayList<String> queryXdata()
    {
        final ArrayList<String> data_category = new ArrayList<String>();

        PopupUtil.showLoading(getActivity(), "", "Loading...");
        String url = getString(R.string.base_url) + "transaction/get_expense";
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("StatisticFragment", "Error: " + e.getMessage());
                PopupUtil.dismissDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                PopupUtil.dismissDialog();
                Log.d("StatisticFragment", "body ====== >" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray data = json.getJSONArray("data");

                    for (int i=0;i < data.length() ; i++ ){

                        data_category.add(String.valueOf(data.getJSONObject(Integer.parseInt("category"))));
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return data_category;
    }


    public ArrayList<Float> queryYdata()
    {
        final ArrayList<Integer> data_amount = new ArrayList<Integer>();
        final ArrayList<Float> newFloat = new ArrayList<Float>();

        PopupUtil.showLoading(getActivity(), "", "Loading...");
        String url = getString(R.string.base_url) + "transaction/get_expense";
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("StatisticFragment", "Error: " + e.getMessage());
                PopupUtil.dismissDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                PopupUtil.dismissDialog();
                Log.d("StatisticFragment", "body ====== >" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray data = json.getJSONArray("data");

                    for (int i=0;i < data.length() ; i++ ){

                        data_amount.add(Integer.valueOf(String.valueOf(data.getJSONObject(Integer.parseInt("amount")))));
                          newFloat.add((float) Float.parseFloat(data_amount.toString()));
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return newFloat;
    }

     private void loadDataExpense(){
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        final Map<String,String> param = new HashMap<>();
        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, URL_Con, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject JBJ = null;
                try {
                    JBJ = new JSONObject(response);
                    JBH = JBJ.getJSONArray("data");
                    JBValue = JBJ.getJSONArray("value");
                    if (JBH != null) {
                        int len = JBH.length();
                        yData = new float[len];
                        xData = new String[len];
                        for (int i=0;i<len;i++){
                            yData[i] = Float.parseFloat(JBValue.get(i).toString());
                            xData[i] = JBH.get(i).toString();
                        }
                    }

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
                param.put("type", "expense");
                //param.put("passwordandroid", txtPassword.getText().toString().trim());
                return param;
            }
        };
        rq.add(sr);
    }

    private void addDataSetExpense()
    {
        loadDataExpense();
        stchart.clear();
        //yData = new float[]{10, 12, 9, 8, 50,60};
        //xData = new String[]{"Bill and utility","Bonus","Food","Health","Salary","Transportation"};
        pieChart.setCenterText("Expense Chart");
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0 ; i<yData.length ; i++)
        {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
            listvaluelist = new StatisticValueChart(xData[i], Float.toString(yData[i]));
            stchart.add(listvaluelist);
            //listvaluelist = new StatisticValueChart("Gaji", "4000000");

                    //Toast.makeText(getActivity(), xData[i], Toast.LENGTH_SHORT).show();
            //Log.d("list A :" , xData[i]);
        }
        mAdapter.notifyDataSetChanged();

//        StatisticValueChart movie = new StatisticValueChart("Gaji", "4000000");
//        movieList.add(movie);
//
//        movie = new StatisticValueChart("Bonus", "2500000");
//        movieList.add(movie);
//
//
//
//        mAdapter.notifyDataSetChanged();

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Expense");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setSelectionShift(5);
        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.animateY(1000);
        pieChart.setData(pieData);
//      pieChart.invalidate();

        pieChart.notifyDataSetChanged();
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                String text =String.valueOf((int) h.getX());
                //Toast.makeText(getActivity(), "click chart " + xData[(int) h.getX()], Toast.LENGTH_SHORT).show();
                if(e == null) {
                    return;
                }else{
                    Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                    intent.putExtra("category", xData[(int) h.getX()]);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void loadDataIncome(){
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        final Map<String,String> param = new HashMap<>();
        StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST, URL_Con, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject JBJ = null;
                try {
                    JBJ = new JSONObject(response);
                    JBH = JBJ.getJSONArray("data");
                    JBValue = JBJ.getJSONArray("value");
                    if (JBH != null) {
                        int len = JBH.length();
                        yDataincome = new float[len];
                        xDataincome = new String[len];
                        for (int i=0;i<len;i++){
                            yDataincome[i] = Float.parseFloat(JBValue.get(i).toString());
                            xDataincome[i] = JBH.get(i).toString();
                        }
                    }

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
                param.put("type", "income");
                //param.put("passwordandroid", txtPassword.getText().toString().trim());
                return param;
            }
        };
        rq.add(sr);
    }

    private void addDataSetIncome()
    {
        loadDataIncome();
        Description description = new Description();
        description.setTextSize(11);
        description.setText("Percentage of Income");
        pieChart.setDescription(description);

        //yDataincome = new float[]{18, 10, 17, 11};
        //xDataincome = new String[] {"Salary", "Allowance", "Bonus", "Other"};
        pieChart.setCenterText("Income Chart");
        stchart.clear();
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0 ; i<yDataincome.length ; i++)
        {
            pieEntries.add(new PieEntry(yDataincome[i], xDataincome[i]));
            listvaluelist = new StatisticValueChart(xDataincome[i], Float.toString(yDataincome[i]));
            stchart.add(listvaluelist);
        }
        mAdapter.notifyDataSetChanged();

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Income");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setSelectionShift(5);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);

        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.animateY(1000);
        pieChart.setData(pieData);
//          pieChart.invalidate();

        pieChart.notifyDataSetChanged();
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                String text =String.valueOf((int) h.getX());
                //Toast.makeText(getActivity(), "click chart " + xDataincome[(int) h.getX()], Toast.LENGTH_SHORT).show();
                if(e == null) {
                    return;
                }else{
                    Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                    intent.putExtra("category", xDataincome[(int) h.getX()]);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

}


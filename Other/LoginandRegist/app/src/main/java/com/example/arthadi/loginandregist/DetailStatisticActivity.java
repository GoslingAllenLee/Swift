package com.example.arthadi.loginandregist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arthadi.loginandregist.adapters.StatisticAdapter;
import com.example.arthadi.loginandregist.models.StatisticValueChart;
import com.example.arthadi.loginandregist.models.Transaction;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

import static com.example.arthadi.loginandregist.Constanta.URL_Con;


public class DetailStatisticActivity extends AppCompatActivity
{
    private PieChart pieChart;
    private Spinner spn_choose;
    private float[] yData;
    private String[] xData;

    private JSONArray JBH;
    private JSONArray JBValue;

    private List<StatisticValueChart> stchart = new ArrayList<>();
    private RecyclerView recyclerView;
    private StatisticAdapter mAdapter;
    StatisticValueChart listvaluelist;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.fragment_statistic);

        Bundle extras = getIntent().getExtras();
        type = extras.getString("category");

        pieChart = (PieChart) findViewById(R.id.Piechart);
        spn_choose = (Spinner) findViewById(R.id.spinner_chart);
        spn_choose.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StatisticAdapter(stchart);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        loadData();

    }

    private void loadData(){
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
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
                        Description description = new Description();
                        description.setTextSize(11);
                        description.setText("Percentage of "+type);
                        pieChart.setDescription(description);

                        pieChart.setCenterText(type+" Chart");
                        stchart.clear();
                        List<PieEntry> pieEntries = new ArrayList<>();

                        int len = JBH.length();
                        yData = new float[len];
                        xData = new String[len];
                        for (int i=0;i<len;i++){
                            yData[i] = Float.parseFloat(JBValue.get(i).toString());
                            xData[i] = JBH.get(i).toString();
                            Toast.makeText(getApplicationContext(), String.valueOf(xData[i]), Toast.LENGTH_SHORT).show();
                            pieEntries.add(new PieEntry(yData[i], xData[i]));
                            listvaluelist = new StatisticValueChart(xData[i], Float.toString(yData[i]));
                            stchart.add(listvaluelist);
                        }
                        mAdapter.notifyDataSetChanged();
                        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Income");
                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);
                        pieDataSet.setSelectionShift(5);
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.GRAY);
                        colors.add(Color.BLUE);
                        colors.add(Color.RED);
                        colors.add(Color.GREEN);
                        colors.add(Color.CYAN);
                        colors.add(Color.YELLOW);

                        pieDataSet.setColors(colors);
                        PieData pieData = new PieData(pieDataSet);
                        pieData.setValueFormatter(new PercentFormatter());
                        pieChart.animateY(1000);
                        pieChart.setData(pieData);
                        pieChart.notifyDataSetChanged();
                        pieChart.invalidate();
                    }

                    //Toast.makeText(getApplicationContext(), xData.length, Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                //dialog.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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
                param.put("type", type);
                //param.put("passwordandroid", txtPassword.getText().toString().trim());
                return param;
            }
        };
        rq.add(sr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

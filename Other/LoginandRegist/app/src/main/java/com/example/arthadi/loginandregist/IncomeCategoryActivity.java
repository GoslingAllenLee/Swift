package com.example.arthadi.loginandregist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Arthadi on 08/05/2017.
 */

public class IncomeCategoryActivity extends AppCompatActivity
{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        final Intent i = new Intent(getApplicationContext(), AddTransActivity.class);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpendableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //expanding group
        expandAllGroup();

        //hit back button give blank
        i.putExtra("category","");
        setResult(RESULT_OK);
        setResult(321,i);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                String get = listDataHeader.get(groupPosition);

                i.putExtra("category",get);
                setResult(RESULT_OK);
                setResult(321,i);
                finish();
                return true;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                String get = listDataHeader.get(groupPosition)
                        + " / "
                        + listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition);

                i.putExtra("category",get);
                setResult(RESULT_OK);
                setResult(321,i);
                finish();
                return false;
            }
        });
    }

    private void expandAllGroup()
    {
        expListView.expandGroup(0);
        expListView.expandGroup(1);
        expListView.expandGroup(2);
        expListView.expandGroup(3);
        expListView.expandGroup(4);
    }

    /*
	 * Preparing the list data
	 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Salary");
        listDataHeader.add("Allowance");
        listDataHeader.add("Petty Cash");
        listDataHeader.add("Bonus");
        listDataHeader.add("Other");


        // Adding child data
        List<String> Salary = new ArrayList<String>();
        List<String> Bonus = new ArrayList<String>();
        List<String> Other = new ArrayList<String>();
        List<String> Allowance = new ArrayList<String>();
        List<String> PettyCash = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), Salary); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Allowance);
        listDataChild.put(listDataHeader.get(2), PettyCash);
        listDataChild.put(listDataHeader.get(3), Bonus);
        listDataChild.put(listDataHeader.get(4), Other);
    }
}

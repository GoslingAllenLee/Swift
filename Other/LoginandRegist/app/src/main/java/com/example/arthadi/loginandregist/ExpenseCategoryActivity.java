package com.example.arthadi.loginandregist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpenseCategoryActivity extends AppCompatActivity
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
//                Toast.makeText(getApplicationContext(),
//                        "Group Clicked " + listDataHeader.get(groupPosition),
//                        Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
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
        expListView.expandGroup(5);
        expListView.expandGroup(6);
        expListView.expandGroup(7);
        expListView.expandGroup(8);
        expListView.expandGroup(9);
        expListView.expandGroup(10);
        expListView.expandGroup(11);

    }

    /*
	 * Preparing the list data
	 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Food");
        listDataHeader.add("Transportasion");
        listDataHeader.add("Health");
        listDataHeader.add("Education");
        listDataHeader.add("Bill and utility");
        listDataHeader.add("Entertaiment");
        listDataHeader.add("Shopping");
        listDataHeader.add("Travel");
        listDataHeader.add("Family");
        listDataHeader.add("Gift And Donation");
        listDataHeader.add("Withdrawal");
        listDataHeader.add("Other");

        // Adding child data
        List<String> Food = new ArrayList<String>();
        Food.add("Diner");
        Food.add("Lunch");
        Food.add("Eating Out");
        Food.add("Beverages");

        List<String> Transportasion = new ArrayList<String>();
        Transportasion.add("Gas");
        Transportasion.add("Parking fee");
        Transportasion.add("Train");
        Transportasion.add("Taxi");
        Transportasion.add("Car");

        List<String> Health = new ArrayList<String>();
        Health.add("Hospital");
        Health.add("Personal Care");
        Health.add("Medicine");
        Health.add("Doctor");

        List<String> Education = new ArrayList<String>();
        Education.add("Text book");
        Education.add("School Suplies");
        Education.add("Schooling");

        List<String> Shopping = new ArrayList<String>();
        Shopping.add("Accessories");
        Shopping.add("Electonic");
        Shopping.add("clothes");

        List<String> Entertaiment = new ArrayList<String>();
        Entertaiment.add("Games");
        Entertaiment.add("Movie");

        List<String> Family = new ArrayList<String>();
        Family.add("Kids And Baby");
        Family.add("Home Reparation");
        Family.add("Home Service");
        Family.add("Pet");


        List<String> GiftAndDonation = new ArrayList<String>();
        GiftAndDonation.add("Wedding");
        GiftAndDonation.add("Funeral");
        GiftAndDonation.add("Charity");

        List<String> BillAndUtility = new ArrayList<String>();
        BillAndUtility.add("Water");
        BillAndUtility.add("Electricity");
        BillAndUtility.add("Phone");
        BillAndUtility.add("Internet");
        BillAndUtility.add("Television");
        BillAndUtility.add("Rent");


        List<String> Travel = new ArrayList<String>();
        List<String> Withdrawal = new ArrayList<String>();
        List<String> Other = new ArrayList<String>();

        listDataChild.put(listDataHeader.get(0), Food); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Transportasion);
        listDataChild.put(listDataHeader.get(2), Health);
        listDataChild.put(listDataHeader.get(3), Education);
        listDataChild.put(listDataHeader.get(4), BillAndUtility);
        listDataChild.put(listDataHeader.get(5), Entertaiment);
        listDataChild.put(listDataHeader.get(6), Shopping);
        listDataChild.put(listDataHeader.get(7), Travel);
        listDataChild.put(listDataHeader.get(8), Family);
        listDataChild.put(listDataHeader.get(9), GiftAndDonation);
        listDataChild.put(listDataHeader.get(10), Withdrawal);
        listDataChild.put(listDataHeader.get(11), Other);
    }
}

package com.example.arthadi.loginandregist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Arthadi on 05/04/2017.
 */

public class Statistik extends Fragment
{



    public Statistik() {
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
        View rootView = inflater.inflate(R.layout.statistik, container, false);
       // ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Statistik");
        return rootView;
    }
}

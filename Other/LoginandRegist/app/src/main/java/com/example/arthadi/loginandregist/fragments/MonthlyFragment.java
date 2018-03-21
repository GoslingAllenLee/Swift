package com.example.arthadi.loginandregist.fragments;

/**
 * Created by faisalrizarakhmat on 29/01/18.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arthadi.loginandregist.R;

public class MonthlyFragment extends Fragment {

    public MonthlyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }
}

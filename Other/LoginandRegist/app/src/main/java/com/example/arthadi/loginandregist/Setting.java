package com.example.arthadi.loginandregist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Arthadi on 05/04/2017.
 */

public class Setting extends Fragment implements View.OnClickListener
{
    //private FirebaseAuth auth;
    private Button btn_logout;
    //private DatabaseReference mDatabase;

    public Setting() {
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
        View rootView = inflater.inflate(R.layout.setting, container, false);

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
        btn_logout = (Button) rootView.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        //logging out the user
//        auth.signOut();
        //closing activity
        getActivity().finish();
        //starting login activity
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}

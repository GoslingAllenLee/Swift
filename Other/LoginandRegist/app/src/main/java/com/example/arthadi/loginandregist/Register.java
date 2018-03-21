package com.example.arthadi.loginandregist;

/**
 * Created by Arthadi on 21/03/2017.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;


public class Register extends Fragment implements View.OnClickListener
{
    private static final String TAG = "Register";
    private EditText inputemail, inputpwd, inputCpwd, inputname;
    Button btn_submit;
    private static TextView dob;
    //Firebase------------------------------------
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener AuthListener;
    private DatabaseReference mDatabase;
    //--------------------------------------------
    private ProgressDialog progressDialog;
    private String userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.regist, container, false);
        //initialisasion firebase
        auth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        //if the objects getcurrentuser method is not null
//        AuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
        //means user is already logged in

        if(auth.getCurrentUser() != null)
        {
            //close this activity
            getActivity().finish();
            //opening profile activity
            startActivity(new Intent(getContext(), HomeActivity.class));
        }
        dob = (TextView) rootView.findViewById(R.id.dob);
        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        inputemail = (EditText) rootView.findViewById(R.id.edit_Email);
        inputpwd = (EditText) rootView.findViewById(R.id.edit_pwd);
        inputCpwd = (EditText) rootView.findViewById(R.id.edit_Cpwd);
        inputname = (EditText) rootView.findViewById(R.id.edit_Name);
        dob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0)
            {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(),"DatePicker");
            }
        });

        btn_submit.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        registerUser();
    }

    public void registerUser()
    {

        //getting email and password from edit texts
        final String email = inputemail.getText().toString().trim();
        final String password  = inputpwd.getText().toString().trim();
        String Cpassword  = inputCpwd.getText().toString().trim();
        final String name = inputname.getText().toString().trim();
        final String inputDOB = dob.getText().toString().trim();

        progressDialog = new ProgressDialog(getActivity());


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getContext(),"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(!TextUtils.equals(password, Cpassword)){
            Toast.makeText(getContext(),"Password Don't Match",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(name)){
            Toast.makeText(getContext(),"Please enter Your Name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(inputDOB)){
            Toast.makeText(getContext(),"Please Your Date of Birth ",Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful())
                        {
                            getActivity().finish();
                            //Get UserID After Regist
                            FirebaseUser user = auth.getCurrentUser();
                            userID = user.getUid();
                            //Put UserInfo to Database
                            HashMap<String, String> RegisData= new HashMap<String, String>();
                            RegisData.put("Name",name);
                            RegisData.put("Email",email);
                            RegisData.put("Password",password);
                            RegisData.put("DOB",inputDOB);
                            mDatabase.child("users").child(userID).setValue(RegisData);
                            startActivity(new Intent(getContext(), HomeActivity.class));
                        }
                        else
                        {
                            //display some message here
                            Toast.makeText(getActivity(),"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }




    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }
        public void onDateSet(DatePicker view, int yy, int mm, int dd)
        {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day)
        {
            dob.setText(day+"/"+month+"/"+year);
        }

    }

}

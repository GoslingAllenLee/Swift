package com.example.arthadi.loginandregist.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arthadi.loginandregist.AddTransActivity;
import com.example.arthadi.loginandregist.Calculator;
import com.example.arthadi.loginandregist.IncomeCategoryActivity;
import com.example.arthadi.loginandregist.LoginActivity;
import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.listeners.DatePickerListener;
import com.example.arthadi.loginandregist.models.Transaction;
import com.example.arthadi.loginandregist.utils.DateUtil;
import com.example.arthadi.loginandregist.utils.PopupUtil;
import com.example.arthadi.loginandregist.utils.ServerUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Arthadi on 07/05/2017.
 */

public class IncomeFragment extends Fragment implements DatePickerListener
{
    private TextView tvDOB;
    private TextView tvCategory, tvAmount;
    private SimpleDateFormat dateFormatter;
    private EditText edtNotes;
    private Button btn_save;
    private Button btnDelete;
    private DatePickerDialog datePickerDialog;
    //firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private long mAmount;

    private Transaction mTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTransaction = ((AddTransActivity) getActivity()).getTransaction();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        //Geting curr user
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
                // ...
            }
        };

        tvDOB = (TextView) getView().findViewById(R.id.dob_trans);
        tvCategory = (TextView) getView().findViewById(R.id.category_trans);
        tvAmount = (TextView) getView().findViewById(R.id.amount_trans);
        edtNotes = (EditText) getView().findViewById(R.id.notes_trans);
        btn_save = (Button) getView().findViewById(R.id.btnSave);
        btnDelete = (Button) getView().findViewById(R.id.btnDelete);

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // DialogFragment newFragment = new IncomeFragment.SelectDateFragment();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "DatePicker");
                datePickerFragment.setListener(IncomeFragment.this);
            }
        });

        tvAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Calculator.class);
                startActivityForResult(i, 123);

            }
        });

        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), IncomeCategoryActivity.class);
                startActivityForResult(i, 321);
            }
        });


        if (mTransaction != null && mTransaction.type.equals("Income")) {
            tvDOB.setText(DateUtil.format(new Date(mTransaction.transactionDate)));
            tvCategory.setText(mTransaction.category);
            tvAmount.setText("" + mTransaction.amount);
            edtNotes.setText(mTransaction.note);
            btnDelete.setVisibility(View.VISIBLE);
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DOB = tvDOB.getText().toString().trim();
                String Category = tvCategory.getText().toString().trim();
                String Amount = tvAmount.getText().toString().trim();
                String notes = edtNotes.getText().toString().trim();

                String[] dates = DOB.split("-");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[0]));
                calendar.set(Calendar.MONTH, Integer.parseInt(dates[1]) - 1);
                calendar.set(Calendar.YEAR, Integer.parseInt(dates[2]));
                Log.d("IncomeFragment", dates[0] + "-" + dates[1] + "-" + dates[2]);

                String[] categories = Category.split("/");

                Transaction transaction;

                if (mTransaction != null) {
                    transaction = mTransaction;
                } else {
                    transaction = new Transaction();
                }
                transaction.uid = userID;
                transaction.transactionDate = calendar.getTimeInMillis();
                transaction.category = categories[0].trim();

                if (categories.length > 1) {
                    transaction.subCategory = categories[1].trim();
                }

                transaction.note = notes;
                transaction.amount = mAmount;
                transaction.type = "Income";

                if (mTransaction != null) {
                    transaction.id = mTransaction.id;
                }

                //mDatabase.child("Transaction").push().setValue(transaction);

                /*HashMap<String, String> RegisData= new HashMap<String, String>();
                //RegisData.put("TransactionDate", calendar.getTime());
                RegisData.put("Category",Categoty);
                RegisData.put("Amount",Amount);
                RegisData.put("Notes",notes);
                RegisData.put("Type", "Expense");
                mDatabase.child("users").child(userID).child("Transaction")
                        .push().setValue(RegisData);*/

                //getActivity().finish();
                ServerUtil.saveTrx(getActivity(), transaction, new ServerUtil.ResponseListener() {
                    @Override
                    public void onFailure(String message) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PopupUtil.showMsg(getActivity(), "Error saving transaction", PopupUtil.SHORT);
                            }
                        });
                    }

                    @Override
                    public void onResponse(final boolean success, final String message) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PopupUtil.showMsg(getActivity(), message, PopupUtil.SHORT);

                                if (success) {
                                    getActivity().finish();
                                }
                            }
                        });
                    }

                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTransaction != null) {
                    ServerUtil.deleteTrx(getActivity(), mTransaction, new ServerUtil.ResponseListener() {
                        @Override
                        public void onFailure(String message) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    PopupUtil.showMsg(getActivity(), "Error saving transaction", PopupUtil.SHORT);
                                }
                            });
                        }

                        @Override
                        public void onResponse(final boolean success, final String message) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    PopupUtil.showMsg(getActivity(), message, PopupUtil.SHORT);

                                    if (success) {
                                        getActivity().finish();
                                    }
                                }
                            });
                        }

                    });
                }
            }
        });
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123)
        {
            String i = data.getStringExtra("key");
            tvAmount.setText("Rp. "+ i);

            try {
                mAmount = Long.parseLong(i);
            }
            catch(Exception e) {

            }
        }

        if (requestCode == 321)
        {
            String j = data.getStringExtra("category");
            tvCategory.setText(j);
        }

    }

    @Override
    public void onDateSet(int year, int month, int day) {
        tvDOB.setText(String.format("%d-%d-%d", day, month, year));
    }
}

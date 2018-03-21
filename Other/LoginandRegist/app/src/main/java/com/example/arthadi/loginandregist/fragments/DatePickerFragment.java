package com.example.arthadi.loginandregist.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.example.arthadi.loginandregist.listeners.DatePickerListener;

import java.util.Calendar;

/**
 * Created by Yohanes Himawan K on 6/20/2017.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    private DatePickerListener listener;

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
        if(listener != null){
            listener.onDateSet(yy , mm+1 , dd);
        }
    }

    public void setListener(DatePickerListener listener) {
        this.listener = listener;
    }

}

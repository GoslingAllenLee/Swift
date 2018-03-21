package com.example.arthadi.loginandregist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.models.Account;
import com.example.arthadi.loginandregist.models.Transaction;
import com.example.arthadi.loginandregist.utils.CurrencyUtil;
import com.example.arthadi.loginandregist.utils.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Yohanes Himawan K on 1/12/2018.
 */

public class AccountItemAdapter extends ArrayAdapter<Account>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater layoutInflater;

    public AccountItemAdapter(@NonNull Context context, @NonNull List<Account> objects) {
        super(context, R.layout.item_account, objects);


        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Account account = getItem(position);

        if(account.isHeader){
            return TYPE_HEADER;
        }
        else{
            return TYPE_ITEM;
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);

        if(view == null) {
            if (type == TYPE_HEADER) {
                view = layoutInflater.inflate(R.layout.section_account, null);
            } else {
                view = layoutInflater.inflate(R.layout.item_account, null);
            }
        }
        // ambil data dari list
        Account account = getItem(position);

        if(type == TYPE_HEADER){
            TextView trxDateTextView = (TextView) view.findViewById(R.id.trx_dateAcc);
            TextView trxMonthTextView = (TextView) view.findViewById(R.id.trx_monthAcc);
            TextView trxDayTextView = (TextView) view.findViewById(R.id.trx_dayAcc);
            TextView trxAmountTextView = (TextView) view.findViewById(R.id.trx_amountAcc);

            trxDateTextView.setText("" + account.date);
            trxMonthTextView.setText(account.month);
            trxDayTextView.setText(account.day);
            trxAmountTextView.setText(CurrencyUtil.formatIdr(account.amount));
        }
        else{
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            TextView trxDateTextView = (TextView) view.findViewById(R.id.trx_dateAcc);
            TextView trxCatTextView = (TextView) view.findViewById(R.id.trx_catAcc);
            TextView trxAmountTextView = (TextView) view.findViewById(R.id.trx_amountAcc);



            Date date = new Date(account.transactionDate);
            String formattedDate = DateUtil.format(date);

            // apply data ke view
            trxDateTextView.setText(formattedDate);

            if(account.type.equals("Income")) {
                imageView.setImageResource(R.drawable.ic_get_app);
                trxAmountTextView.setTextColor(Color.parseColor("#38a1f4"));
            }
            else {
                imageView.setImageResource(R.drawable.ic_transaction);
                trxAmountTextView.setTextColor(Color.parseColor("#ff6734"));
            }

            if(account.subCategory != null) {
                trxCatTextView.setText(account.category + "/" + account.subCategory);
            }
            else {
                trxCatTextView.setText(account.category);
            }

            trxAmountTextView.setText(CurrencyUtil.formatIdr(account.amount));

        }

        // ambil view dari layout

        return view;
    }
}

package com.example.arthadi.loginandregist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.models.Transaction;
import com.example.arthadi.loginandregist.utils.CurrencyUtil;
import com.example.arthadi.loginandregist.utils.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by blastocode on 6/10/17.
 */

public class TransactionItemAdapter extends ArrayAdapter<Transaction> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater layoutInflater;

    public TransactionItemAdapter(@NonNull Context context, @NonNull List<Transaction> objects) {
        super(context, R.layout.item_transaction, objects);


        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Transaction transaction = getItem(position);

        if(transaction.isHeader){
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
                view = layoutInflater.inflate(R.layout.section_transaction, null);
            } else {
                view = layoutInflater.inflate(R.layout.item_transaction, null);
            }
        }
        // ambil data dari list
        Transaction transaction = getItem(position);

        if(type == TYPE_HEADER){
            TextView trxDateTextView = (TextView) view.findViewById(R.id.trx_date);
            TextView trxMonthTextView = (TextView) view.findViewById(R.id.trx_month);
            TextView trxDayTextView = (TextView) view.findViewById(R.id.trx_day);
            TextView trxAmountTextView = (TextView) view.findViewById(R.id.trx_amount);

            trxDateTextView.setText("" + transaction.date);
            trxMonthTextView.setText(transaction.month);
            trxDayTextView.setText(transaction.day);
            trxAmountTextView.setText(CurrencyUtil.formatIdr(transaction.amount));
        }
        else{
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            TextView trxDateTextView = (TextView) view.findViewById(R.id.trx_date);
            TextView trxCatTextView = (TextView) view.findViewById(R.id.trx_cat);
            TextView trxNoteTextView = (TextView) view.findViewById(R.id.trx_note);
            TextView trxAmountTextView = (TextView) view.findViewById(R.id.trx_amount);



            Date date = new Date(transaction.transactionDate);
            String formattedDate = DateUtil.format(date);

            // apply data ke view
            trxDateTextView.setText(formattedDate);

            if(transaction.type.equals("Income")) {
                imageView.setImageResource(R.drawable.ic_get_app);
                trxAmountTextView.setTextColor(Color.parseColor("#38a1f4"));
            }
            else {
                imageView.setImageResource(R.drawable.ic_transaction);
                trxAmountTextView.setTextColor(Color.parseColor("#ff6734"));
            }

            if(transaction.subCategory != null) {
                trxCatTextView.setText(transaction.category + "/" + transaction.subCategory);
            }
            else {
                trxCatTextView.setText(transaction.category);
            }

            trxNoteTextView.setText(transaction.note);
            trxAmountTextView.setText(CurrencyUtil.formatIdr(transaction.amount));

        }

        // ambil view dari layout

        return view;
    }
}

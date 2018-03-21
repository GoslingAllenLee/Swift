package com.example.arthadi.loginandregist.adapters;

/**
 * Created by faisalrizarakhmat on 27/01/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.models.StatisticValueChart;

import java.util.List;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.MyViewHolder> {

    private List<StatisticValueChart> statisticList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            amount = (TextView) view.findViewById(R.id.amount);

        }
    }


    public StatisticAdapter(List<StatisticValueChart> statisticList) {
        this.statisticList = statisticList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StatisticValueChart movie = statisticList.get(position);
        holder.name.setText(movie.getName());
        holder.amount.setText(movie.getAmount());
    }

    @Override
    public int getItemCount() {
        return statisticList.size();
    }
}

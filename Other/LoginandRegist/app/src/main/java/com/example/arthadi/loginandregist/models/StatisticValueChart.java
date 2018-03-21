package com.example.arthadi.loginandregist.models;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by faisalrizarakhmat on 27/01/18.
 */

public class StatisticValueChart {

    private String name, amount;

    public StatisticValueChart() {
    }

    public StatisticValueChart(String name, String amount) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        this.name = name;
        this.amount = formatRupiah.format((double) Double.parseDouble(amount));
        //this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}

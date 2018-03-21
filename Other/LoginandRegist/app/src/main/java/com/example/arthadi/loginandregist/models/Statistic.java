package com.example.arthadi.loginandregist.models;

/**
 * Created by Yohanes Himawan K on 1/12/2018.
 */

public class Statistic {

    public Statistic(int amount, String category) {
        this.setAmount(amount);
        this.setCategory(category); ;

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    int amount;
    String category;


}

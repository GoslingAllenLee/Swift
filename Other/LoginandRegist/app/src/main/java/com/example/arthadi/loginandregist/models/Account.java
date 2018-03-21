package com.example.arthadi.loginandregist.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yohanes Himawan K on 1/12/2018.
 */

public class Account implements Parcelable{
    public int id;
    public String uid;
    public long transactionDate;
    public String category;
    public String subCategory;
    public long amount;
    public String note;
    public String type;

    //tambahan untuk section header
    public String month;
    public Integer date;
    public String day;
    public Boolean isHeader=false;

    public Account() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeLong(transactionDate);
        parcel.writeString(category);
        parcel.writeString(subCategory);
        parcel.writeLong(amount);
        parcel.writeString(note);
        parcel.writeString(type);

    }

    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>(){
        public Account createFromParcel(Parcel in){
            return new Account(in);
        }
        public Account [] newArray (int size){
            return new Account[size];
        }
    };

    private Account (Parcel in){
        id = in.readInt();
        transactionDate = in.readLong();
        category = in.readString();
        subCategory = in.readString();
        amount = in.readLong();
        note = in.readString();
        type = in.readString();
    }
}

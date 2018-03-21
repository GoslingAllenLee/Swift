package com.example.arthadi.loginandregist.models;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by blastocode on 6/6/17.
 */

@IgnoreExtraProperties
public class Transaction implements Parcelable{
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

    public Transaction() {

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

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>(){
        public Transaction createFromParcel(Parcel in){
            return new Transaction(in);
        }
        public Transaction [] newArray (int size){
            return new Transaction[size];
        }
    };

    private Transaction (Parcel in){
        id = in.readInt();
        transactionDate = in.readLong();
        category = in.readString();
        subCategory = in.readString();
        amount = in.readLong();
        note = in.readString();
        type = in.readString();
    }
}

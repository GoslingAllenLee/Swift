package com.example.arthadi.loginandregist;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Arthadi on 13/06/2017.
 */

public class Application extends android.app.Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        //enable offline data store
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

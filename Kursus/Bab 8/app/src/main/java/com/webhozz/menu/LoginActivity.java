package com.webhozz.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_register){
            Intent registeritent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(registeritent);
        }
        return super.onOptionsItemSelected(item);

    }

//    @Override
//    public boolean onCreateOptionMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if(id == R.id.menu_register){
//            Intent registeritent = new Intent(LoginActivity.this,RegisterActivity.class);
//            startActivity(registeritent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}

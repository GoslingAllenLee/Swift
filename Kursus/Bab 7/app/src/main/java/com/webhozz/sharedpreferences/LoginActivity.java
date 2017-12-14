package com.webhozz.sharedpreferences;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtID, txtPassword;
    Button btnLogin;
    String id, password;
//    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        txtID = (EditText) findViewById(R.id.txtID);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

//        pref = getSharedPreferences("data", Context.MODE_PRIVATE);
//
//        if(pref.getString("nim",null) != null){
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//            finish();
//        }

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                id = txtID.getText().toString();
                password = txtPassword.getText().toString();
//                if(id.length() < 10){
//                    Toast.makeText(LoginActivity.this, "Please Input Your Student Number",Toast.LENGTH_SHORT).show();
//                }else if (password.equals("")){
//                    Toast.makeText(LoginActivity.this, "Please Input Your Password",Toast.LENGTH_SHORT).show();
//                }else{



//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.setComponent(ComponentName.unflattenFromString("com.google.android.apps.maps"));
//                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);





                    //Toast.makeText(LoginActivity.this, "Anda berhasil",Toast.LENGTH_SHORT).show();
                    openApp(LoginActivity.this, "com.google.android.youtube");
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("nim",id);
//                    //editor.commit();
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                    finish();
//                }
            }
        });

    }

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                return false;
                //throw new ActivityNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}

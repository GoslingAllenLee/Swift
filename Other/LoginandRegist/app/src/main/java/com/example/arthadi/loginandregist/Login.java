package com.example.arthadi.loginandregist;

/**
 * Created by Arthadi on 21/03/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.arthadi.loginandregist.Constanta.URL_Con;


public class Login extends Fragment implements View.OnClickListener
{
    private static final String TAG = "Login";
    private EditText edtEmail, edtPwd;
    private Button btn_login;
    //private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.log_in, container, false);
        //auth = FirebaseAuth.getInstance();


//        if(auth.getCurrentUser() != null)
//        {
//            //close this activity
//            getActivity().finish();
//            //opening profile activity
//            startActivity(new Intent(getContext(), HomeActivity.class));
//        }

        edtEmail = (EditText) rootView.findViewById(R.id.edt_email);
        edtPwd = (EditText) rootView.findViewById(R.id.edt_pwd);
        btn_login = (Button) rootView.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);

        return rootView;
    }

    private void userLogin()
    {
        final String email = edtEmail.getText().toString().trim();
        final String password  = edtPwd.getText().toString().trim();
        progressDialog = new ProgressDialog(getActivity());

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getContext(),"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Login Please Wait...");
        progressDialog.show();

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        final Map<String,String> param = new HashMap<>();
        StringRequest sr = new StringRequest(Request.Method.POST, URL_Con, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                JSONObject JBJ = null;

                try {
                    JBJ = new JSONObject(response);
                    //JBJ.getJSONObject("status");
                    if(JBJ.getString("status").equals("success")){
                        getActivity().finish();
                        startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
                    }else {
                        Toast.makeText(getActivity(), JBJ.getString("value"), Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                param.put("Content-Type","application/x-www-form-urlencoded");

                return param;
            }

            @Override
            protected  Map<String,String> getParams() throws AuthFailureError{
                param.put("type", "login");
                param.put("username", email);
                param.put("password", password);
                return param;
            }
        };
        rq.add(sr);

//        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener((Activity) getContext(),
//                new OnCompleteListener<AuthResult>()
//        {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                //checking if success
//                if(task.isSuccessful())
//                {
//                    getActivity().finish();
//                    startActivity(new Intent(getContext(), HomeActivity.class));
//                }
//                else
//                {
//                    //display some message here
//                    Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_LONG).show();
//                }
//                progressDialog.dismiss();
//            }
//        });
    }


    @Override
    public void onClick(View v)
    {
        userLogin();
    }
}

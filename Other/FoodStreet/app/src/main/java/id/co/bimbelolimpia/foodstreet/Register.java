package id.co.bimbelolimpia.foodstreet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    EditText txUsername, txtEmail, txtPhonenumber;
    Button btnLogin;
    String username, email, phonenumber;
    ProgressDialog dialog;

    String url = "http://bimbelolimpia.co.id/foodstreet/registrasi.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dialog = new ProgressDialog(Register.this);
        txUsername = (EditText) findViewById(R.id.username);
        txtEmail = (EditText) findViewById(R.id.email);
        txtPhonenumber = (EditText) findViewById(R.id.phonenumber);
        btnLogin = (Button) findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                dialog.setMessage("Loading ...");
                username = txUsername.getText().toString();
                email = txtEmail.getText().toString();
                phonenumber = txtPhonenumber.getText().toString();

                if(username.equals("") || email.equals("") || phonenumber.equals("")){
                    Toast.makeText(Register.this, "Please Input Your Usernama, Email or Password",Toast.LENGTH_SHORT).show();
                }else {

                    RequestQueue rq = Volley.newRequestQueue(Register.this);
                    final Map<String,String> param = new HashMap<>();
                    StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject JBJ = null;
                            try {
                                JBJ = new JSONObject(response);
                                JBJ.getJSONObject("message");
                                Toast.makeText(getApplicationContext(), JBJ.getString("message"), Toast.LENGTH_LONG).show();
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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
                            param.put("username", txUsername.getText().toString().trim());
                            param.put("email", txtEmail.getText().toString().trim());
                            param.put("hp", txtPhonenumber.getText().toString().trim());
                            param.put("password", "1234");
                            param.put("type", "b");
                            return param;
                        }
                    };
                    rq.add(sr);
                    dialog.show();
//                    sendAndRequestResponse();
//                    dialog.show();
                }

            }
        });

        Button MoveTologin= (Button) findViewById(R.id.btn_login);
        MoveTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
            }
        });

    }
}

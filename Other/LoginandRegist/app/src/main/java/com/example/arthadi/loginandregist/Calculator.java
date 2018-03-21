package com.example.arthadi.loginandregist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.regex.Pattern;

/**
 * Created by Arthadi on 02/05/2017.
 */

public class Calculator extends AppCompatActivity {
    private TextView _screen, tvDone;
    private ImageView ivBack;
    private String display = "";
    private String currentOperator = "";
    private String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        tvDone = (TextView) findViewById(R.id.tvDone);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        _screen = (TextView)findViewById(R.id.textView);
        _screen.setText(display);
        final Intent i = new Intent(getApplicationContext(), AddTransActivity.class);
        i.putExtra("key",display);
        setResult(RESULT_OK);
        setResult(123,i);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(result =="")
                {
                    i.putExtra("key",display);
                    setResult(RESULT_OK);
                    setResult(123,i);
                }else
                {
                    i.putExtra("key",result);
                    setResult(RESULT_OK);
                    setResult(123,i);
                }
                finish();
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//              Intent i = new Intent(getApplicationContext(), AddTransActivity.class);
                if(result =="")
                {
                    i.putExtra("key",display);
                    setResult(RESULT_OK);
                    setResult(123,i);
                }else
                {
                    i.putExtra("key",result);
                    setResult(RESULT_OK);
                    setResult(123,i);
                }
                finish();


            }
        });


    }

    private void updateScreen(){
        _screen.setText(display);
    }

    public void onClickNumber(View v){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '÷':return true;
            default: return false;
        }
    }

    public void onClickOperator(View v){
        if(display == "") return;

        Button b = (Button)v;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }

        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear(){
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }

    private int operate(String a, String b, String op){
        switch (op){
            case "+": return Integer.valueOf(a) + Integer.valueOf(b);
            case "-": return Integer.valueOf(a) - Integer.valueOf(b);
            case "x": return Integer.valueOf(a) * Integer.valueOf(b);
            case "÷": try{
                return Integer.valueOf(a) / Integer.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getResult(){
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v){
        if(display == "") return;
        if(!getResult()) return;
        _screen.setText(display + "\n" + String.valueOf(result));
    }


}

package com.webhozz.intent;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    Button btn_dial, btn_browser, btn_camera, btn_message;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_dial = (Button) findViewById(R.id.btn_dial);
        btn_browser = (Button) findViewById(R.id.btn_browser);
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_message = (Button) findViewById(R.id.btn_message);

        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent(view);
            }
        });

        btn_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent(view);
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent(view);
            }
        });

        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent(view);
            }
        });
    }
        public void startIntent(View view){
            Intent intent = null;
            switch (view.getId()){
                case R.id.btn_dial:
                    Intent dialintent = new Intent(Intent.ACTION_DIAL);
                    dialintent.setData(Uri.parse("tel:+081289057972"));
                    startActivity(dialintent);
                    break;
                case R.id.btn_browser:
                    Intent browserintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://binusmaya.binus.ac.id"));
                    startActivity(browserintent);
                    break;
                case R.id.btn_camera:
                    Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(cameraintent.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(cameraintent, REQUEST_IMAGE_CAPTURE);
                    }
                    break;
                case R.id.btn_message:
                    Intent messageintent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:+081289057972"));
                    startActivity(messageintent);
                    break;
            }

        }

}

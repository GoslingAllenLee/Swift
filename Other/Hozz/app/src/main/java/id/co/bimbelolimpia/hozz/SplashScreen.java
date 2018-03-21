package id.co.bimbelolimpia.hozz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        setContentView(R.layout.activity_login);
//
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 =(TextView) findViewById(R.id.tv2);
        iv = (ImageView) findViewById(R.id.iv);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv1.startAnimation(myanim);
        tv2.startAnimation(myanim);
        iv.startAnimation(myanim);

        final Intent MoveToRegister = new Intent(this, Register.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(MoveToRegister);
                    finish();
                }


            }




        };

        timer.start();

    }
}

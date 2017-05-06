package com.example.android.whitecaps;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//SplashScreen
public class DAMgr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts  here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damgr);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                Intent intent = new Intent(DAMgr.this, mainPage.class);//going to mainPage activity from current activity
                startActivity(intent);
            }
        }, 5000);//Splashscreen displays for duration of 2seconds

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

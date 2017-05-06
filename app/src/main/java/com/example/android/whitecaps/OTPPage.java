package com.example.android.whitecaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.R.attr.data;
//ShowOTP Page
public class OTPPage extends AppCompatActivity {
    private static TextView tv;
    private static TextView showClock;
    private String OTP;
    private  String Period;
    String provider;
    LocationManager locationManager;
    String startTimeOtp;
    String endTimeOtp;
    static String Text;
    LocationListener mlocListener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppage);
        Intent intent = getIntent();
        OTP = intent.getStringExtra("OTP");//OTP retrieved from AttenMgr class
        Period=intent.getStringExtra("Period");//Period retrieved from AttenMgr class
        startTimeOtp= getCurrentTime();//gets current time when OTP is generated
        startTimer();//Two minute timer starts


    }

    //gets current time when OTP is generated
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCurrentTime()
    {
        Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date now= calender.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        String time1= date.format(now);
        return time1;
    }


    //gets current location when OTP is generated
    public  void locationl()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         mlocListener = new MyLocationListner(this,Period,startTimeOtp,endTimeOtp,OTP);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 0, mlocListener);

    }
    //Two minute Timer starts
    public void startTimer() {
        showClock = (TextView) findViewById(R.id.textView7);
        tv = (TextView) findViewById(R.id.textView8);
        tv.setText(OTP);
        new CountDownTimer(30000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                showClock.setText("" + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onFinish() {
                showClock.setText("done!");
                endTimeOtp= getCurrentTime();//After end of two minute timer current time is stored
                locationl();
                Intent i = new Intent(OTPPage.this, Teacher_Dashboard.class);//Ater OTP expires move to teacher dashboard
                startActivity(i);
            }
        }.start();

    }
}
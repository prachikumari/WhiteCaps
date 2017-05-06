package com.example.android.whitecaps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
//Teacher Dashboard.
public class Teacher_Dashboard extends AppCompatActivity {
    private String usr_name;
    private static TextView username;
    Button takeAttenBtn;
    DataMgr helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__dashboard);
        Intent intent = getIntent();
        usr_name = intent.getStringExtra("usr");//retrieving username from LogiMgr Activity
        String table_name = intent.getStringExtra("table_name");//retrieving tablename from LogiMgr Activity
        username =(TextView)findViewById(R.id.textView2);
        helper = new DataMgr(Teacher_Dashboard.this);
        String name =  helper.getName(LoginMgr.getEmail(),LoginMgr.getTable());//extracing name of user using its username and tablename from database
        username.setText(name);// name set in textview
        takeAttendanceButton();//method called
    }

    //Take Attendance Button is clicked and event follows
    public void takeAttendanceButton()
    {
         takeAttenBtn = (Button)findViewById(R.id.button3);
         takeAttenBtn.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
             public void onClick(View v) {

                    Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                    Date now= calender.getTime();
                    DateFormat date = new SimpleDateFormat("HH:mm a");
                    date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                    String time1= date.format(now);
                    SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
                    SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
                    boolean value1=false;
                    try {
                        String t=(date24Format.format(date12Format.parse(time1)));

                    } catch (ParseException e) {e.printStackTrace();}
                    try {
                       value1= helper.checkTime(time1);//checkTime returns boolean value after comparing current time and period timing
                    } catch (ParseException e) {e.printStackTrace();}
                    if (value1) {//returns true if current time lies in range of period timing
                        AttenMgr attmgr = new AttenMgr(Teacher_Dashboard.this, usr_name);
                        attmgr.takeAttendance();
                    }
                    else//returns false if current time does not lies in range of period timing
                    {
                        showalerttime();//showing alert
                    }
                }
            }
    );
}
 //Alert with message
    public void showalerttime()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("No class is scheduled for given timimg");
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // return;
            }
        });
        alertDialog.show();
    }
}


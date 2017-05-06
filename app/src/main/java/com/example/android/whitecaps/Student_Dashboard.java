package com.example.android.whitecaps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

//Student Dashboard
public class Student_Dashboard extends AppCompatActivity {
    private String usr_name;
    String table_name;
    private static TextView username;
    private static Button btn;
    DataMgr d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);
        Intent intent = getIntent();
        usr_name = intent.getStringExtra("usr");//retrieving username from LogiMgr Activity
        table_name = intent.getStringExtra("table_name");//retrieving tablename from LogiMgr Activity
        username=(TextView)findViewById(R.id.textView2);
        d=new DataMgr(Student_Dashboard.this);
        String name =d.getName(usr_name,table_name);//extracing name of user using its username and tablename from database
        username.setText(name);// name set in textview
        displayPromptForEnablingGPS(Student_Dashboard.this);
       // displayLocationSettingsRequest(Student_Dashboard.this);
        giveAttendanceButton();//method called
    }

    //Take Attendance Button is clicked and event follows
    public void giveAttendanceButton()
    {
          btn= (Button)findViewById(R.id.giveatten);
          btn.setOnClickListener(new View.OnClickListener() {
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
                    value1= d.checkTime(time1);//checkTime returns boolean value after comparing current time and period timing
                } catch (ParseException e) {e.printStackTrace();}
                if (value1) {//returns true if current time lies in range of period timing
                    AttenMgr attmgr = new AttenMgr(Student_Dashboard.this, usr_name);
                    attmgr.giveAttendance();
                }
                else//returns false if current time does not lies in range of period timing
                {
                    showalerttime();//showing alert
                }
            }
        }
        );
    }
    public static void displayPromptForEnablingGPS(final Activity activity)
    {
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable either GPS or any other location"
                + " service to find current location.  Click OK to go to"
                + " location services settings to let you do so.";

        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();

                            }

                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
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
    /*private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }*/
}

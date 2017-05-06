package com.example.android.whitecaps;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by User on 03-05-2017.
 */
//MyLocationListener.

public class MyLocationListner implements LocationListener {
    Context p;
    String Text;
    String Period;
    String startTimeOtp;
    String endTimeOtp;
    String OTP;

    //Constructor
    public MyLocationListner(Context p,String Period,String startTimeOtp,String endTimeOtp,String OTP)
    {
        this.p=p;
        this.Period=Period + ",";
        this.startTimeOtp=startTimeOtp + ",";
        this.endTimeOtp=endTimeOtp+ ",";
        this.OTP=OTP+" ";


    }
    //gets location of user
    @Override
    public void onLocationChanged(Location loc) {
        loc.getLatitude();
        loc.getLongitude();
        Text =loc.getLatitude() + "," + loc.getLongitude();
        SaveInInternalCacheStorage();
        LoadFromInternalCacheStorage();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    public void SaveInInternalCacheStorage() {
        File dir = p.getCacheDir();  //return the directory of internal cache in which your file will be created
        File file = new File(dir,"MyFile.txt");  //Creates a new file named MyFile.txt in a folde "dir"
        writeData(file, OTP,Period,startTimeOtp,endTimeOtp,Text);  //Calling user defined method

    }
    public void LoadFromInternalCacheStorage() {
        File dir = p.getCacheDir();  //return the directory of internal cache in which your file will be created
        File file = new File(dir,"MyFile.txt");  //Creates a new file named MyFile.txt in a folder "dir"
        readData(file);  //Calling user defined method
    }

    //Writing data into file
    public void writeData(File file,String text1,String text2,String text3,String text4,String text5)
    {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);

            //writing the text into the file in the form of char[] bytes ; getBytes will convert string to char[] bytes
            //Text contains OTP,Period,startTimeOtp,endTimeOtp and location of user
            fileOutputStream.write(text1.getBytes());
            fileOutputStream.write(text2.getBytes());
            fileOutputStream.write(text3.getBytes());
            fileOutputStream.write(text4.getBytes());
            fileOutputStream.write(text4.getBytes());
            fileOutputStream.write(text5.getBytes());
        } catch (FileNotFoundException e) {e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();}
        finally {
            if(fileOutputStream!=null)
            {
                try {
                    fileOutputStream.close();    //closing the file
                } catch (IOException e) {e.printStackTrace();}
            }
        }

    }


    //Reading data from file
    public void readData(File file) {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            int read = -1;
            StringBuffer stringBuffer = new StringBuffer();//to store a string (which is retrieved by Internal Storage)
            while ((read = fileInputStream.read()) != -1)//read() will return ascii value (char[] bytes) and returns -1 if bytes not found
            {
                stringBuffer.append((char) read);//Converting ascii into char by typecasting
            }
            String showdata = stringBuffer.toString();
        } catch (FileNotFoundException e) {e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }


    }
};





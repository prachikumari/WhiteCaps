package com.example.android.whitecaps;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 02-05-2017.
 */
//AttenMgr Page
class AttenMgr implements Parcelable {
    private Context context;
    String OTP;
    private String useremail;

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    //Constructor
    protected AttenMgr(Parcel in) {
        useremail = in.readString();
    }
    //Default Constructor
    public AttenMgr() {

    }
    //Constructor
    public AttenMgr(Context context,String n) {
        this.context = context;
        setUseremail(n);
    }

    //AttenMgr is calling TakeAttendance
    public void takeAttendance() {
        Intent i1 = new Intent (context, TakeAttendance.class);//move to Take Atteendance Activity
        i1.putExtra("var",  this);//Passing instance of this class to Take Attendance Activity
        context.startActivity(i1);
    }

    public void giveAttendance() {
        Intent i1 = new Intent (context, GiveAttendance.class);//move to Take Atteendance Activity
        i1.putExtra("var",  this);//Passing instance of this class to Take Attendance Activity
        context.startActivity(i1);
    }

    //OTP,classid extracted from database is returned in this method and OTP,Period are forwarded to OTPPage Activity
    public void generateOTP(String day, String period, String stream, String semester, String section,DataMgr d,Context c)
    {

        String classid =  d.getclssid(stream,semester,section);
        String OTP1 = d.getOTP(classid , period,day);
        setOTP(OTP1);
        Intent i2 = new Intent(c , OTPPage.class);
        i2.putExtra("OTP",getOTP());
        i2.putExtra("Period",period);
        c.startActivity(i2);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(useremail);
       // dest.writeParcelable(AttenMgr.this,1);
    }
    public static final Creator<AttenMgr> CREATOR
            = new Creator<AttenMgr>() {
        public AttenMgr createFromParcel(Parcel in) {
            return new AttenMgr(in);
        }

        public AttenMgr[] newArray(int size) {
            return new AttenMgr[size];
        }
    };
}

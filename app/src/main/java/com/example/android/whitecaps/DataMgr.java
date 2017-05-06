 package com.example.android.whitecaps;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 25-04-2017.
 */
/*
public class DataMgr extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ATTENDANCESYSTEM.db";
    public static final String TABLE_NAME = "STUDENT";
    public static final String COL_1 = "STUD_ENROLLID";
    public static final String COL_2 = "UNIV_ROLLNO";
    public static final String COL_3 = "CLASSROLL";
    public static final String COL_4 = "NAME";
    public static final String COL_5 = "EMAILID";
    public static final String COL_6 = "CONTACT";


    public DataMgr(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +"(STUD_ENROLLID INTEGER PRIMARY KEY,UNIV_ROLLNO INTEGER,CLASSROLL INTEGER,NAME TEXT, EMAILID TEXT,CONTACT INTEGER);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from STUDENT",null);
        return res;
    }
}*/
//package com.example.shippingdbwithandroid;

//DataManager
public class DataMgr extends SQLiteOpenHelper {


    private static String DB_NAME = "ATTENDANCESYSTEM";//DatabaseName
    private final Context myContext;
    private String DB_PATH = null;
    private SQLiteDatabase myDataBase;
    public static final int DATABASE_VERSION = 10;//Version of Database

    //DataMgr Constructor
    public DataMgr(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        }
    //Reads Database and copies it in case of any change in database
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {throw new Error("Error copying database");}

        }
    //checking Database
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {}
        if (checkDB != null) {
           checkDB.close();
        }
        return checkDB != null ? true : false;

    }

    //searching password corresponding to valid username
    public String searchPass(String user,String table){
        myDataBase = this.getReadableDatabase();
        String query = "select * from "+table;
        Cursor cr =  myDataBase.rawQuery(query,null);
        String a,b;
        b="not found";
        if(cr.moveToFirst()){
            do {
                a=cr.getString(cr.getColumnIndex("Emailid"));
                if(a.equals(user)){
                    b=cr.getString(cr.getColumnIndex("Password"));
                    break;
                }
            }while (cr.moveToNext());
        }
        cr.close();
        return b;
    }

    //Extracting all the sections present in the routine database
    public  ArrayList<String> getAllSections(String stream,String semester ) {
        myDataBase = this.getReadableDatabase();
        String query = "select distinct sec from routine where stream='" + stream.trim() + "'and semester='" + semester.trim() + "' ORDER BY SEC";
        Cursor cr = myDataBase.rawQuery(query, null);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Section");
        for(cr.moveToFirst();!cr.isAfterLast(); cr.moveToNext()){
            arr.add(cr.getString(cr.getColumnIndex("Sec")));
        }
           cr.close();
            return arr;
        }

    //Extracting all streams  from routine database
    public  ArrayList<String> getAllStreams() {
        myDataBase = this.getReadableDatabase();
        String query = "select distinct Stream from routine ";
        Cursor cr = myDataBase.rawQuery(query, null);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Stream");
        if (cr.moveToFirst()) {
            do {
                  arr.add(cr.getString(0));
            } while (cr.moveToNext());

        }

        cr.close();
        return arr;
    }

    //Extracting all semester  from routine database
    public  ArrayList<String> getAllSemester(String stream) {
        myDataBase = this.getReadableDatabase();
        String query = "select distinct Semester from routine where stream='" + stream.trim() + "'";
        Cursor cr = myDataBase.rawQuery(query, null);
        ArrayList<String> arr = new ArrayList<>();

        arr.add("Semester");
        if (cr.moveToFirst()) {
            do {
                arr.add(cr.getString(0));
            } while (cr.moveToNext());

        }

        cr.close();
        return arr;
    }

    //Extracting TeacherName using teachercode from teacher's database
     public  String gettname(String tcode)
     {
            String query = "select * from TEACHER where Teachercode='" + tcode.trim() + "'";
            Cursor cr = myDataBase.rawQuery(query, null);
            String tname = null;
            if (cr.moveToFirst()) {
                tname = cr.getString(cr.getColumnIndex("Name"));
            }
            cr.close();
            return tname;
     }

     //Extracting subjectname using subjectcode from subject database
     public  String getsubname(String subcode)
     {
            String query = "select * from SUBJECT where Subcode='" + subcode.trim() + "'";
            Cursor cr = myDataBase.rawQuery(query, null);
            String subname = null;
            if (cr.moveToFirst()) {
               subname = cr.getString(1);
            }
            cr.close();
            return subname;
     }

    //Extracting subjectcode with teachercode using stream,semester,section,period,day  from routine database
    public String getSub(String stream , String semester, String section , String period, String day)
    {
          myDataBase = this.getReadableDatabase();
          String query = "select * from ROUTINE where Stream='" + stream.trim() + "' and Semester='" + semester.trim() + "'and Sec='" + section.trim() + "'and Day='" + day.trim() + "'";
          Cursor cr = myDataBase.rawQuery(query, null);
          String a = null;
          if (cr.moveToFirst()) {
            do {
                a = cr.getString(Integer.parseInt(period) + 3);
               } while (cr.moveToNext());
            }
        cr.close();
        return a;
    }

   //checking current time with period timings so that Take Attendance and Give Attendance become disable in non-working hours of college
   // using Timing database
   @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean checkTime(String currTime) throws ParseException
    {
        myDataBase = this.getReadableDatabase();
        String query = "select * from Timing";
        Cursor cr = myDataBase.rawQuery(query, null);
        String startTime=null,endTime=null;
        boolean f=false;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//Time is in 24hr format
        Date d=sdf.parse(currTime);
        if (cr.moveToFirst())
            do {
            startTime = cr.getString(1);
            endTime = cr.getString(2);
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);

            long elapsed1 = d.getTime() - d1.getTime();
            long elapsed2 = d2.getTime() - d.getTime();
            if (elapsed1 >= 0 && elapsed2 >= 0) {
                f=true;
                break;

            }
        } while (cr.moveToNext());
       cr.close();
      return  f;
    }

    //Extracting name using emailid from admin/teacher/student database
    public String getName(String user,String table){
        myDataBase = this.getReadableDatabase();
        String query = "select * from '"+table+"' where Emailid='"+user+"'";
        Cursor cr =  myDataBase.rawQuery(query,null);
        String a,b;
        b="not found";
        if(cr.moveToFirst()){
            do {
                a=cr.getString(cr.getColumnIndex("Emailid"));
                if(a.equals(user)){
                    b=cr.getString(cr.getColumnIndex("Name"));
                    break;
                }
            }while (cr.moveToNext());
        }
        cr.close();
        return b;
    }

    //Verifying that entered username is valid and it eists in the database
    public boolean getUser(String user,String table)
    {
        myDataBase = this.getReadableDatabase();
        String query = "select * from '"+table+"'";
        Cursor cr =  myDataBase.rawQuery(query,null);
        String a,b;
        b="not found";
        boolean flag=false;
        if(cr.moveToFirst()){
            do {
                a=cr.getString(cr.getColumnIndex("Emailid"));
                if(a.equals(user)){
                    flag=true;
                    break;
                }
            }while (cr.moveToNext());
        }
        cr.close();
        return flag;
    }

    //Extracting classid using stream,semester,section from Classid database
    public String getclssid(String stream, String semester, String section)
    {
        myDataBase = this.getReadableDatabase();
        String query = "select * from Classid where Stream='" + stream.trim() + "' and Semester='" + semester.trim() + "'and Section='" + section.trim()  + "'";
        Cursor cr = myDataBase.rawQuery(query, null);
        String a = null;
        if (cr.moveToFirst()) {
          do {
            a = cr.getString(cr.getColumnIndex("_id"));
          } while (cr.moveToNext());
         }
         cr.close();
         return a;
    }



    //Extracting OTP using classid,period,day from OTP database
    public  String getOTP(String classid,String period,String day)
    {
        myDataBase = this.getReadableDatabase();
        String query = "select * from OTP where classid='" + classid.trim() + "' and Period='" + period.trim() + "'and Day='" + day.trim()  + "'";
        Cursor cr = myDataBase.rawQuery(query, null);
        String a = null;
        if (cr.moveToFirst()) {
        do {
            a = cr.getString(cr.getColumnIndex("OTP"));
        } while (cr.moveToNext());
       }
        cr.close();
        return   a;
    }

    //copyDatabase
    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
       // byte[] buffer = new byte[10];
        byte[] buffer = new byte[2024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }
    //delete database
    public void db_delete()
    {
        File file = new File(DB_PATH + DB_NAME);
        if(file.exists())
        {
            file.delete();
            System.out.println("delete database file.");
        }
    }
    //open database
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }
}


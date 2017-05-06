package com.example.android.whitecaps;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

//Login Page 1
public class LoginMgr extends AppCompatActivity {
    private static EditText username;
    private static EditText password;
    private static Button login_btn;
    private static String email;
    private static String table;
    Cursor c = null;

    //Getter Setter
    public static String getTable() {
        return table;
    }
    public static void setTable(String table) {
        LoginMgr.table = table;
    }
    static public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        login_btn = (Button)findViewById(R.id.button_login);

        Intent intent=getIntent();
        String str = intent.getStringExtra("mystr");//getting value from mainPage(i.e 1/2/3)
        if(str == null) str = 1 +"" ;
        int i = Integer.parseInt(str);
        //viewData();
        //set table according to value
        if(i==1) table="student";
        if(i==2) table="teacher";
        if(i==3) table="admin";
        setTable(table);//table name is set as per value
        LoginButton(table);//method called
    }

   /* public void viewData(){
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataMgr myDbHelper = new DataMgr(LoginMgr.this);
                try {
                    myDbHelper.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    myDbHelper.openDataBase();
                } catch (SQLException sqle) {
                    throw sqle;
                }
                Toast.makeText(LoginMgr.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
                c = myDbHelper.query("STUDENT", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                    do {
                        Toast.makeText(LoginMgr.this,
                                "Enroll: " + c.getString(0) + "\n" +
                                        "univroll: " + c.getString(1) + "\n" +
                                        "classroll: " + c.getString(2) + "\n" +
                                        "name: " + c.getString(3) + "\n" +
                                        "emailid: " + c.getString(4) + "\n" +
                                        "contact:  " + c.getString(5),
                                Toast.LENGTH_LONG).show();
                    } while (c.moveToNext());
                }
            }
        });
    }*/
    public void authenticateUser( String table, String username , String password) {
       DataMgr helper = new DataMgr(LoginMgr.this);
        try {
          helper.createDataBase();
       } catch (IOException e) {e.printStackTrace();}

        helper.getWritableDatabase();
        boolean match_user=helper.getUser(username,table);// vaerify if username is valid
        String pass = helper.searchPass(username, table);//getting password corresponding to the valid username

        //Checking all possible conditions and showing alert with suitable message
        if(username.equals("")|| password.equals("")){
            String msg ="Please enter empty fields";
            showalert(msg);
        }
        else if (password.equals(pass)&& match_user) {
            Toast.makeText(LoginMgr.this, "User and Password is correct", Toast.LENGTH_SHORT).show();
            Intent intent = null;
            if (table == "student") {
                intent = new Intent(LoginMgr.this,Student_Dashboard.class);
                //On successful login as student move to student dashboard activity
            } else if (table == "admin") {
                intent = new Intent(LoginMgr.this,Admin_Dashboard.class);//On successful login as admin move to student dashboard activity
            } else if (table == "teacher") {
                intent = new Intent(LoginMgr.this, Teacher_Dashboard.class);//On successful login as teacher move to student dashboard activity
            }
            //send username and table to next activity
            intent.putExtra("usr", username);
            intent.putExtra("table_name", table);
            startActivity(intent);
        }
        else if( match_user==false)
        {
            String msg ="Invalid Username";
            showalert(msg);
        }
        else if(match_user && password!=pass)
        {
            String msg ="Invalid Password";
            showalert(msg);
        }

    }
    //showsalert with message
    public void showalert(String msg)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // return;
            }
        });
        alertDialog.show();
    }
    public  void LoginButton( final String table) {


        login_btn.setOnClickListener(//on clicking login button
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       setEmail(username.getText().toString());//email set
                        authenticateUser(table , username.getText().toString() , password.getText().toString());//method called to verify user
                    }
                }
        );
    }

}

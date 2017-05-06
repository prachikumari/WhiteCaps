package com.example.android.whitecaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
//Admin Dashboard
public class Admin_Dashboard extends AppCompatActivity {
      private String usr_name;
      String table_name;
      private static TextView username;
      DataMgr d;

      @Override
      protected void onCreate(Bundle savedInstanceState) {//execution starts here
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_admin__dashboard);
          Intent intent = getIntent();
          usr_name = intent.getStringExtra("usr");//retrieving username from LogiMgr Activity
          table_name = intent.getStringExtra("table_name");//retrieving tablename from LogiMgr Activity
          username=(TextView)findViewById(R.id.textView2);
          d=new DataMgr(Admin_Dashboard.this);
          String name =d.getName(usr_name,table_name);//extracing name of user using its username and tablename from database
          username.setText(name);// name set in textview
      }
}

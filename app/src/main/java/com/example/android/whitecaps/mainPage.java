package com.example.android.whitecaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//Login As Page
public class mainPage extends Activity {

    Button studentBtn ,teacherBtn, adminBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        LoginAs();//method called

    }
    public void startMainActivity(String mystr)
    {
        Intent intent = new Intent(this, LoginMgr.class);//going to LoginMgr activity from current activity

        intent.putExtra("mystr",mystr);//choosen value i.e. 1/2/3 is sent to LoginMgr depending on the button clicked
        startActivity(intent);
    }

    public  void LoginAs() {
        studentBtn= (Button)findViewById(R.id.button5);//only student can login clicking on this button
        studentBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mainPage.this,"Success login as Student",
                                Toast.LENGTH_SHORT).show();
                        int i = 1;//i=1 is set corresponding to student
                        String mystr1 = i+"";
                        startMainActivity(mystr1);//1 is sent to LoginMgr
                        }
                }
        );

        teacherBtn= (Button)findViewById(R.id.button6);//only teacher can login clicking on this button
        teacherBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mainPage.this,"Success login as Teacher",
                                Toast.LENGTH_SHORT).show();
                        int i = 2;//i=2 is set corresponding to teacher
                        String mystr = i+"";
                        startMainActivity(mystr);//2 is sent to LoginMgr
                    }
                }
        );

        adminBtn= (Button)findViewById(R.id.button);//only admin can login clicking on this button
        adminBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mainPage.this,"Success login as Admin",
                                Toast.LENGTH_SHORT).show();
                        int i = 3;//i=3 is set corresponding to admin
                        String mystr = i+"";
                        startMainActivity(mystr);//3 is sent to LoginMgr
                    }
                }
        );
    }

}

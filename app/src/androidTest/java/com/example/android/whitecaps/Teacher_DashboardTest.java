package com.example.android.whitecaps;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by User on 06/05/17.
 */
@RunWith(JUnit4.class)
public class Teacher_DashboardTest {
    ////LoginMgr lmr ;
//DataMgr dm = new DataMgr(getContext());
    @Rule
    public ActivityTestRule<Teacher_Dashboard> td = new ActivityTestRule<Teacher_Dashboard>(Teacher_Dashboard.class,true,false);

    @Before
    public void setUp() throws Exception {
//lmr = new LoginMgr();
       // lmr.setEmail("tamal@gmail.com");
        //LoginMgr.setTable("teacher");
    }
@Test
public void launch()
{
    Intent i = new Intent();
    i.putExtra("usr","tamal@gmail.com");
    i.putExtra("table_name","teacher");
    td.launchActivity(i);

   // assertEquals("Expected" ,"Tamal Chakraborty",dm.getName("tamal@gmail.com","teacher") );

    onView(withId(R.id.textView2)).check(matches(withText("Tamal Chakraborty")));
    onView(withId(R.id.button3)).check(matches(notNullValue() ));
;}

    @After
    public void tearDown() throws Exception {

    }

}

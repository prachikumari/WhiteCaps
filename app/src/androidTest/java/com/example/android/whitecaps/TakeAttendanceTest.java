package com.example.android.whitecaps;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Pramod on 06/05/17.
 */
@RunWith(JUnit4.class)
public class TakeAttendanceTest {
  AttenMgr atmr;
   // DataMgr dm = new DataMgr(getContext());
RoutineMgr rm;
    TakeAttendance t;
   @Rule
   public ActivityTestRule<TakeAttendance> atr = new ActivityTestRule<TakeAttendance>(TakeAttendance.class,true,false);

    @Before
    public void setUp() throws Exception {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        atmr=new AttenMgr(targetContext,"tamal@gmail.com");
       // atmr.takeAttendance();
    //atmr.setUseremail("tamal@gmal.com");
    }
    @Test
    public void launc()
    {
        Intent i = new Intent();
       i.putExtra("var",atmr);
        atr.launchActivity(i);
        onView(withId(R.id.spinner1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CSE"))).perform(click());
        onView(withId(R.id.spinner2)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("1"))).perform(click());
        onView(withId(R.id.spinner3)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("A"))).perform(click());
        onView(withId(R.id.spinner4)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("1"))).perform(click());
     rm = new RoutineMgr("tamal@gmail.com",t,"CSE",
             "1", "1","A","Tuesday" );
        assertEquals("CSE",rm.getStream());
        //onView(withId(R.id.textView)).check(matches((Matcher<? super View>) isNotNull()));



        //onView(withId(R.id.spinner1).matches(withSpinnerText(containsString("CSE"))));
      //  dm.getsubname("EEE");

       // onView(withId(R.id.spinner1)).check(m);
    }


    @After
    public void tearDown() throws Exception {

    }

}
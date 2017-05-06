package com.example.android.whitecaps;

import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Pramod on 05/05/17.
 */
@RunWith(JUnit4.class)
public class LoginMgrTest {
EditText t;
    private  String useremail="tamal@gmail.com";
    private String password="tamal123";
    @Rule
   public ActivityTestRule<LoginMgr> lmr = new ActivityTestRule<LoginMgr>(LoginMgr.class);
    @Before
    public void setUp() throws Exception {
        //l=this.getActivity();

    }
  @Test

  public void k()

  {
      onView(withId(R.id.editText_user)).perform(typeText("tamal@gmail.com")).check(matches(notNullValue()));
      onView(withId(R.id.editText_password)).perform(typeText("tamal")).check(matches(notNullValue()));
      //assertNotEquals("",f1());
      onView(withId(R.id.editText_user)).perform(typeText("tamal@gmail.com"));
      onView(withId(R.id.editText_password)).perform(typeText("tamal123"));
      onView(withId(R.id.button_login)).perform(click());
     // onView(withId(R.id.editText_user)).check(matches(withText("tamal@gmail.com")));
     assertNotEquals("",f1());
      assertEquals("tamal@gmail.com" , f1());
      assertEquals("tamal123" , f2());


;
  }


    @After
    public void tearDown() throws Exception {

    }

    public String f1()
    {
        return useremail;
    }
    public String f2()
    {
        return password;
    }
}
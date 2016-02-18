package com.example.vrinaldi.transhappy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vrinaldi on 30/01/16.
 * Testing Git push Gregor
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    public static final String FROM = "Von:";
    public static final String TO = "Nach:";
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testShowElements() {
        onView(withId(R.id.requestFrom)).check(matches(withText(FROM)));
        onView(withId(R.id.requestTo)).check(matches(withText(TO)));
        onView(withId(R.id.requestTo)).check(matches(withText(TO)));

    }

    @Test
    public void testSearch() {
        onView(withId(R.id.sarchButton)).perform(click());
        onView(withId(R.id.firstResOff)).check(matches(withText("12:10")));
        onView(withId(R.id.firstResOn)).check(matches(withText("13:10")));
        onView(withId(R.id.firstResDuration)).check(matches(withText("01:00")));
        onView(withId(R.id.firstResTrail)).check(matches(withText("3")));
    }
}

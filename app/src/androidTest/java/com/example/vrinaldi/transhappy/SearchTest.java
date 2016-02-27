package com.example.vrinaldi.transhappy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import android.support.test.espresso.contrib.PickerActions;

import java.util.Calendar;

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
public class SearchTest {
    public static final String FROM = "Von:";
    public static final String TO = "Nach:";
    public static final String SEARCH = "Verbindung suchen";
    public static final String DEPARTURE = "Wil";
    public static final String DESTINATION = "Luzern";
    public static final String CHANGE_DATE = "Datum auswählen";
    public static final String CHANGE_TIME = "Zeit auswählen";

    public static final int YEAR = 2016;
    public static final int MONTH_OF_YEAR = 10;
    public static final int DAY_OF_MONTH = 20;

    public static final int HOUR = 12;
    public static final int MINUTE = 30;


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testShowElements() {
        onView(withId(R.id.requestFrom)).check(matches(withText(FROM)));
        onView(withId(R.id.searchFromText));
        onView(withId(R.id.to)).check(matches(withText(TO)));
        onView(withId(R.id.searchToText));
        onView(withId(R.id.btnChangeDate)).check(matches(withText(CHANGE_DATE)));
        onView(withId(R.id.dpResult));
        onView(withId(R.id.btnChangeTime)).check(matches(withText(CHANGE_TIME)));
        onView((withId(R.id.tpResult)));
        onView(withId(R.id.searchButton)).check(matches(withText(SEARCH)));
    }

    @Test
    public void testSearch() {
        onView(withId(R.id.searchFromText)).perform(typeText(DEPARTURE));
        onView(withId(R.id.searchToText)).perform(typeText(DESTINATION));
        onView(withId(R.id.dpResult)).perform(PickerActions.setDate(YEAR, MONTH_OF_YEAR, DAY_OF_MONTH));
        onView(withId(R.id.tpResult)).perform(PickerActions.setTime(HOUR, MINUTE));
        onView(withId(R.id.searchButton)).perform(click());
    }
}

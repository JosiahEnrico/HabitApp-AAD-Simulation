package com.aadexercise.habitapp.ui.list

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.aadexercise.habitapp.ui.add.AddHabitActivity
import com.aadexercise.habitapp.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
class HabitActivityTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(HabitListActivity::class.java)
    var addHabitAct: Activity? = null

    @Test
    fun homeActivityTest() {
        Espresso.onView(withId(R.id.fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            run {
                addHabitAct = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED).elementAtOrNull(0)
                TestCase.assertTrue(addHabitAct?.javaClass == AddHabitActivity::class.java)
            }

        }


    }
}
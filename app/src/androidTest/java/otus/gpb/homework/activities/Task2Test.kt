package otus.gpb.homework.activities

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//
//  otus.gpb.homework.activities
//  Activities
//
//  Created by ponyu on 3.11.2023
//  Copyright © 2023 ponyu. All rights reserved.

const val INFO_TAG = "Task2Test"

@RunWith(AndroidJUnit4::class)
class Task2Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ActivityA::class.java)

    @Test
    fun testActivityATransitionToActivityB() {
        Intents.init()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val am = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        onView(withId(R.id.btnOpenActivityB)).perform(click())

        Thread.sleep(1000)
        intended(hasComponent(ActivityB::class.java.name))

        val appTasks = am.appTasks

        assertEquals(2, appTasks.size)

        assertEquals(1, appTasks[0].taskInfo.numActivities)
        assertEquals(1, appTasks[1].taskInfo.numActivities)

        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.topActivity?.className)
        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.baseActivity?.className)

        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.topActivity?.className)
        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.baseActivity?.className)

        Intents.release()
    }

    @Test
    fun testActivityA_ToActivityB_ToActivityC() {
        Intents.init()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val am = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        onView(withId(R.id.btnOpenActivityB)).perform(click())
        Thread.sleep(1000)
        intended(hasComponent(ActivityB::class.java.name))

        val appTasks = am.appTasks

        assertEquals(2, appTasks.size)

        assertEquals(1, appTasks[0].taskInfo.numActivities)
        assertEquals(1, appTasks[1].taskInfo.numActivities)

        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.topActivity?.className)
        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.baseActivity?.className)

        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.topActivity?.className)
        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.baseActivity?.className)

        onView(withId(R.id.btnOpenActivityC)).perform(click())
        Thread.sleep(1000)
        intended(hasComponent(ActivityC::class.java.name))

        assertEquals(2, appTasks.size)

        assertEquals(2, appTasks[0].taskInfo.numActivities)
        assertEquals(1, appTasks[1].taskInfo.numActivities)

        assertEquals(ActivityC::class.java.name, appTasks[0].taskInfo.topActivity?.className)
        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.baseActivity?.className)

        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.topActivity?.className)
        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.baseActivity?.className)

        Intents.release()
    }
}
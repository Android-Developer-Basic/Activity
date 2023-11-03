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

        //Test ActivityA_ToActivityB Start

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

        //Test ActivityA_ToActivityB End

        //Test ActivityB_ToActivityC Start
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

        //Test ActivityB_ToActivityC End

        Intents.release()
    }

    @Test
    fun testActivityA_ToActivityB_ToActivityC_ToActivityA() {
        Intents.init()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val am = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        //Test ActivityA_ToActivityB Start

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

        //Test ActivityA_ToActivityB End

        //Test ActivityB_ToActivityC Start
        Log.i("$INFO_TAG TestC", "")

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

        //Test ActivityB_ToActivityC End

        //Test ActivityC_ToActivityA Start

        Log.i("$INFO_TAG TestC", "")

        onView(withId(R.id.btnOpenActivityA)).perform(click())
        Thread.sleep(1000)
        intended(hasComponent(ActivityA::class.java.name))

        assertEquals(2, appTasks.size)

        assertEquals(2, appTasks[0].taskInfo.numActivities)
        assertEquals(1, appTasks[1].taskInfo.numActivities)

        assertEquals(ActivityC::class.java.name, appTasks[0].taskInfo.topActivity?.className)
        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.baseActivity?.className)

        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.topActivity?.className)
        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.baseActivity?.className)

        //Test ActivityC_ToActivityA End

        Intents.release()
    }

    @Test
    fun testActivityA_ToActivityB_ToActivityC_ToActivityD() {
        Intents.init()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val am = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        //Test ActivityA_ToActivityB Start

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

        //Test ActivityA_ToActivityB End

        //Test ActivityB_ToActivityC Start

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

        //Test ActivityB_ToActivityC End

        //Test ActivityC_ToActivityD Start

        onView(withId(R.id.btnOpenActivityD)).perform(click())
        Thread.sleep(1000)
        intended(hasComponent(ActivityD::class.java.name))

        assertEquals(2, appTasks.size)

        assertEquals(1, appTasks[0].taskInfo.numActivities)
        assertEquals(1, appTasks[1].taskInfo.numActivities)

        assertEquals(ActivityD::class.java.name, appTasks[0].taskInfo.topActivity?.className)
        assertEquals(ActivityD::class.java.name, appTasks[0].taskInfo.baseActivity?.className)

        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.topActivity?.className)
        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.baseActivity?.className)

        //Test ActivityC_ToActivityD End

        Intents.release()
    }

    @Test
    fun testActivityA_ToActivityB_ToActivityC_CloseActivityC() {
        Intents.init()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val am = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        //Test ActivityA_ToActivityB Start

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

        //Test ActivityA_ToActivityB End

        //Test ActivityB_ToActivityC Start

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

        //Test ActivityB_ToActivityC End

        //Test ActivityC_CloseActivityC Start

        onView(withId(R.id.btnCloseActivityC)).perform(click())
        Thread.sleep(1000)
        intended(hasComponent(ActivityB::class.java.name))

        assertEquals(2, appTasks.size)

        assertEquals(1, appTasks[0].taskInfo.numActivities)
        assertEquals(1, appTasks[1].taskInfo.numActivities)

        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.topActivity?.className)
        assertEquals(ActivityB::class.java.name, appTasks[0].taskInfo.baseActivity?.className)

        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.topActivity?.className)
        assertEquals(ActivityA::class.java.name, appTasks[1].taskInfo.baseActivity?.className)

        //Test ActivityC_CloseActivityC End

        Intents.release()
    }

    @Test
    fun testActivityA_ToActivityB_ToActivityC_CloseStack() {
        Intents.init()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val am = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        //Test ActivityA_ToActivityB Start

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

        //Test ActivityA_ToActivityB End

        //Test ActivityB_ToActivityC Start

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

        //Test ActivityB_ToActivityC End

        //Test ActivityC_CloseStack Start
        //TODO Mistake Когда происходит нажатие этой кнопки вызывается finishAffinity()
        //TODO Mistake Переход на ActivityA происходит
        onView(withId(R.id.btnCloseStack)).perform(click())
        Thread.sleep(1000)

        Log.i("$INFO_TAG TestC", "CloseStack")
        for (task in appTasks) {
            Log.i("$INFO_TAG TestC", "Task info:")
            Log.i("$INFO_TAG TestC", "\tCount: ${task.taskInfo.numActivities}")
            Log.i("$INFO_TAG TestC", "\tTop: ${task.taskInfo.topActivity?.shortClassName}")
            Log.i("$INFO_TAG TestC", "\tRoot: ${task.taskInfo.baseActivity?.shortClassName}")
        }
        /*
        TODO Mistake
          Task info:
            Count: 0
            Top: null
            Root: null
          Task info:
            Count: 1
            Top: .ActivityA
            Root: .ActivityA
        Вопрос это правильное поведение ?
        Просто я думал то что стек будет размером 1.
        И там будет лежать только ActivityA.
        Я не правильный тест пишу или я не правльно в manifest неправльно определил настройки для активити ?
         */

        //TODO Mistake Вот эта проверка не срабатывает.
        intended(hasComponent(ActivityA::class.java.name))

        assertEquals(1, appTasks.size)

        assertEquals(1, appTasks[0].taskInfo.numActivities)

        assertEquals(ActivityA::class.java.name, appTasks[0].taskInfo.topActivity?.className)
        assertEquals(ActivityA::class.java.name, appTasks[0].taskInfo.baseActivity?.className)

        //Test ActivityC_CloseStack End

        Intents.release()
    }
}
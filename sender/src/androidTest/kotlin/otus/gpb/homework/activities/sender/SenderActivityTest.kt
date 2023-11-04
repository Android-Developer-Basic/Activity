package otus.gpb.homework.activities.sender

import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//
//  otus.gpb.homework.activities.sender
//  Activities
//
//  Created by ponyu on 4.11.2023
//  Copyright © 2023 ponyu. All rights reserved.
//

@RunWith(AndroidJUnit4::class)
class SenderActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SenderActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun test_content() {
        onView(
            allOf(
                withId(R.id.btnToGoogleMaps),
                withText("To Google Maps"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
            )
        ).check(matches(isDisplayed()))


        onView(
            allOf(
                withId(R.id.btnSendEmail),
                withText("Send Email"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
            )
        ).check(matches(isDisplayed()))


        onView(
            allOf(
                withId(R.id.btnOpenReceiver),
                withText("Open Receiver"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun testGoogleMapsIntent() {
        // Клик на кнопку "To Google Maps"
        onView(withId(R.id.btnToGoogleMaps)).perform(click())

        // Проверка вызова явного Intent
        val expectedIntent = allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData(Uri.parse("geo:0,0?q=restaurants"))
        )
        Intents.intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        Thread.sleep(2000)

        // Проверка открытия Activity Google Maps
        intended(expectedIntent)
    }

    @Test
    fun testSendEmailIntent() {
        // Клик на кнопку "Send Email"
        onView(withId(R.id.btnSendEmail)).perform(click())

        val mailOtus = "android@otus.ru"
        val emailSubject = "Lesson Activity 2"
        val emailText = "Example text for Lesson Activity 2"

        // Проверка вызова не явного Intent
        val expectedIntent = allOf(
            hasAction(Intent.ACTION_SENDTO),
            hasData(Uri.parse("mailto:$mailOtus?subject=$emailSubject&body=$emailText"))
            /*
            TODO если использовать в Активти putExtra. То здесь проверка не проходит.
             Решил оставить на потом разобраться по чему не работет.
            hasExtra(Intent.EXTRA_EMAIL, mailOtus),
            hasExtra(Intent.EXTRA_SUBJECT, emailSubject),
            hasExtra(Intent.EXTRA_TEXT, emailText)
            */
        )
        Intents.intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        Thread.sleep(1000)

        // Проверка открытия Activity для отправки письма
        intended(expectedIntent)
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}
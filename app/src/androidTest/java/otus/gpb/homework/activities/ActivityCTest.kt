package otus.gpb.homework.activities

import android.widget.Button
import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//
//  otus.gpb.homework.activities
//  Activities
//
//  Created by ponyu on 3.11.2023
//  Copyright © 2023 ponyu. All rights reserved.
//

@RunWith(AndroidJUnit4::class)
class ActivityCTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ActivityC::class.java)

    @Test
    fun test_content() {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.btnOpenActivityA),
                ViewMatchers.withText("Open ActivityA"),
                ViewMatchers.isAssignableFrom(Button::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(LinearLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.btnOpenActivityD),
                ViewMatchers.withText("Open ActivityD"),
                ViewMatchers.isAssignableFrom(Button::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(LinearLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.btnCloseActivityC),
                ViewMatchers.withText("Close ActivityC"),
                ViewMatchers.isAssignableFrom(Button::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(LinearLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.btnCloseStack),
                ViewMatchers.withText("Close Stack"),
                ViewMatchers.isAssignableFrom(Button::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(LinearLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
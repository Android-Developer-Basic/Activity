package otus.gpb.homework.activities

import android.widget.Button
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
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

@RunWith(AndroidJUnit4::class)
class ActivityATest{
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ActivityA::class.java)

    @Test
    fun test_content() {
        onView(
            Matchers.allOf(
                withId(R.id.btnOpenActivityB),
                withText("Open ActivityB"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}